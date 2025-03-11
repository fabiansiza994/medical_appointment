package com.fmsp.medical_appointment.entity.core.oracle;

import com.fmsp.medical_appointment.entity.base.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AGENDA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agenda extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICO", nullable = false)
    private Profesional medico;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "DISPONIBILIDAD", nullable = false)
    private Boolean disponibilidad;
}
