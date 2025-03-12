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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        if(solicitarCita.getMetodoPago().equals("EFECTIVO")){
            solicitarCita.setEstado(EstadoCita.CONFIRMADA);
        }

        var response = new ResponseDTO();

        consultarAgenda(solicitarCita);
        var agenda = crearAgenda(solicitarCita);

        var cita = modelMapper.map(solicitarCita, Cita.class);
        var citaDB = citaRepository.save(cita);

        response.setDatosCita(modelMapper.map(citaDB, SolicitarCitaDTO.class));
        response.setAgenda(agenda);

        var especialista = consultarUsuarioEspecialista.consultarEspecialista(response.getDatosCita().getIdMedico());

        response.getAgenda().setMedico(especialista);
        enviarNotificacion(response);

        return response;
    }

    private void enviarNotificacion(ResponseDTO response) {
        var notificacion = new NotificacionDTO();
        notificacion.setIdPaciente(response.getDatosCita().getIdPaciente());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setMensaje("Su cita ha sido programada para el dia "+ response.getAgenda().getFecha()
                + ", con el especialista: "+ response.getAgenda().getMedico().getNombre());

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
        AgendaDTO agenda = consultarAgenda.consultarAgenda(solicitarCita.getIdMedico(), solicitarCita.getFechaHora());

        if (agenda != null) {
            throw new CustomServiceException(
                    "Agenda ocupada", solicitarCita.getIdTx(), "E001", "400", "El médico ya tiene una cita en este horario."
            );
        }
    }


    @Override
    public SolicitarCitaDTO cancelarCita(SolicitarCitaDTO solicitarCita) {
        return null;
    }
}
