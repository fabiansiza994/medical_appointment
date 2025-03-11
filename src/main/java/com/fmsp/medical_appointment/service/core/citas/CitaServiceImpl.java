package com.fmsp.medical_appointment.service.core.citas;

import com.fmsp.medical_appointment.configuration.exceptionManager.exceptions.CustomServiceException;
import com.fmsp.medical_appointment.dto.SolicitarCitaDTO;
import com.fmsp.medical_appointment.entity.core.oracle.Cita;
import com.fmsp.medical_appointment.repository.jpa.oracle.CitaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements ISolicitarCita, ICancelarCita {

    private final CitaRepository citaRepository;
    private final ModelMapper modelMapper;

    public CitaServiceImpl(CitaRepository citaRepository, ModelMapper modelMapper) {
        this.citaRepository = citaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SolicitarCitaDTO solicitarCita(SolicitarCitaDTO solicitarCita) {

        if(solicitarCita.getValorCita() == null){
            throw new CustomServiceException(super.toString(), solicitarCita.getIdTx(), "E001", "400", "valor no corresponde");
        }
        var cita = modelMapper.map(solicitarCita, Cita.class);
        var citaDB = citaRepository.save(cita);
        return modelMapper.map(citaDB, SolicitarCitaDTO.class);
    }

    @Override
    public SolicitarCitaDTO cancelarCita(SolicitarCitaDTO solicitarCita) {
        return null;
    }
}
