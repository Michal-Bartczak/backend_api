package com.magazyn.backendapi.service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ShippingLabelServiceImpl implements ShippingLabelService {

    @Override
    public byte[] createPdf(String trackingNumber) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Label for Tracking Number: " + trackingNumber));
            // Tutaj możesz dodać więcej danych do dokumentu PDF
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}