package ru.tkachenko.buyerassistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistent.service.FileStorageService;
import ru.tkachenko.buyerassistent.service.mmk_accept.MmkAcceptService;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@RestController
public class MainController {

    private final FileStorageService fileStorageService;
    private final MmkAcceptService mmkAcceptService;

    @Autowired
    public MainController(FileStorageService fileStorageService, MmkAcceptService mmkAcceptService) {
        this.fileStorageService = fileStorageService;
        this.mmkAcceptService = mmkAcceptService;
    }

    @PostMapping("/uploadAccept")
    public String uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        //added sout new Date() for check function time
        try {
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return "Accept Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/uploadMultipleFiles")
    public String uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                      @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                      @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk) {
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk,dependenciesMmk);
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Files Uploaded";
    }

}
