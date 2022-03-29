package com.virtudoc.web;

import com.lowagie.text.DocumentException;
import com.virtudoc.web.service.PDFGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PDFGeneratorServiceTests {
    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Test
    public void testGeneratePDF() {
        assertNotNull(pdfGeneratorService.templateEngine);
    }

    @Test
    public void testGeneratePDFWithTemplate() throws MalformedURLException, DocumentException, UnsupportedEncodingException {
        Map<String, Object> model = new HashMap<>();
        ByteArrayOutputStream outputStream = (ByteArrayOutputStream)pdfGeneratorService.generatePDF(model, "test/pdftest.html");
        assertNotNull(outputStream);
        byte[] pdfData = outputStream.toByteArray();
        assertTrue(pdfData.length > 0);

    }
}
