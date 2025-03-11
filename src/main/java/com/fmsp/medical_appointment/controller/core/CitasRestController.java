package com.fmsp.medical_appointment.controller.core;

import com.fmsp.medical_appointment.configuration.exceptionManager.ApiResponse;
import com.fmsp.medical_appointment.configuration.exceptionManager.ResponseHandler;
import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.entity.enums.TipoCita;
import com.fmsp.medical_appointment.service.core.citas.ISolicitarCita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CitasRestController implements ICitasRestController{

    private final ISolicitarCita citaService;

    public CitasRestController(ISolicitarCita citaService) {
        this.citaService = citaService;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> test(SolicitarCitaDTO solicitarCitaDTO) {
        UUID idTest = UUID.randomUUID();

        solicitarCitaDTO.setIdTx(idTest.toString());

        List<ErrorItemDTO> errors = new ArrayList<>();

        if(solicitarCitaDTO.getTipoCita() == TipoCita.valueOf("PRESENCIAL")){
            errors.add(new ErrorItemDTO("E001", "404", "error"));
        }

        if(!errors.isEmpty()){
            return ResponseHandler.badRequestResponse(errors, idTest.toString());
        }

        var response = citaService.solicitarCita(solicitarCitaDTO);
        return ResponseHandler.successResponse(response, idTest.toString());
    }
}
