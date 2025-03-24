package com.fmsp.medical_appointment.configuration;

import com.fmsp.medical_appointment.service.task.JobMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Slf4j
@Configuration
public class QuartzConfiguration {

    @Value("${quartz.jobCron}")
    private String jobCron;

    @Value("${quartz.jobGroup}")
    private String jobGroup;

    @Bean
    CronTriggerFactoryBean mailTrigger() { // Fire
        log.info( "jobCron "+ jobCron);
        log.info( "jobGroup "+ jobGroup);
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(mailJob().getObject());
        cronTriggerFactoryBean.setCronExpression(jobCron);
        cronTriggerFactoryBean.setGroup(jobGroup);
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean mailJob() { // Tarea
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobMailService.class);
        jobDetailFactoryBean.setGroup(jobGroup);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }
}
