package ru.tkachenko.buyerassistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistent.service.FileStorageService;
import ru.tkachenko.buyerassistent.service.mmk_accept.MmkAcceptService;
import ru.tkachenko.buyerassistent.service.summary.SummaryService;
import ru.tkachenko.buyerassistent.utils.TimerUtil;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@RestController
public class MainController {

    private final FileStorageService fileStorageService;
    private final MmkAcceptService mmkAcceptService;
    private final SummaryService summaryService;

    @Autowired
    public MainController(FileStorageService fileStorageService, MmkAcceptService mmkAcceptService,
                          SummaryService summaryService) {
        this.fileStorageService = fileStorageService;
        this.mmkAcceptService = mmkAcceptService;
        this.summaryService = summaryService;
    }

    @PostMapping("/uploadAccept")
    public String uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        TimerUtil timerUtil = new TimerUtil();
        try {
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return "Accept Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            timerUtil.consoleLogTime("uploadAccept");
        }
    }

    @PostMapping("/uploadMultipleFiles")
    public String uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                      @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                      @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk) {
        TimerUtil timerUtil = new TimerUtil();
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk,dependenciesMmk);
            summaryService.parseFilesToSummary(savedFilesPaths);
            return "Files Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            timerUtil.consoleLogTime("uploadMultipleFiles");
        }

    }

}
