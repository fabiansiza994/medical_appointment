package com.fmsp.medical_appointment.service.core.agenda;

import com.fmsp.medical_appointment.dto.AgendaDTO;

public interface ICancelarAgenda {
    AgendaDTO cancelarAgenda(AgendaDTO agenda, String idTx);
}
