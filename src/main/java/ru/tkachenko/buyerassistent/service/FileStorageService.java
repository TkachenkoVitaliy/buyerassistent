package ru.tkachenko.buyerassistent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistent.property.FileStorageProperties;
import ru.tkachenko.buyerassistent.utils.CurrentDate;
import ru.tkachenko.buyerassistent.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

    private final Path FILE_STORAGE_LOCATION;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    public Path storeFile(MultipartFile mmkAccept) throws IllegalFileExtensionException{
        final String MMK_ACCEPT_STORAGE_FILENAME = "mmkAccept.xlsx";
        final Path TEMP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("temp");
        Path mmkAcceptDestinationPath = TEMP_DIRECTORY.resolve(MMK_ACCEPT_STORAGE_FILENAME);

        try {
            FileUtils.validateFileExtension(mmkAccept); //if extension is wrong throws WrongExtensionException and end this method
            Files.createDirectories(TEMP_DIRECTORY);
            Files.copy(mmkAccept.getInputStream(), mmkAcceptDestinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO нужно что-то придумать с этим возвращаемым значением (мб бросить ошибку)
        return mmkAcceptDestinationPath;
    }

    public List<Path> storeFiles(MultipartFile otherFactories, MultipartFile oracleMmk, MultipartFile dependenciesMmk)
    throws IllegalFileExtensionException {
        String otherFactoriesFileName = "otherFactories.xlsx";
        String oracleMmkFileName = "oracleMMK.xlsx";
        String dependenciesMmkFileName = "dependenciesMMK.xlsx";
        CurrentDate currentDate = new CurrentDate();
        String year = currentDate.getYear();
        String month = currentDate.getMonth();
        String day = currentDate.getDay();
        String time = currentDate.getTime();
        Path DESTINATION_DIRECTORY = FILE_STORAGE_LOCATION.resolve(year).resolve(month).resolve(day).resolve(time);

        try {
            FileUtils.validateFileExtension(otherFactories);
            FileUtils.validateFileExtension(oracleMmk);
            FileUtils.validateFileExtension(dependenciesMmk);

            Files.createDirectories(DESTINATION_DIRECTORY);

            Path otherFactoriesPath = DESTINATION_DIRECTORY.resolve(otherFactoriesFileName);
            Files.copy(otherFactories.getInputStream(), otherFactoriesPath, StandardCopyOption.REPLACE_EXISTING);

            Path oracleMmkPath = DESTINATION_DIRECTORY.resolve(oracleMmkFileName);
            Files.copy(oracleMmk.getInputStream(), oracleMmkPath, StandardCopyOption.REPLACE_EXISTING);

            Path dependenciesMmkPath = DESTINATION_DIRECTORY.resolve(dependenciesMmkFileName);
            Files.copy(dependenciesMmk.getInputStream(), dependenciesMmkPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO return List<Path>
        return null;
    }
}
