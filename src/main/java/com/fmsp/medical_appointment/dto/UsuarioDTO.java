package com.fmsp.medical_appointment.dto;

import com.fmsp.medical_appointment.entity.core.oracle.Especialidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDateTime fechaNacimiento;
    private Especialidad especialidad;
}
