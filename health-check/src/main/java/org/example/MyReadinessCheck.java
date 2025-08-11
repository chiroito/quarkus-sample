package org.example;

import org.eclipse.microprofile.health.*;

@Readiness
public class MyReadinessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder();
        return builder.name("a").up().build();
    }

}