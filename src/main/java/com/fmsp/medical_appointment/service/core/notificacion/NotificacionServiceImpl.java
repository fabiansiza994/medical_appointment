package com.fmsp.medical_appointment.service.core.notificacion;

import com.fmsp.medical_appointment.dto.NotificacionDTO;
import com.fmsp.medical_appointment.entity.core.mysql.Notificacion;
import com.fmsp.medical_appointment.repository.jpa.mysql.NotificacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServiceImpl implements IEnviarNotificacion{

    private final NotificacionRepository notificacionRepository;
    private final ModelMapper modelMapper;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, ModelMapper modelMapper) {
        this.notificacionRepository = notificacionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void enviarNotificacion(NotificacionDTO notificacion) {
        notificacionRepository.save(modelMapper.map(notificacion, Notificacion.class));
        System.out.println(notificacion.getMensaje());
    }
}
