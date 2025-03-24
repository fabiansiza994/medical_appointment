package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.dto.ResponseDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;

@FunctionalInterface
public interface ICancelarCita {
    ResponseDTO cancelarCita(SolicitarCitaDTO solicitarCita);
}
