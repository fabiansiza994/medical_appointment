package com.fmsp.medical_appointment.util;

public class Constants {
    public static final String INTERNAL_ERROR_MESSAGE = "Error inesperado del sistema";
    public static final String ERROR_PAYMENT_METHOD = "Metodo de pago invalido";
    public static final String ESP_NOT_FOUND = "no existe la especialidad";
    public static final String INSUFFICIENT_AMOUNT = "monto insuficiente";
    public static final String MEDIC_UNAVAILABLE_AT_THIS_TIME = "El médico ya tiene una cita en este horario.";
    public static final String SCHELUDED_OFF = "Agenda ocupada";
    public static final String CITA_PROGRAMADA_MESSAGE =
            "Su cita ha sido programada para el día {0}, con el especialista: {1}.";
    public static final String CITA_CANCELADA = "su cita a sido cancelada exitosamente!";

    // CUSTOM CODE ERRORS
    public static final String ERROR_E001 = "E001";
    public static final String ERROR_E002 = "E002";
    public static final String ERROR_E003 = "E003";
    public static final String ERROR_E004 = "E004";
    public static final String ERROR_E005 = "E005";
    public static final String ERROR_E006 = "E006";


    // SYSTEM ERRORS
    public static final String ERROR_404 = "404";
    public static final String ERROR_400 = "400";
    public static final String ERROR_500 = "500";



    public static final String CITA_RECORDATORIO_MESSAGE = """
        <h2>Recordatorio de cita</h2>
        <p>Estimado/a {0},</p>
        <p>Le recordamos que tiene una cita programada para el día <strong>{1}</strong> a las <strong>{2}</strong>.</p>
        <p>Por favor, llegue con anticipación. ¡Gracias por confiar en nosotros!</p>
        """;

    public static final String CITA_CANCELADA_MESSAGE = """
        <h2>Cancelación de cita</h2>
        <p>Estimado/a {0},</p>
        <p>Su cita programada para el día <strong>{1}</strong> a las <strong>{2}</strong> ha sido cancelada.</p>
        """;

    public static final String CITA_REAGENDADA_MESSAGE = """
        <h2>Reagendamiento de cita</h2>
        <p>Estimado/a {0},</p>
        <p>Su cita ha sido reagendada para el día <strong>{1}</strong> a las <strong>{2}</strong>.</p>
        """;

}
