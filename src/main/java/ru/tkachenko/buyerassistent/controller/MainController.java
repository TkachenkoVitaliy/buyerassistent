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

@RestController
public class MainController {

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    MmkAcceptService mmkAcceptService;

    @PostMapping("/uploadAccept")
    public String uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        //added sout new Date() for check function time
        try{
            System.out.println(new Date());
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return "Accept Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return "Wrong Extension! File - " + mmkAccept.getOriginalFilename();
        }
        finally {
            System.out.println(new Date());
        }
    }

}
