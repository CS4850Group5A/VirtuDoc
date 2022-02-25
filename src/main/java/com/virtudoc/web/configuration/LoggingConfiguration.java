package com.virtudoc.web.configuration;

import com.virtudoc.web.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global logger instance. Writes information to STDOUT for capture by Papertrail (or some other logging platform).
 *
 * @author ARMmaster17
 */
@Configuration
public class LoggingConfiguration {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(WebApplication.class);
    }
}
