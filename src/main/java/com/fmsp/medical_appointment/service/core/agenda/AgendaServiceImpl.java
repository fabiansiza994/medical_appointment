package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.controller.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.AgendaDTO;
import com.fmsp.medical_appointment.entity.core.oracle.Agenda;
import com.fmsp.medical_appointment.repository.jpa.oracle.AgendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendaServiceImpl implements IConsultarAgenda, ICrearAgenda, ICancelarAgenda {

    private final AgendaRepository agendaRepository;
    private final ModelMapper modelMapper;

    public AgendaServiceImpl(AgendaRepository agendaRepository, ModelMapper modelMapper) {
        this.agendaRepository = agendaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<AgendaDTO> consultarAgenda(Long idMedico, LocalDateTime fechaHora) {
        return agendaRepository.findByMedicoAndFecha(idMedico, fechaHora).map(agenda ->
                modelMapper.map(agenda, AgendaDTO.class));
    }

    @Override
    public AgendaDTO crearAgenda(AgendaDTO agenda) {
        var agendaEntity = modelMapper.map(agenda, Agenda.class);
        var agendaDb = agendaRepository.save(agendaEntity);
        return modelMapper.map(agendaDb, AgendaDTO.class);
    }

    @Override
    public AgendaDTO cancelarAgenda(AgendaDTO agendaDTO, String idTx) {
        Long idMedico = agendaDTO.getMedico().getId();
        LocalDateTime fecha = agendaDTO.getFecha();

        Agenda agenda = agendaRepository
                .findByMedicoIdAndFecha(idMedico, fecha)
                .orElseThrow(() -> new CustomServiceException(
                        "ERROR",
                        idTx,
                        "E014",
                        "404",
                        "Agenda no encontrada para la fecha y médico especificados"
                ));

        agenda.setDisponibilidad(true);
        agenda.setUpdatedAt(LocalDateTime.now());

        Agenda updated = agendaRepository.save(agenda);
        return modelMapper.map(updated, AgendaDTO.class);
    }
}