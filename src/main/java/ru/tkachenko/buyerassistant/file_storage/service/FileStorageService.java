package ru.tkachenko.buyerassistant.file_storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistant.property.FileStorageProperties;
import ru.tkachenko.buyerassistant.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
    private final Path TEMP_DIRECTORY;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        Path FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.TEMP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("temp");
    }

    public Path storeFile(MultipartFile mmkAccept) throws IllegalFileExtensionException {
        final String MMK_ACCEPT_STORAGE_FILENAME = "mmkAccept.xls";
        Path mmkAcceptDestinationPath = TEMP_DIRECTORY.resolve(MMK_ACCEPT_STORAGE_FILENAME);

        try {
            FileUtils.validateFileExtensionXls(mmkAccept);
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
            FileUtils.validateFileExtensionXlsx(otherFactories);
            FileUtils.validateFileExtensionXlsx(oracleMmk);
            FileUtils.validateFileExtensionXlsx(dependenciesMmk);

            Files.createDirectories(TEMP_DIRECTORY);

            savedFilesPaths.add(saveFileToFileSystem(otherFactories, otherFactoriesFileName));
            savedFilesPaths.add(saveFileToFileSystem(oracleMmk, oracleMmkFileName));
            savedFilesPaths.add(saveFileToFileSystem(dependenciesMmk, dependenciesMmkFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedFilesPaths;
    }

    private Path saveFileToFileSystem(MultipartFile file, String fileNameForSave) throws IOException {
        Path savedFilePath = TEMP_DIRECTORY.resolve(fileNameForSave);
        Files.copy(file.getInputStream(), savedFilePath, StandardCopyOption.REPLACE_EXISTING);
        return savedFilePath;
    }
}
