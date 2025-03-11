package com.fmsp.medical_appointment;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedicalAppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalAppointmentApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper;
	}

}
