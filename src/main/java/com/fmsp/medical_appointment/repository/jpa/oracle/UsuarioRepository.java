package com.fmsp.medical_appointment.repository.jpa.oracle;

import com.fmsp.medical_appointment.entity.core.oracle.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);
}
