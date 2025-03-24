package com.fmsp.medical_appointment.repository.jpa.oracle;

import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT c FROM Cita c WHERE c.fechaHora = :fechaHora AND c.paciente.id = :idPaciente AND c.medico.id = :idMedico")
    Optional<Cita> findByFechaHoraAndPacienteIdAndMedicoId(LocalDateTime fechaHora, Long idPaciente, Long idMedico);

    @Query("SELECT c FROM Cita c WHERE c.estado = 'CONFIRMADA' AND c.fechaHora BETWEEN :inicio AND :fin")
    List<Cita> findCitasParaRecordatorio(LocalDateTime inicio, LocalDateTime fin);
}
