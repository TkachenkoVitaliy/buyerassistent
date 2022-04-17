package ru.tkachenko.buyerassistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistent.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistent.file_storage.service.FileStorageService;
import ru.tkachenko.buyerassistent.mmk_accept.service.MmkAcceptService;
import ru.tkachenko.buyerassistent.summary.service.SummaryService;
import ru.tkachenko.buyerassistent.utils.TimerUtil;

import java.nio.file.Path;
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
        //TODO remove timer
        TimerUtil timerUtil = new TimerUtil();
        //TODO remove timer
        try {
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return "Accept Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally { //TODO remove timer
            timerUtil.consoleLogTime("uploadAccept");
        } //TODO remove timer
    }

    @PostMapping("/uploadMultipleFiles")
    public String uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                      @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                      @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk) {
        //TODO remove timer
        TimerUtil timerUtil = new TimerUtil();
        //TODO remove timer
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk,dependenciesMmk);
            summaryService.parseFilesToSummary(savedFilesPaths);
            return "Files Uploaded";
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally { //TODO remove timer
            timerUtil.consoleLogTime("uploadMultipleFiles");
        } //TODO remove timer
    }
}
