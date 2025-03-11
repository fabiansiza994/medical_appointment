package com.fmsp.medical_appointment.entity.core.oracle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PACIENTE")
@Getter
@Setter
@AllArgsConstructor
public class Paciente extends Usuario {
}
