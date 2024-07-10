package findo.lab.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import findo.lab.service.PdfGeneratorService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final TemplateEngine templateEngine;

    @Override
    public ByteArrayInputStream generatePdf(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process("pdf/" + templateName, context); // Sertakan subdirektori

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, "");
            builder.toStream(outputStream);
            builder.run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}