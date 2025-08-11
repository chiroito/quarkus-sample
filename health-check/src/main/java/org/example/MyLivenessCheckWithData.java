package org.example;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class MyLivenessCheckWithData implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder();
        return builder.name("healthcheckWithData").up().withData("key1", "value1").withData("key2", 1L).build();
    }
}