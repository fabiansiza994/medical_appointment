package com.fmsp.medical_appointment.entity.oracle;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Profesional medico;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cita", nullable = false)
    private TipoCita tipoCita;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "valor_cita", nullable = false)
    private Double valorCita;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCita estado;
}
