package com.fmsp.medical_appointment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDTO {
    AgendaDTO agenda;
    SolicitarCitaDTO datosCita;
}