package com.fpt.MidtermG1.util;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PDFUtils {

    private final SpringTemplateEngine springTemplateEngine;

    public byte[] generateAllInvoicesPDF(List<InvoiceProduct> invoiceProducts) throws IOException {
        Context context = new Context();
        context.setVariable("invoiceProducts", invoiceProducts);
    
        String htmlContent = springTemplateEngine.process("invoice-template", context);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, baos);
        return baos.toByteArray();
    }
    
}