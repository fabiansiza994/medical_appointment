package com.fmsp.medical_appointment.controller.core;

import com.fmsp.medical_appointment.configuration.exceptionManager.ApiResponse;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/citas")
public interface ICitasRestController {

    @PostMapping("/solicitar")
    ResponseEntity<ApiResponse<Object>> agendarCita(@RequestBody SolicitarCitaDTO solicitarCitaDTO);
}
