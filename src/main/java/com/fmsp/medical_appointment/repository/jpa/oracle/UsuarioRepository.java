package com.fmsp.medical_appointment.repository.jpa.oracle;

import com.fmsp.medical_appointment.entity.core.oracle.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.especialidad.id <> 0")
    Optional<Usuario> findByIdAndEspecialidad(@Param("id") Long id);
}
