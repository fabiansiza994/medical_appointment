package com.fmsp.medical_appointment.entity.core.oracle;

import com.fmsp.medical_appointment.entity.base.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "PERSONA_SEQ", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;
}
