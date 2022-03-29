package com.virtudoc.web.service;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.util.Map;

@Service
public class PDFGeneratorService {
    @Autowired
    public SpringTemplateEngine templateEngine;

    public OutputStream generatePDF(Map<String, Object> model, String templateName) throws UnsupportedEncodingException, MalformedURLException, DocumentException {
        Context context = new Context();

        context.setVariables(model);


        String htmlContentToRender = templateEngine.process(templateName, context);
        String xHtml = xhtmlConvert(htmlContentToRender);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources","templates")
                .toUri()
                .toURL()
                .toString();
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);

        return outputStream;
    }

    private String xhtmlConvert(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");
    }
}
