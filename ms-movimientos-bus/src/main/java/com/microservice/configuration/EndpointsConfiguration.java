package com.microservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "uri.endpoints")
public class EndpointsConfiguration {
    private String obtenerClienteIdentificacion;
    private String obtenerClienteId;
}
