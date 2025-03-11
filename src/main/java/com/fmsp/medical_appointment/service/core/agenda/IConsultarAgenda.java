package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.dto.AgendaDTO;

import java.time.LocalDateTime;

@FunctionalInterface
public interface IConsultarAgenda {
    AgendaDTO consultarAgenda(Long idMedico, LocalDateTime fechaHora);
}
