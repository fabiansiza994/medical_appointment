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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esp_seq")
    @SequenceGenerator(name = "esp_seq", sequenceName = "ESP_SEQ", allocationSize = 1)
    @Column(name = "ID_ESPECIALIDAD")
    private Long id;

    @Column(name = "NOMBRE_ESPECIALIDAD", nullable = false)
    private String nombreEspecialidad;
}
