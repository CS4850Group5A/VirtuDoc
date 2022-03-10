package com.virtudoc.web.configuration;

import com.virtudoc.web.rendering.dialect.EmailDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Injectable class that allows for template processing outside of the normal MVC request flow.
 *
 * @author ARMmaster17
 */
@Configuration
public class TemplateConfiguration {
    @Autowired
    private EmailDialect emailDialect;

    @Bean
    public ITemplateResolver templateResolver()
    {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    @Bean
    @Primary
    public SpringTemplateEngine customTemplateEngine()
    {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        templateEngine.addDialect(emailDialect);
        return templateEngine;
    }
}
