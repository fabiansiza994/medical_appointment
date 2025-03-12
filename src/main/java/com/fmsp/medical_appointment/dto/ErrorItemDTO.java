package com.fmsp.medical_appointment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorItemDTO {
    private String codeError;
    private String codTypeError;
    private String messageError;

    public ErrorItemDTO(String codeError, String codTypeError, String messageError) {
        this.codeError = codeError;
        this.codTypeError = codTypeError;
        this.messageError = messageError;
    }

    public ErrorItemDTO() {
    }
}