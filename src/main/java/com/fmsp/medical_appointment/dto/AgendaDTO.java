package com.fmsp.medical_appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(value = { "id" }, allowSetters = true)
public class AgendaDTO {
    private Long id;
    private UsuarioDTO medico;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 'a las' HH:mm")
    private LocalDateTime fecha;
    private Boolean disponibilidad;
}
