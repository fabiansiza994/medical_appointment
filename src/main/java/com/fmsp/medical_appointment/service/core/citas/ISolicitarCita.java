package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;

@FunctionalInterface
public interface ISolicitarCita {
    SolicitarCitaDTO solicitarCita(SolicitarCitaDTO solicitarCita);
}
