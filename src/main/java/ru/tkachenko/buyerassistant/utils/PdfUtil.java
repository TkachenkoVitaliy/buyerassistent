package ru.tkachenko.buyerassistant.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class PdfUtil {
    public void createPDFFromImage( String inputFile, String imagePath, String outputFile )
            throws IOException
    {
        try (PDDocument doc = PDDocument.load(new File(inputFile)))
        {
            PDPage page = doc.getPage(0);
            PDRectangle mediaBox = page.getMediaBox();
            float topY= mediaBox.getHeight();


            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true))
            {
                contentStream.drawImage(pdImage, 0, 810);
            }

            doc.save(outputFile);
        }
    }
}
