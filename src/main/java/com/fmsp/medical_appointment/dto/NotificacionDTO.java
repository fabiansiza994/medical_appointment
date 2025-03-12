package com.fmsp.medical_appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NotificacionDTO {
    private Long id;
    private Long idPaciente;
    private String mensaje;
    private LocalDateTime fechaEnvio;
}
