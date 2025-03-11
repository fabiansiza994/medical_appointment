package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.dto.AgendaDTO;
import com.fmsp.medical_appointment.entity.core.oracle.Agenda;
import com.fmsp.medical_appointment.entity.core.oracle.Usuario;
import com.fmsp.medical_appointment.repository.jpa.oracle.AgendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaServiceImpl implements IConsultarAgenda, ICrearAgenda, ICancelarAgenda {

    private final AgendaRepository agendaRepository;
    private final ModelMapper modelMapper;

    public AgendaServiceImpl(AgendaRepository agendaRepository, ModelMapper modelMapper) {
        this.agendaRepository = agendaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AgendaDTO consultarAgenda(Long idMedico, LocalDateTime fechaHora) {
        return agendaRepository.findByMedicoAndFecha(idMedico, fechaHora)
                .map(agenda -> modelMapper.map(agenda, AgendaDTO.class))
                .orElse(null);  // Si no hay agenda en esa fecha/hora, devuelve `null`
    }

    @Override
    public AgendaDTO crearAgenda(AgendaDTO agenda) {
        var agendaEntity = modelMapper.map(agenda, Agenda.class);
        var agendaDb = agendaRepository.save(agendaEntity);
        return modelMapper.map(agendaDb, AgendaDTO.class);
    }

    @Override
    public AgendaDTO eliminarAgenda(AgendaDTO agenda) {
        return null;
    }
}
