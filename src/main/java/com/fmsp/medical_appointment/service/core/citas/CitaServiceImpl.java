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

@Service
public class CitaServiceImpl implements ISolicitarCita, ICancelarCita {

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
                .findByFechaHoraAndPacienteIdAndMedicoId(solicitarCita.getFechaHora(), solicitarCita.getIdPaciente(),
                        solicitarCita.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con los datos proporcionados"));

        if(cita.getEstado() == EstadoCita.CANCELADA){
            throw new CustomServiceException("ERROR", idTx, "E008", "500", "la cita ya fue cancelada");
        }

        if (!(cita.getEstado().equals(EstadoCita.PENDIENTE) ||
                        cita.getEstado().equals(EstadoCita.CONFIRMADA))
        ) {
            throw new CustomServiceException("ERROR", idTx, "E007", "500", "Solo se pueden cancelar citas en estado PENDIENTE o CONFIRMADA");
        }

        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);

        var medico = new UsuarioDTO();
        medico.setId(solicitarCita.getIdMedico());

        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setMedico(medico);
        agendaDTO.setFecha(solicitarCita.getFechaHora());
        cancelarAgenda.cancelarAgenda(agendaDTO);

        var response = construirResponse(cita, agendaDTO);
        notificacionHelper.enviar(response, Constants.CITA_CANCELADA);
        return response;
    }
}