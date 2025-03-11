package com.fmsp.medical_appointment.entity.core.oracle;

import com.fmsp.medical_appointment.entity.base.GenericEntity;
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
public class Especialidad extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESPECIALIDAD")
    private Long id;

    @Column(name = "NOMBRE_EPECIALIDAD", nullable = false)
    private String nombreEspecialidad;
}
