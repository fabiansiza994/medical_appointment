package com.fmsp.medical_appointment.repository.jpa.oracle;

import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
