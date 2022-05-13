package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistant.file_storage.service.FileDownloadService;
import ru.tkachenko.buyerassistant.file_storage.service.FileStorageService;
import ru.tkachenko.buyerassistant.mmk_accept.service.MmkAcceptService;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;
import ru.tkachenko.buyerassistant.summary.service.SummaryService;
import ru.tkachenko.buyerassistant.utils.TimerUtil;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.List;

@RestController
public class MainController {

    private final FileStorageService fileStorageService;
    private final FileDownloadService fileDownloadService;
    private final MmkAcceptService mmkAcceptService;
    private final SummaryService summaryService;

    private final BranchStartMonthService branchStartMonthService;

    @Autowired
    public MainController(FileStorageService fileStorageService, FileDownloadService fileDownloadService, MmkAcceptService mmkAcceptService,
                          SummaryService summaryService, BranchStartMonthService branchStartMonthService) {
        this.fileStorageService = fileStorageService;
        this.fileDownloadService = fileDownloadService;
        this.mmkAcceptService = mmkAcceptService;
        this.summaryService = summaryService;
        this.branchStartMonthService = branchStartMonthService;
    }

    @PostMapping("/uploadAccept")
    public ModelAndView uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept, Model model) {
        //TODO remove timer
        TimerUtil timerUtil = new TimerUtil();
        //TODO remove timer
        try {
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return createUserResponse(model, "Accept Uploaded");
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return createUserResponse(model, e.getMessage());
        } finally { //TODO remove timer
            timerUtil.consoleLogTime("uploadAccept");
        } //TODO remove timer
    }

    @PostMapping("/uploadMultipleFiles")
    public ModelAndView uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                      @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                      @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk, Model model) {
        //TODO remove timer
        TimerUtil timerUtil = new TimerUtil();
        //TODO remove timer
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk, dependenciesMmk);
            summaryService.parseFilesToSummary(savedFilesPaths);
            return createUserResponse(model, "Files Uploaded");
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return createUserResponse(model, e.getMessage());
        } finally { //TODO remove timer
            timerUtil.consoleLogTime("upload and write to DB MultipleFiles");
        } //TODO remove timer
    }

    @GetMapping("/downloadAllFiles")
    public ResponseEntity<Resource> downloadAllFiles(HttpServletRequest request) {
        //TODO remove timer
        TimerUtil timerUtilDownloadAllFiles = new TimerUtil();
        //TODO remove timer
        try {
            List<Path> createdBranchesFiles = summaryService.createAllBranchesFiles();
            return fileDownloadService.getZipFileAsResources(createdBranchesFiles, request);
        } finally {
            timerUtilDownloadAllFiles.consoleLogTime("createAllFiles");
        }
    }

    @GetMapping("/settings")
    public ModelAndView settingsPage(Model model) {
        List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings");
        return modelAndView;
    }

    @GetMapping("/main")
    public ModelAndView getMainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    private ModelAndView createUserResponse(Model model, String message) {
        model.addAttribute("userResponse", message);
        ModelAndView modelAndView = new ModelAndView("response");
        return modelAndView;
    }

}
