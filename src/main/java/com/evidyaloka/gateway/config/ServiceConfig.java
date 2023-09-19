package com.evidyaloka.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import java.io.IOException;

@Configuration
public class ServiceConfig {

    @Bean(name = "rcClient")
    public WebClient rcClient() throws IOException {
        return WebClient.builder()
                .baseUrl("http://43.204.25.161:8081/api/v1")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap(true)
                ))
                .build();
    }
}
