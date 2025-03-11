package com.fmsp.medical_appointment.entity.core.oracle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PROFESIONAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesional extends Usuario {

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;
}
