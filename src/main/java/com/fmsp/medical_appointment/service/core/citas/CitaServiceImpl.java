package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.configuration.exceptionManager.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.*;
import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import com.fmsp.medical_appointment.entity.enums.EstadoCita;
import com.fmsp.medical_appointment.repository.jpa.oracle.CitaRepository;
import com.fmsp.medical_appointment.service.core.agenda.IConsultarAgenda;
import com.fmsp.medical_appointment.service.core.agenda.ICrearAgenda;
import com.fmsp.medical_appointment.service.core.notificacion.IEnviarNotificacion;
import com.fmsp.medical_appointment.service.usuario.IConsultarUsuarioEspecialista;
import com.fmsp.medical_appointment.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class CitaServiceImpl implements ISolicitarCita, ICancelarCita {

    private final IConsultarAgenda consultarAgenda;
    private final ICrearAgenda crearAgenda;
    private final IEnviarNotificacion enviarNotificacion;
    private final IConsultarUsuarioEspecialista consultarUsuarioEspecialista;

    private final CitaRepository citaRepository;
    private final ModelMapper modelMapper;

    public CitaServiceImpl(IConsultarAgenda consultarAgenda, ICrearAgenda crearAgenda,
                           IEnviarNotificacion enviarNotificacion,
                           IConsultarUsuarioEspecialista consultarUsuarioEspecialista,
                           CitaRepository citaRepository, ModelMapper modelMapper) {
        this.consultarAgenda = consultarAgenda;
        this.crearAgenda = crearAgenda;
        this.enviarNotificacion = enviarNotificacion;
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
        enviarNotificacion(response);

        return response;
    }

    private void actualizarEstadoCita(SolicitarCitaDTO solicitarCita) {
        if (solicitarCita.getMetodoPago().equals("EFECTIVO")) {
            solicitarCita.setEstado(EstadoCita.CONFIRMADA);
        }
    }

    private void enviarNotificacion(ResponseDTO response) {
        var notificacion = new NotificacionDTO();
        notificacion.setIdPaciente(response.getDatosCita().getIdPaciente());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setMensaje(MessageFormat.format(
                Constants.CITA_PROGRAMADA_MESSAGE,
                response.getAgenda().getFecha(),
                response.getAgenda().getMedico().getNombre()
        ));

        enviarNotificacion.enviarNotificacion(notificacion);
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

    @Override
    public SolicitarCitaDTO cancelarCita(SolicitarCitaDTO solicitarCita) {
        throw new UnsupportedOperationException("Método cancelarCita() aún no implementado.");
    }
}