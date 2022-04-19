package ru.tkachenko.buyerassistent.file_storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistent.property.FileStorageProperties;
import ru.tkachenko.buyerassistent.utils.CurrentDate;
import ru.tkachenko.buyerassistent.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileStorageService {

    private final Path FILE_STORAGE_LOCATION;
    private final Path TEMP_DIRECTORY;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.TEMP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("temp");
    }

    public Path storeFile(MultipartFile mmkAccept) throws IllegalFileExtensionException {
        final String MMK_ACCEPT_STORAGE_FILENAME = "mmkAccept.xlsx";
        Path mmkAcceptDestinationPath = TEMP_DIRECTORY.resolve(MMK_ACCEPT_STORAGE_FILENAME);

        try {
            FileUtils.validateFileExtension(mmkAccept);
            Files.createDirectories(TEMP_DIRECTORY);
            Files.copy(mmkAccept.getInputStream(), mmkAcceptDestinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mmkAcceptDestinationPath;
    }

    public List<Path> storeFiles(MultipartFile otherFactories, MultipartFile oracleMmk, MultipartFile dependenciesMmk)
            throws IllegalFileExtensionException {
        String otherFactoriesFileName = "otherFactories.xlsx";
        String oracleMmkFileName = "oracleMMK.xlsx";
        String dependenciesMmkFileName = "dependenciesMMK.xlsx";

        List<Path> savedFilesPaths = new ArrayList<>();

        try {
            FileUtils.validateFileExtension(otherFactories);
            FileUtils.validateFileExtension(oracleMmk);
            FileUtils.validateFileExtension(dependenciesMmk);

            Files.createDirectories(TEMP_DIRECTORY);

            Path otherFactoriesPath = TEMP_DIRECTORY.resolve(otherFactoriesFileName);
            Files.copy(otherFactories.getInputStream(), otherFactoriesPath, StandardCopyOption.REPLACE_EXISTING);
            savedFilesPaths.add(otherFactoriesPath);

            Path oracleMmkPath = TEMP_DIRECTORY.resolve(oracleMmkFileName);
            Files.copy(oracleMmk.getInputStream(), oracleMmkPath, StandardCopyOption.REPLACE_EXISTING);
            savedFilesPaths.add(oracleMmkPath);

            Path dependenciesMmkPath = TEMP_DIRECTORY.resolve(dependenciesMmkFileName);
            Files.copy(dependenciesMmk.getInputStream(), dependenciesMmkPath, StandardCopyOption.REPLACE_EXISTING);
            savedFilesPaths.add(dependenciesMmkPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedFilesPaths;
    }
}
