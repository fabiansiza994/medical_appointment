package com.fmsp.medical_appointment.repository.jpa.oracle;

import com.fmsp.medical_appointment.entity.core.oracle.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("SELECT a FROM Agenda a WHERE a.medico.id = :medicoId " +
            "AND a.fecha = :fechaHora " +
            "AND a.disponibilidad = true")
    Optional<Agenda> findByMedicoAndFecha(@Param("medicoId") Long medicoId,
                                          @Param("fechaHora") LocalDateTime fechaHora);
}
