package ru.tkachenko.buyerassistent.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.exceptions.WrongExtensionException;

public class FileUtils {

    public static boolean validateFileExtension (MultipartFile multipartFile) throws WrongExtensionException {
        final String CORRECT_EXTENSION = ".xlsx";

        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(fileExtension.equals(CORRECT_EXTENSION)) {
            return true;
        } else {
            throw new WrongExtensionException("Wrong Extension! File - " + multipartFile.getOriginalFilename());
        }
    }

}
