package com.fmsp.medical_appointment.entity.oracle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ESPECIALIDAD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long id;

    @Column(name = "nombre_especialidad", nullable = false)
    private String nombreEspecialidad;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
