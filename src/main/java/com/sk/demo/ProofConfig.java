package com.sk.demo;

import com.sk.demo.web.ProofController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Configuration
public class ProofConfig extends ResourceConfig{

    public ProofConfig() {
        register(ProofController.class);
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                if (HttpMethod.PUT.equals(request.getMethod())
                        && ((request.getContentType().equals(MediaType.TEXT_PLAIN)) || request.getContentType().equals(MediaType.APPLICATION_JSON))) {
                    filterChain.doFilter(request, response);
                } else {
                    super.doFilterInternal(request, response, filterChain);
                }
            }
        };
    }
}
