package com.fmsp.medical_appointment.util;

import com.fmsp.medical_appointment.dto.NotificacionDTO;
import com.fmsp.medical_appointment.dto.ResponseDTO;
import com.fmsp.medical_appointment.service.core.notificacion.IEnviarNotificacion;
import com.fmsp.medical_appointment.service.messages.MailService;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Component
public class NotificacionHelper {

    private final IEnviarNotificacion enviarNotificacion;
    private final MailService mailService;

    public NotificacionHelper(IEnviarNotificacion enviarNotificacion, MailService mailService) {
        this.enviarNotificacion = enviarNotificacion;
        this.mailService = mailService;
    }

    public void enviar(ResponseDTO response, String mensajePlantilla) {
        var notificacion = new NotificacionDTO();
        notificacion.setIdPaciente(response.getDatosCita().getIdPaciente());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setMensaje(MessageFormat.format(
                mensajePlantilla,
                response.getAgenda().getFecha(),
                response.getAgenda().getMedico().getNombre()
        ));

        enviarNotificacion.enviarNotificacion(notificacion);
    }

    public void enviarCorreoSimple(String toEmail, String asunto, String mensajeHtml) {
        mailService.sendMail(toEmail, asunto, mensajeHtml);
    }
}
