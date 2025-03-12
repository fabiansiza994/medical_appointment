package com.fmsp.medical_appointment.util;

import com.fmsp.medical_appointment.dto.ErrorItemDTO;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.entity.enums.Especialidad;
import com.fmsp.medical_appointment.entity.enums.PrecioCitas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateRequest {

    public List<ErrorItemDTO> validateRequest(SolicitarCitaDTO request) {
        List<ErrorItemDTO> errors = new ArrayList<>();

        validarEspecialidad(errors, request);

        return errors;
    }

    private void validarEspecialidad(List<ErrorItemDTO> errors, SolicitarCitaDTO request) {
        var especialidad = Especialidad.getByIndice(Math.toIntExact(request.getIdEspecialidad()));
        var precio = PrecioCitas.getByName(especialidad.name());

        if(especialidad.getIndice().longValue() != request.getIdEspecialidad()) {
            errors.add(new ErrorItemDTO("E004", "400", "no existe la especialidad"));
        }

        if(request.getValorCita() < precio.getValor()){
            errors.add(new ErrorItemDTO("E004", "400", "monto insuficiente"));
        }
    }
}
