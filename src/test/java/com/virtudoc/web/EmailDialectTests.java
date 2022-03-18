package com.virtudoc.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailDialectTests {
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Test
    public void replacesStaticLink() {
        Context templateEngineContext = new Context(Locale.getDefault(), null);
        String result =  this.templateEngine.process("test/linkinjection.html", templateEngineContext);
        assertEquals("<a href=\"http://localhost:8080/test/page\"></a>", result);
    }

    @Test
    public void replacesModelLink() {
        Map<String, Object> model = new HashMap<>();
        model.put("dynamiclink", "/test/page");
        Context templateEngineContext = new Context(Locale.getDefault(), model);
        String result =  this.templateEngine.process("test/linkinjection2.html", templateEngineContext);
        assertEquals("<a href=\"http://localhost:8080/test/page\"></a>", result);
    }

    @Test
    public void replacesStaticImage() {
        Context templateEngineContext = new Context(Locale.getDefault(), null);
        String result =  this.templateEngine.process("test/imginjection.html", templateEngineContext);
        assertEquals("<img src=\"http://localhost:8080/img/test.png\" />", result);
    }

    @Test
    public void replacesModelImage() {
        Map<String, Object> model = new HashMap<>();
        model.put("dynamiclink", "/img/test.png");
        Context templateEngineContext = new Context(Locale.getDefault(), model);
        String result =  this.templateEngine.process("test/imginjection2.html", templateEngineContext);
        assertEquals("<img src=\"http://localhost:8080/img/test.png\" />", result);
    }
}
