package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;
import ru.tkachenko.buyerassistant.email.service.EmailSenderService;
import ru.tkachenko.buyerassistant.email.service.MailService;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistant.file_storage.service.FileDownloadService;
import ru.tkachenko.buyerassistant.file_storage.service.FileStorageService;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.mmk_accept.service.MmkAcceptService;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryService;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.product.type.service.ProductTypeService;
import ru.tkachenko.buyerassistant.total.tables.FactoryTotalTable;
import ru.tkachenko.buyerassistant.total.service.TotalService;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;
import ru.tkachenko.buyerassistant.utils.CurrentDate;
import ru.tkachenko.buyerassistant.utils.TimerUtil;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:8080")//TODO for frontend app
public class MainController {
    private final FileStorageService fileStorageService;
    private final FileDownloadService fileDownloadService;
    private final MmkAcceptService mmkAcceptService;
    private final SummaryService summaryService;
    private final EmailSenderService emailSenderService;
    private final MailService mailService;
    private final BranchStartMonthService branchStartMonthService;
    private final TotalUserSettingsService totalUserSettingsService;
    private final TotalService totalService;
    private final ProductTypeService productTypeService;
    private final ProductGroupService productGroupService;

    private final List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");


    @Autowired
    public MainController(FileStorageService fileStorageService, FileDownloadService fileDownloadService,
                          MmkAcceptService mmkAcceptService, SummaryService summaryService,
                          EmailSenderService emailSenderService, MailService mailService,
                          BranchStartMonthService branchStartMonthService,
                          TotalUserSettingsService totalUserSettingsService, TotalService totalService, ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.fileStorageService = fileStorageService;
        this.fileDownloadService = fileDownloadService;
        this.mmkAcceptService = mmkAcceptService;
        this.summaryService = summaryService;
        this.emailSenderService = emailSenderService;
        this.mailService = mailService;
        this.branchStartMonthService = branchStartMonthService;
        this.totalUserSettingsService = totalUserSettingsService;
        this.totalService = totalService;
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
    }

        @PostMapping("/uploadMultipleFiles") //REST-API
    public ResponseEntity uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                            @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                            @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk, Model model) {
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk, dependenciesMmk);
            summaryService.parseFilesToSummary(savedFilesPaths);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/undefinedRows") //REST-API
    public List<SummaryRowEntity> getUndefinedRows() {
        return summaryService.findAllUndefinedBranchRows();
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
        } catch (IllegalFileExtensionException | AcceptParseException e) {
            e.printStackTrace();
            return createUserResponse(model, e.getMessage());
        } finally { //TODO remove timer
            timerUtil.consoleLogTime("uploadAccept");
        } //TODO remove timer
    }

//    @PostMapping("/uploadMultipleFiles")
//    public ModelAndView uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
//                                            @RequestParam("oracleMmk") MultipartFile oracleMmk,
//                                            @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk, Model model) {
//        //TODO remove timer
//        TimerUtil timerUtil = new TimerUtil();
//        //TODO remove timer
//        try {
//            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk, dependenciesMmk);
//            summaryService.parseFilesToSummary(savedFilesPaths);
//            return createUserResponse(model, "Files Uploaded");
//        } catch (IllegalFileExtensionException e) {
//            e.printStackTrace();
//            return createUserResponse(model, e.getMessage());
//        } finally { //TODO remove timer
//            timerUtil.consoleLogTime("upload and write to DB MultipleFiles");
//        } //TODO remove timer
//    }

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

    @GetMapping("/sendAllFiles")
    public ModelAndView sendAllFiles(Model model) {
        System.out.println("Start method sendAllFiles");
        List<String> resultForUser = new ArrayList<>();
        String message = "Это автоматическая рассылка, не нужно отвечать на это письмо";
        List<Path> createdBranchesFiles = summaryService.createAllBranchesFiles();
        for (Path filePath : createdBranchesFiles) {
            try {
                String branchName = filePath.getFileName().toString().replace(".xlsx", "");
                List<MailEntity> mailEntities = mailService.getMailsByBranchName(branchName);
                for (MailEntity mailEntity : mailEntities) {
                    String emailAddress = mailEntity.getEmailAddress();
                    String subject = "Акцепт-отгрузка " + branchName;
                    emailSenderService.sendMailWithAttachment(emailAddress, subject, message, filePath.toString());
                    resultForUser.add(branchName + " - " + emailAddress + "   ");
                    TimeUnit.SECONDS.sleep(4L);
                }
            } catch (MessagingException | FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
                return createUserResponse(model, e.getMessage());
            }

        }
        model.addAttribute("sendEmails", resultForUser);
        return createUserResponse(model, "Email send complete");
    }


    @GetMapping("/settings")
    public ModelAndView settingsPage(Model model) {
        CurrentDate currentDate = new CurrentDate();
        int currentYear = currentDate.getYearInt();
        List<Integer> years = List.of(currentYear - 1, currentYear, currentYear + 1, currentYear + 2,
                currentYear + 3, currentYear + 4);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("years", years);
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView getMainPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("undefinedBranchRows", summaryService.findAllUndefinedBranchRows());
        return modelAndView;
    }

    @GetMapping("/total")
    public ModelAndView totalPage(Model model) {
        summaryService.updateProductTypeTable();
        CurrentDate currentDate = new CurrentDate();
        int currentYear = currentDate.getYearInt();
        List<Integer> years = List.of(currentYear - 1, currentYear, currentYear + 1, currentYear + 2,
                currentYear + 3, currentYear + 4);
        model.addAttribute("years", years);
        model.addAttribute("months", months);

        TotalUserSettingsEntity currentUserSettings = totalUserSettingsService.getCurrentUserSettings();
        model.addAttribute("userSettings", currentUserSettings);

        List<ProductTypeEntity> undefinedProductTypes = productTypeService.findUndefinedProductTypes();
        model.addAttribute("undefinedProductTypes", undefinedProductTypes);

        List<ProductGroupEntity> allProductGroups = productGroupService.findAllOrdered();
        model.addAttribute("allProductGroups", allProductGroups);

        List<FactoryTotalTable> factoryTables = totalService.createFactoryTables();
        model.addAttribute("factoryTables", factoryTables);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("total");
        return modelAndView;
    }

    @PostMapping("/total")
    public ModelAndView totalPageUpdate(@RequestParam("monthValue") int month,
                                        @RequestParam("yearValue") int year,
                                        @RequestParam(required = false, name = "types[]") List<String> types,
                                        Model model) {
        totalUserSettingsService.updateCurrentUserSettings(month, year);
        if (types != null && !types.isEmpty()) {
            productTypeService.updateUndefinedProductTypes(types);
        }
        return totalPage(model);
    }

    private ModelAndView createUserResponse(Model model, String message) {
        model.addAttribute("userResponse", message);
        model.addAttribute("undefinedBranchRows", summaryService.findAllUndefinedBranchRows());
        return new ModelAndView("response");
    }

}
