package com.fmsp.medical_appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmsp.medical_appointment.entity.enums.EstadoCita;
import com.fmsp.medical_appointment.entity.enums.MetodoPago;
import com.fmsp.medical_appointment.entity.enums.TipoCita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "idPaciente", "idMedico", "idEspecialidad", "fechaHora" }, allowSetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitarCitaDTO {
    private String idTx;
    private Long idPaciente;
    private Long idMedico;
    private Long idEspecialidad;
    private LocalDateTime fechaHora;
    private TipoCita tipoCita;
    private MetodoPago metodoPago;
    private Double valorCita;
    private EstadoCita estado;
}