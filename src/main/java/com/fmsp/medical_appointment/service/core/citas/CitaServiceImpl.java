package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.configuration.exceptionManager.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.AgendaDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.dto.UsuarioDTO;
import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import com.fmsp.medical_appointment.repository.jpa.oracle.CitaRepository;
import com.fmsp.medical_appointment.service.core.agenda.IConsultarAgenda;
import com.fmsp.medical_appointment.service.core.agenda.ICrearAgenda;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements ISolicitarCita, ICancelarCita {

    private final IConsultarAgenda consultarAgenda;
    private final ICrearAgenda crearAgenda;

    private final CitaRepository citaRepository;
    private final ModelMapper modelMapper;

    public CitaServiceImpl(IConsultarAgenda consultarAgenda, ICrearAgenda crearAgenda, CitaRepository citaRepository, ModelMapper modelMapper) {
        this.consultarAgenda = consultarAgenda;
        this.crearAgenda = crearAgenda;
        this.citaRepository = citaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SolicitarCitaDTO solicitarCita(SolicitarCitaDTO solicitarCita) {

        consultarAgenda(solicitarCita);
        crearAgenda(solicitarCita);

        var cita = modelMapper.map(solicitarCita, Cita.class);
        var citaDB = citaRepository.save(cita);
        var citaCreada = modelMapper.map(citaDB, SolicitarCitaDTO.class);

        //TODO enviar notificacion

        return citaCreada;
    }

    private void crearAgenda(SolicitarCitaDTO solicitarCita) {
        var usuario = new UsuarioDTO();
        usuario.setId(solicitarCita.getIdMedico());

        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setFecha(solicitarCita.getFechaHora());
        agendaDTO.setMedico(usuario);
        agendaDTO.setDisponibilidad(true);
        AgendaDTO newAgendaDTO = crearAgenda.crearAgenda(agendaDTO);
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
