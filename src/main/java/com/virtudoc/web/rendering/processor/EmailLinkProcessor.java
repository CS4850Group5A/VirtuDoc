package com.virtudoc.web.rendering.processor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Objects;

@Component
public class EmailLinkProcessor extends AbstractAttributeTagProcessor {
    private static final String ATTR_NAME = "relink";
    private static final int PRECEDENCE = 1000;

    @Value("${test.rooturl}")
    private String rootUrl;

    public EmailLinkProcessor() {
        super(TemplateMode.HTML, "email", null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName, final String attributeValue, final IElementTagStructureHandler structureHandler) {
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
        final String relativeUrl = (String)expression.execute(context);
        if (Objects.equals(tag.getElementCompleteName(), "a") && tag.getAttribute("href") == null) {
            structureHandler.setAttribute("href", getFullyQualifiedUrl(relativeUrl));
        } else if (Objects.equals(tag.getElementCompleteName(), "img") && tag.getAttribute("src") == null) {
            structureHandler.setAttribute("src", getFullyQualifiedUrl(relativeUrl));
        }
    }

    private String getFullyQualifiedUrl(String relativeUrl) {
        return rootUrl + relativeUrl;
    }
}
