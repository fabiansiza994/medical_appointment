package com.fmsp.medical_appointment.configuration.exceptionManager;

import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import com.fmsp.medical_appointment.entity.enums.EstadoProceso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseHandler {

    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T response, String idTx){
        ApiResponse<T> apiResponse = new ApiResponse<>(idTx, EstadoProceso.PS.getValor(), response, "EXITO");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequestResponse(List<ErrorItemDTO> itemDTOList, String idTx){
        ApiResponse<T> apiResponse = new ApiResponse<>(idTx, EstadoProceso.PF.getValor(), "ERROR", itemDTOList);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
