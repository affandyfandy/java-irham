package findo.lab.service;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface PdfGeneratorService {
    ByteArrayInputStream generatePdf(String templateName, Map<String, Object> data);
}