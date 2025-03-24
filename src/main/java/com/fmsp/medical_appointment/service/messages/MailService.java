package com.fmsp.medical_appointment.service.messages;

@FunctionalInterface
public interface MailService {
    void sendMail(String toEmail, String subject, String body);
}
