package com.fmsp.medical_appointment.entity.core.mysql;

import com.fmsp.medical_appointment.entity.base.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICACIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACION")
    private Long id;

    @Column(name = "ID_PACIENTE", nullable = false)
    private Long idPaciente;

    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    @Column(name = "FECHA_ENVIO", nullable = false)
    private LocalDateTime fechaEnvio;
}