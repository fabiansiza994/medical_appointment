package com.fmsp.medical_appointment.service.core.notificacion;

import com.fmsp.medical_appointment.dto.NotificacionDTO;

public interface IEnviarNotificacion {
    void enviarNotificacion(NotificacionDTO notificacion);
}
