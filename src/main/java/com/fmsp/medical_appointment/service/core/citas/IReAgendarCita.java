package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.dto.ResponseDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;

@FunctionalInterface
public interface IReAgendarCita {
    ResponseDTO reAgendarCita(SolicitarCitaDTO solicitarCita);
}
