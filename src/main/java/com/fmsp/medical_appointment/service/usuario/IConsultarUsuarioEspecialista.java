package com.fmsp.medical_appointment.service.usuario;

import com.fmsp.medical_appointment.dto.UsuarioDTO;
import org.springframework.data.repository.query.Param;

public interface IConsultarUsuarioEspecialista {
    UsuarioDTO consultarEspecialista(@Param("id") Long id);

}
