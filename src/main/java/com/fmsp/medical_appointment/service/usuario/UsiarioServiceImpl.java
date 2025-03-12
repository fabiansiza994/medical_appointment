package com.fmsp.medical_appointment.service.usuario;

import com.fmsp.medical_appointment.dto.UsuarioDTO;
import com.fmsp.medical_appointment.repository.jpa.oracle.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UsiarioServiceImpl implements IConsultarUsuario, IConsultarUsuarioEspecialista{

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsiarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioDTO consultarUsuario(Long id) {
        var usuarioDb = usuarioRepository.findById(id);
        return usuarioDb.map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).orElse(null);
    }

    @Override
    public UsuarioDTO consultarEspecialista(Long id) {
        var usuarioDb = usuarioRepository.findByIdAndEspecialidad(id);
        return usuarioDb.map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).orElse(null);
    }
}
