package com.fmsp.medical_appointment.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

   /* @Bean
    OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Servicios de reclamos")
                        .version(appVersion)
                        .description("Servicios de gestión de reclamos")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }*/
}
