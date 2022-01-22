package com.virtudoc.web.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for a send email operation. Implemented as a DTO to allow for future serialization and
 * transmission to backend or another thread. Validation of DTO objects must be performed externally.
 *
 * @author wordm
 */
public class EmailDTO {
    @Email
    private final String recipient;

    @NotBlank
    @Size(min = 2, max = 998) // Limitation specified in RFC 2822
    private final String subject;

    @NotBlank
    private final String templatePath;

    @NotNull
    private final Map<String, ? extends Object> templateVariables;

    /**
     * Creates an email Data Transfer Object (DTO).
     * @param recipient Address to send the email message to.
     * @param subject Subject line for email message.
     * @param templatePath Relative path to the HTML template file to use for the message body.
     */
    public EmailDTO(final String recipient, final String subject, final String templatePath) {

        this.subject = subject;
        this.recipient = recipient;
        this.templatePath = templatePath;
        this.templateVariables = new HashMap<String, String>();

    }

    /**
     * Creates an email Data Transfer Object (DTO).
     * @param recipient Address to send the email message to.
     * @param subject Subject line for email message.
     * @param templatePath Relative path to the HTML template file to use for the message body.
     * @param templateVariables List of variable substitutions to use when rendering the HTML template.
     */
    public EmailDTO(final String recipient, final String subject, final String templatePath, final Map<String, ? extends Object> templateVariables) {

        this.subject = subject;
        this.recipient = recipient;
        this.templatePath = templatePath;
        this.templateVariables = templateVariables;
    }

    public final String getRecipient() {
        return recipient;
    }

    public final String getSubject() {
        return subject;
    }

    public final String getTemplatePath() {
        return templatePath;
    }

    public final Map<String, Object> getTemplateVariables() {
        Map<String, Object> convertedMap = new HashMap<>(templateVariables);
        return convertedMap;
    }
}
