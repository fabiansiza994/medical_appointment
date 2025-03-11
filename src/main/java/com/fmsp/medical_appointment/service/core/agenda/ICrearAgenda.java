package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.dto.AgendaDTO;

@FunctionalInterface
public interface ICrearAgenda {
    AgendaDTO crearAgenda(AgendaDTO agenda);
}
