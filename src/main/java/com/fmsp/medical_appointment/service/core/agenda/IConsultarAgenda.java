package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.dto.AgendaDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@FunctionalInterface
public interface IConsultarAgenda {
    Optional<AgendaDTO> consultarAgenda(Long idMedico, LocalDateTime fechaHora);
}
