package com.fmsp.medical_appointment.controller.core;

import com.fmsp.medical_appointment.configuration.exceptionManager.ApiResponse;
import com.fmsp.medical_appointment.configuration.exceptionManager.ResponseHandler;
import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.service.core.citas.ICancelarCita;
import com.fmsp.medical_appointment.service.core.citas.ISolicitarCita;
import com.fmsp.medical_appointment.util.ValidateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CitasRestController implements ICitasRestController{

    private final ICancelarCita cancelarCita;
    private final ISolicitarCita citaService;
    private final ValidateRequest validateRequest;

    public CitasRestController(ICancelarCita cancelarCita, ISolicitarCita citaService, ValidateRequest validateRequest) {
        this.cancelarCita = cancelarCita;
        this.citaService = citaService;
        this.validateRequest = validateRequest;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> agendarCita(@RequestBody SolicitarCitaDTO solicitarCitaDTO) {
        UUID idTest = UUID.randomUUID();

        solicitarCitaDTO.setIdTx(idTest.toString());

        List<ErrorItemDTO> errors = validateRequest.validateRequest(solicitarCitaDTO);

        if(!errors.isEmpty()){
            return ResponseHandler.badRequestResponse(errors, idTest.toString());
        }

        var response = citaService.solicitarCita(solicitarCitaDTO);
        return ResponseHandler.successResponse(response, idTest.toString());
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<Object>> cancelarCita(@RequestBody SolicitarCitaDTO solicitarCitaDTO) {
        UUID idTest = UUID.randomUUID();

        solicitarCitaDTO.setIdTx(idTest.toString());

        List<ErrorItemDTO> errors = validateRequest.validateRequest(solicitarCitaDTO);

        if(!errors.isEmpty()){
            return ResponseHandler.badRequestResponse(errors, idTest.toString());
        }

        var response = cancelarCita.cancelarCita(solicitarCitaDTO);
        return ResponseHandler.successResponse(response, idTest.toString());
    }
}