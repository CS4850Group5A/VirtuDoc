package com.virtudoc.web.rendering.dialect;

import com.virtudoc.web.rendering.processor.EmailLinkProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class EmailDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Email Dialect";

    @Autowired
    private EmailLinkProcessor emailLinkProcessor;

    public EmailDialect() {
        super(DIALECT_NAME, "email", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(emailLinkProcessor);
        return processors;
    }
}
