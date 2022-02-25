package com.virtudoc.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthEndpointTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHealthEndpoint() throws Exception {
        String body = this.restTemplate.getForObject("http://localhost:" + port + "/debug/health", String.class);
        assert(body.equals("{\"health\":\"OK\"}"));
    }
}
