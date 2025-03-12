package com.fmsp.medical_appointment.service.core.notificacion;

import com.fmsp.medical_appointment.dto.NotificacionDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServiceImpl implements IEnviarNotificacion{

    @Override
    public void enviarNotificacion(NotificacionDTO notificacion) {
        System.out.println(notificacion.getMensaje());
    }
}
