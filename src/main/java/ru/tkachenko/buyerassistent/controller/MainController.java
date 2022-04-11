package ru.tkachenko.buyerassistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.service.FileStorageService;

import java.nio.file.Path;

@RestController
public class MainController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/uploadAccept")
    public String uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        Path mmkAcceptPath = fileStorageService.storeFiles(mmkAccept);
        return "Accept Uploaded";
    }

}
