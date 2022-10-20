package ru.tkachenko.buyerassistant.file_storage.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.property.FileStorageProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileDownloadService {
    private final Path FILE_STORAGE_LOCATION;
    private final Path ZIP_DIRECTORY;
    private final Path ZIP_FILE_PATH;

    public FileDownloadService(FileStorageProperties fileStorageProperties) {
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.ZIP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("forZip");
        this.ZIP_FILE_PATH = ZIP_DIRECTORY.resolve("allFiles.zip");
    }

    public ResponseEntity<Resource> getZipFileAsResources(List<Path> filesForZip, HttpServletRequest request) {
        String headerValues = null;
        String contentType = null;
        Resource resource = null;
        try {
            resource = new UrlResource(zipFiles(filesForZip).toUri());
            String fileName = resource.getFilename();
            headerValues = "attachment; filename=" + fileName;
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not determine file type");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }

    private Path zipFiles(List<Path> filesForZip) {
        try (FileOutputStream fos = new FileOutputStream(ZIP_FILE_PATH.toString());
             ZipOutputStream zipos = new ZipOutputStream(fos);) {
            filesForZip.forEach(e -> addZipEntryToZipOutputStream(e, zipos));
            return ZIP_FILE_PATH;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void addZipEntryToZipOutputStream(Path fileForZip, ZipOutputStream zipos) {
        try (FileInputStream fis = new FileInputStream(fileForZip.toString())) {
            ZipEntry zipEntry = new ZipEntry(fileForZip.getFileName().toString());
            zipos.putNextEntry(zipEntry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zipos.write(buffer);
            zipos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
