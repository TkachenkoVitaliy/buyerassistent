package ru.tkachenko.buyerassistant.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.property.FileStorageProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfUtil {
    private final Path LOA_DIRECTORY;
    private final String templateName = "template.xls";
    private final String createdLoa = "Доверенность.xls";
    private final String createdLoadPdf = "Доверенность.pdf";

    public PdfUtil(FileStorageProperties fileStorageProperties) {
        this.LOA_DIRECTORY = Paths.get(fileStorageProperties.getLoaDir()).toAbsolutePath().normalize();
    }


    public void createPDFFromImage( String inputFile, String outputFile ) throws IOException {
        try (PDDocument doc = PDDocument.load(new File(inputFile))) {
            PDPage page = doc.getPage(0);
            PDRectangle mediaBox = page.getMediaBox();
            float topY= mediaBox.getHeight();

            String imagePath = LOA_DIRECTORY.resolve("image.png").toString();

            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true))
            {
                contentStream.drawImage(pdImage, 0, 810);
            }

            doc.save(outputFile);
        }
    }
}
