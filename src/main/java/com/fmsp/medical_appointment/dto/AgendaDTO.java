package com.fmsp.medical_appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AgendaDTO {
    private Long id;
    private UsuarioDTO medico;
    private LocalDateTime fecha;
    private Boolean disponibilidad;
}
