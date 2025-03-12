package com.fmsp.medical_appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(value = { "id" }, allowSetters = true)
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private EspecialidadDTO especialidad;
}
