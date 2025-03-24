package com.fmsp.medical_appointment.service.task;

import com.fmsp.medical_appointment.service.core.citas.CitaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobMailService implements Job {

    private final CitaServiceImpl citaService;

    public JobMailService(CitaServiceImpl citaService) {
        this.citaService = citaService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Ejecutando Job de recordatorio de citas próximas");
        citaService.enviarRecordatorioCitasProximas();
    }
}
