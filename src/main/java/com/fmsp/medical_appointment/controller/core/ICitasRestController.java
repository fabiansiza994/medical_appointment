package com.fmsp.medical_appointment.controller.core;

import com.fmsp.medical_appointment.configuration.exceptionManager.ApiResponse;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/citas")
public interface ICitasRestController {

    @Operation(
            summary = "Solicitar una cita médica",
            description = "Permite al paciente agendar una cita médica con el especialista disponible.",
            requestBody = @RequestBody(
                    description = "Datos necesarios para agendar la cita",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SolicitarCitaDTO.class)
                    )
            )
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cita agendada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/solicitar")
    ResponseEntity<ApiResponse<Object>> agendarCita(SolicitarCitaDTO solicitarCitaDTO);

    @Operation(
            summary = "Cancelar una cita médica",
            description = "Permite cancelar una cita previamente agendada.",
            requestBody = @RequestBody(
                    description = "Datos necesarios para cancelar la cita",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SolicitarCitaDTO.class)
                    )
            )
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cita cancelada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/cancelar")
    ResponseEntity<ApiResponse<Object>> cancelarCita(SolicitarCitaDTO solicitarCitaDTO);
}