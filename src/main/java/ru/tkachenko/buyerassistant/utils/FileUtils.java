package ru.tkachenko.buyerassistant.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static boolean validateFileExtensionXlsx(MultipartFile multipartFile) throws IllegalFileExtensionException {
        final String CORRECT_EXTENSION = ".xlsx";
        return validateFileExtension(CORRECT_EXTENSION, multipartFile);
    }

    public static boolean validateFileExtensionXls(MultipartFile multipartFile) throws IllegalFileExtensionException {
        final String CORRECT_EXTENSION = ".xls";
        return validateFileExtension(CORRECT_EXTENSION, multipartFile);
    }

    private static boolean validateFileExtension (String extension, MultipartFile multipartFile) throws IllegalFileExtensionException {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName != null && fileName.endsWith(extension)) {
            return true;
        }
        if(fileName == null) {
            throw new IllegalFileExtensionException("File doesn't have name");
        }else {
            throw new IllegalFileExtensionException("Wrong Extension! File - " + multipartFile.getOriginalFilename());
        }
    }

    public static void cleanDirectory(Path directoryForClean) {
        try {
            File file = new File(directoryForClean.toString());
            File[] filesInDirectory = file.listFiles();
            if(filesInDirectory != null) {
                for (File currentFile : filesInDirectory) {
                    if (!currentFile.isDirectory()) {
                        Files.deleteIfExists(currentFile.toPath());
                    } else {
                        cleanDirectory(currentFile.toPath());
                        Files.deleteIfExists(currentFile.toPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
