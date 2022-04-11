package ru.tkachenko.buyerassistent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.property.FileStorageProperties;
import ru.tkachenko.buyerassistent.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path FILE_STORAGE_LOCATION;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    public Path storeFiles(MultipartFile mmkAccept) {
        final String MMK_ACCEPT_STORAGE_FILENAME = "mmkAccept.xlsx";
        final Path TEMP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("temp");
        Path mmkAcceptDestinationPath = TEMP_DIRECTORY.resolve(MMK_ACCEPT_STORAGE_FILENAME);

        FileUtils.validateFileExtension(mmkAccept); //if extension is wrong throws WrongExtensionException and end this method
        try {
            Files.createDirectories(TEMP_DIRECTORY);
            Files.copy(mmkAccept.getInputStream(), mmkAcceptDestinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO нужно что-то придумать с этим возвращаемым значением (мб бросить ошибку)
        return mmkAcceptDestinationPath;
    }
}
