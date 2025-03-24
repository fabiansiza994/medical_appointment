package com.fmsp.medical_appointment.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class GenericEntity {

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "ESTADO")
    protected Boolean estado = true;
}