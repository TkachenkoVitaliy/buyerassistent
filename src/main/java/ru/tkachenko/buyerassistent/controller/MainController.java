package ru.tkachenko.buyerassistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.service.FileStorageService;
import ru.tkachenko.buyerassistent.service.mmk_accept.MmkAcceptService;

import java.nio.file.Path;

@RestController
public class MainController {

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    MmkAcceptService mmkAcceptService;

    @PostMapping("/uploadAccept")
    public String uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        Path mmkAcceptPath = fileStorageService.storeFiles(mmkAccept);
        mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
        return "Accept Uploaded";
    }

}
