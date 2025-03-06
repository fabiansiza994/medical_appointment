package com.fmsp.medical_appointment.entity.oracle;

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
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Profesional medico;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "disponibilidad", nullable = false)
    private Boolean disponibilidad;
}
