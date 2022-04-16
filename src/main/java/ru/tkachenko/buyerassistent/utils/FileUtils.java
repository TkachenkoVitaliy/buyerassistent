package ru.tkachenko.buyerassistent.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.file_storage.exceptions.IllegalFileExtensionException;

public class FileUtils {

    public static boolean validateFileExtension(MultipartFile multipartFile) throws IllegalFileExtensionException {
        final String CORRECT_EXTENSION = ".xlsx";

        String fileName = multipartFile.getOriginalFilename();
        if (fileName.endsWith(CORRECT_EXTENSION)) {
            return true;
        } else {
            throw new IllegalFileExtensionException("Wrong Extension! File - " + multipartFile.getOriginalFilename());
        }
    }

}
