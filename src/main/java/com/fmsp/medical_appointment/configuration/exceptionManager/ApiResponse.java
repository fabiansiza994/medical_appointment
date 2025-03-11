package com.fmsp.medical_appointment.configuration.exceptionManager;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    private String idTx;
    private String processStatus;
    private T data;
    private String message;
    private List<ErrorItemDTO> errors;

    public ApiResponse(String idTx, String processStatus, String message, List<ErrorItemDTO> errors) {
        this.idTx = idTx;
        this.processStatus = processStatus;
        this.message = message;
        this.errors = errors;
    }

    public ApiResponse(String idTx, String processStatus,T data, String message) {
        this.idTx = idTx;
        this.processStatus = processStatus;
        this.message = message;
        this.data = data;
    }

}
