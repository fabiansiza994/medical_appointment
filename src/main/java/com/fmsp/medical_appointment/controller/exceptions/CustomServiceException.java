package com.fmsp.medical_appointment.controller.exceptions;

public class CustomServiceException extends RuntimeException {
    private String idTx;
    private String codeError;
    private String codTypeError;
    private String messageError;

    public String getIdTx() {
        return idTx;
    }

    public void setIdTx(String idTx) {
        this.idTx = idTx;
    }

    public String getCodeError() {
        return codeError;
    }

    public void setCodeError(String codeError) {
        this.codeError = codeError;
    }

    public String getCodTypeError() {
        return codTypeError;
    }

    public void setCodTypeError(String codTypeError) {
        this.codTypeError = codTypeError;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public CustomServiceException(String message, String idTx, String codeError, String codTypeError, String messageError) {
        super(message);
        this.idTx = idTx;
        this.codeError = codeError;
        this.codTypeError = codTypeError;
        this.messageError = messageError;
    }
}
