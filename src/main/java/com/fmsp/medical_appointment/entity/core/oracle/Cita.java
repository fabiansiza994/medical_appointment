package com.fmsp.medical_appointment.entity.core.oracle;

import com.fmsp.medical_appointment.entity.enums.EstadoCita;
import com.fmsp.medical_appointment.entity.enums.MetodoPago;
import com.fmsp.medical_appointment.entity.enums.TipoCita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CITA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cita_seq")
    @SequenceGenerator(name = "cita_seq", sequenceName = "CITA_SEQ", allocationSize = 1)
    @Column(name = "ID_CITA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTE", nullable = false)
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICO", nullable = false)
    private Usuario medico;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CITA", nullable = false)
    private TipoCita tipoCita;

    @Enumerated(EnumType.STRING)
    @Column(name = "METODO_PAGO", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "VALOR_CITA", nullable = false)
    private Double valorCita;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoCita estado;
}
