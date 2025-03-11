package com.fmsp.medical_appointment.configuration.exceptionManager.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomServiceException extends RuntimeException {
    private String idTx;
    private String codeError;
    private String codTypeError;
    private String messageError;

    public CustomServiceException(String message, String idTx, String codeError, String codTypeError, String messageError) {
        super(message);
        this.idTx = idTx;
        this.codeError = codeError;
        this.codTypeError = codTypeError;
        this.messageError = messageError;
    }
}
