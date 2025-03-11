package com.fmsp.medical_appointment.configuration.exceptionManager;

import com.fmsp.medical_appointment.configuration.exceptionManager.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomServiceException(CustomServiceException e) {
        ErrorItemDTO errorItemDTO = new ErrorItemDTO(e.getCodeError(), e.getCodTypeError(), e.getMessageError());
        return ResponseHandler.badRequestResponse(Collections.singletonList(errorItemDTO), e.getIdTx());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorItemDTO errorItemDTO = new ErrorItemDTO("E001", "404", e.getMessage());
        return ResponseHandler.badRequestResponse(Collections.singletonList(errorItemDTO), "- -");
    }

}
