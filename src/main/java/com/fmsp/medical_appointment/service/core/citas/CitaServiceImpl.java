package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.controller.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.AgendaDTO;
import com.fmsp.medical_appointment.dto.ResponseDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.dto.UsuarioDTO;
import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import com.fmsp.medical_appointment.entity.enums.EstadoCita;
import com.fmsp.medical_appointment.entity.enums.MetodoPago;
import com.fmsp.medical_appointment.repository.jpa.oracle.CitaRepository;
import com.fmsp.medical_appointment.service.core.agenda.ICancelarAgenda;
import com.fmsp.medical_appointment.service.core.agenda.IConsultarAgenda;
import com.fmsp.medical_appointment.service.core.agenda.ICrearAgenda;
import com.fmsp.medical_appointment.service.usuario.IConsultarUsuarioEspecialista;
import com.fmsp.medical_appointment.util.Constants;
import com.fmsp.medical_appointment.util.NotificacionHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaServiceImpl implements ISolicitarCita, ICancelarCita, IReAgendarCita {

    private final IConsultarAgenda consultarAgenda;
    private final ICrearAgenda crearAgenda;
    private final ICancelarAgenda cancelarAgenda;

    private final NotificacionHelper notificacionHelper;
    private final IConsultarUsuarioEspecialista consultarUsuarioEspecialista;

    private final CitaRepository citaRepository;
    private final ModelMapper modelMapper;

    public CitaServiceImpl(IConsultarAgenda consultarAgenda, ICrearAgenda crearAgenda, ICancelarAgenda cancelarAgenda,
                           NotificacionHelper notificacionHelper,
                           IConsultarUsuarioEspecialista consultarUsuarioEspecialista,
                           CitaRepository citaRepository, ModelMapper modelMapper) {
        this.consultarAgenda = consultarAgenda;
        this.crearAgenda = crearAgenda;
        this.cancelarAgenda = cancelarAgenda;
        this.notificacionHelper = notificacionHelper;
        this.consultarUsuarioEspecialista = consultarUsuarioEspecialista;
        this.citaRepository = citaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseDTO solicitarCita(SolicitarCitaDTO solicitarCita) {

        actualizarEstadoCita(solicitarCita);
        consultarAgenda(solicitarCita);

        var agenda = crearAgenda(solicitarCita);

        var cita = modelMapper.map(solicitarCita, Cita.class);
        var citaDB = citaRepository.save(cita);

        var response = construirResponse(citaDB, agenda);
        notificacionHelper.enviar(response, Constants.CITA_PROGRAMADA_MESSAGE);

        return response;
    }

    private void actualizarEstadoCita(SolicitarCitaDTO solicitarCita) {
        solicitarCita.setEstado(EstadoCita.CONFIRMADA);
        if (solicitarCita.getMetodoPago() != MetodoPago.EFECTIVO) {
            solicitarCita.setEstado(EstadoCita.PENDIENTE);
        }
    }

    private AgendaDTO crearAgenda(SolicitarCitaDTO solicitarCita) {
        var usuario = new UsuarioDTO();
        usuario.setId(solicitarCita.getIdMedico());

        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setFecha(solicitarCita.getFechaHora());
        agendaDTO.setMedico(usuario);
        agendaDTO.setDisponibilidad(true);
        return crearAgenda.crearAgenda(agendaDTO);
    }

    public void consultarAgenda(SolicitarCitaDTO solicitarCita) {
        consultarAgenda.consultarAgenda(solicitarCita.getIdMedico(), solicitarCita.getFechaHora())
                .ifPresent(agenda -> {
                    throw new CustomServiceException(
                            Constants.SCHELUDED_OFF, solicitarCita.getIdTx(), Constants.ERROR_E001, Constants.ERROR_400,
                            Constants.MEDIC_UNAVAILABLE_AT_THIS_TIME
                    );
                });
    }

    private ResponseDTO construirResponse(Cita citaDB, AgendaDTO agenda) {
        var response = new ResponseDTO();
        response.setDatosCita(modelMapper.map(citaDB, SolicitarCitaDTO.class));
        response.setAgenda(agenda);
        var especialista = consultarUsuarioEspecialista.consultarEspecialista(response.getDatosCita().getIdMedico());
        response.getAgenda().setMedico(especialista);
        return response;
    }

    @Transactional
    @Override
    public ResponseDTO cancelarCita(SolicitarCitaDTO solicitarCita) {
        var idTx = solicitarCita.getIdTx();

        Cita cita = citaRepository
                .findByFechaHoraAndPacienteIdAndMedicoId(
                        solicitarCita.getFechaHora(),
                        solicitarCita.getIdPaciente(),
                        solicitarCita.getIdMedico()
                )
                .orElseThrow(() -> new CustomServiceException(
                        "ERROR",
                        idTx,
                        "E010",
                        "404",
                        "Cita no encontrada con los datos proporcionados"
                ));

        if(cita.getEstado() == EstadoCita.CANCELADA){
            throw new CustomServiceException("ERROR", idTx, "E008", "500", "la cita ya fue cancelada");
        }

        if (!(cita.getEstado().equals(EstadoCita.PENDIENTE) ||
                        cita.getEstado().equals(EstadoCita.CONFIRMADA))
        ) {
            throw new CustomServiceException("ERROR", idTx, "E007", "500", "Solo se pueden cancelar citas en estado PENDIENTE o CONFIRMADA");
        }

        double nuevoValor = calcularIncrementoPorReagendamiento(cita.getValorCita());
        cita.setValorCita(nuevoValor);

        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);

        var medico = new UsuarioDTO();
        medico.setId(solicitarCita.getIdMedico());

        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setMedico(medico);
        agendaDTO.setFecha(solicitarCita.getFechaHora());
        cancelarAgenda.cancelarAgenda(agendaDTO, idTx);

        var response = construirResponse(cita, agendaDTO);
        notificacionHelper.enviar(response, Constants.CITA_CANCELADA);

        notificacionHelper.enviarCorreoCita(
                cita,
                "Cancelación de Cita Médica",
                Constants.CITA_CANCELADA_MESSAGE
        );

        return response;
    }

    public void enviarRecordatorioCitasProximas() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicio = ahora.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = inicio.withHour(23).withMinute(59).withSecond(59);

        List<Cita> citas = citaRepository.findCitasParaRecordatorio(inicio, fin);

        for (Cita cita : citas) {
            String correo = cita.getPaciente().getEmail();

            if (correo == null || correo.isBlank()) {
                continue;
            }

            String body = "<h2>Recordatorio de cita</h2>" +
                    "<p>Estimado/a " + cita.getPaciente().getNombre() + ",</p>" +
                    "<p>Le recordamos que tiene una cita programada para el día <strong>" + cita.getFechaHora().toLocalDate() +
                    "</strong> a las <strong>" + cita.getFechaHora().toLocalTime() + "</strong>.</p>" +
                    "<p>Por favor, llegue con anticipación. ¡Gracias por confiar en nosotros!</p>";

            notificacionHelper.enviarCorreoSimple(
                    correo,
                    "Recordatorio de Cita Médica",
                    body
            );

        }
    }

    @Transactional
    @Override
    public ResponseDTO reAgendarCita(SolicitarCitaDTO solicitarCita) {

        var idTx = solicitarCita.getIdTx();

        // 1. Buscar cita original
        Cita citaOriginal = citaRepository
                .findByFechaHoraAndPacienteIdAndMedicoId(
                        solicitarCita.getFechaHora(),
                        solicitarCita.getIdPaciente(),
                        solicitarCita.getIdMedico()
                )
                .orElseThrow(() -> new CustomServiceException("ERROR", idTx, "E010", "404", "Cita no encontrada"));


        var fechaOriginal = citaOriginal.getFechaHora();
        var nuevaFecha = solicitarCita.getNuevaFechaHora();

        if (nuevaFecha.toLocalDate().isEqual(fechaOriginal.toLocalDate())) {
            throw new CustomServiceException("ERROR", idTx, "E012", "400", "No se puede reagendar una cita el mismo día");
        }

        if (nuevaFecha.isBefore(fechaOriginal)) {
            throw new CustomServiceException("ERROR", idTx, "E015", "400", "No se puede reagendar a una fecha anterior a la original");
        }

        // 2. Validaciones
        if (citaOriginal.getEstado() == EstadoCita.CANCELADA || citaOriginal.getEstado() == EstadoCita.COMPLETADA) {
            throw new CustomServiceException("ERROR", idTx, "E011", "400", "No se puede reagendar una cita cancelada o completada");
        }

        // 3. Verificar que el médico tenga disponibilidad en la nueva fecha
        consultarAgenda.consultarAgenda(solicitarCita.getIdMedico(), solicitarCita.getNuevaFechaHora())
                .ifPresent(agenda -> {
                    throw new CustomServiceException("ERROR", idTx, "E013", "400", "El médico no tiene disponibilidad en la nueva fecha");
                });

        // 4. Liberar la agenda anterior
        var medico = new UsuarioDTO();
        medico.setId(citaOriginal.getMedico().getId());

        var agendaAntigua = new AgendaDTO();
        agendaAntigua.setMedico(medico);
        agendaAntigua.setFecha(citaOriginal.getFechaHora());
        agendaAntigua.setDisponibilidad(true);
        cancelarAgenda.cancelarAgenda(agendaAntigua, idTx);


        // 5. Crear nueva agenda
        var medicoSoli = new UsuarioDTO();
        medicoSoli.setId(solicitarCita.getIdMedico());

        var agendaNueva = new AgendaDTO();
        agendaNueva.setMedico(medicoSoli);
        agendaNueva.setFecha(solicitarCita.getNuevaFechaHora());
        agendaNueva.setDisponibilidad(false);
        var nuevaAgenda = crearAgenda.crearAgenda(agendaNueva);

        // 6. Actualizar la cita original con la nueva fecha y nuevo valor
        citaOriginal.setFechaHora(solicitarCita.getNuevaFechaHora());
        double nuevoValor = calcularIncrementoPorReagendamiento(citaOriginal.getValorCita());
        citaOriginal.setValorCita(nuevoValor);
        citaRepository.save(citaOriginal);

        // 7. Armar respuesta y notificar
        var response = construirResponse(citaOriginal, nuevaAgenda);
        notificacionHelper.enviar(response, Constants.CITA_REAGENDADA_MESSAGE);

        notificacionHelper.enviarCorreoCita(
                citaOriginal,
                "Reagendamiento de Cita Médica",
                Constants.CITA_REAGENDADA_MESSAGE
        );

        return response;
    }

    private double calcularIncrementoPorReagendamiento(double valorOriginal) {
        return valorOriginal + (valorOriginal / 2);
    }

}