package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;
import ru.tkachenko.buyerassistant.email.service.EmailSenderService;
import ru.tkachenko.buyerassistant.email.service.MailService;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistant.file_storage.service.FileDownloadService;
import ru.tkachenko.buyerassistant.file_storage.service.FileStorageService;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.mmk_accept.service.MmkAcceptService;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowMinEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryService;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.product.type.service.ProductTypeService;
import ru.tkachenko.buyerassistant.total.tables.FactoryTotalTable;
import ru.tkachenko.buyerassistant.total.service.TotalService;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")//TODO for frontend app
public class MainController {
    private final FileStorageService fileStorageService;
    private final MmkAcceptService mmkAcceptService;
    private final SummaryService summaryService;
    private final EmailSenderService emailSenderService;
    private final MailService mailService;
    private final TotalUserSettingsService totalUserSettingsService;
    private final TotalService totalService;
    private final ProductTypeService productTypeService;
    private final ProductGroupService productGroupService;

    private final List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");


    @Autowired
    public MainController(FileStorageService fileStorageService, MmkAcceptService mmkAcceptService,
                          SummaryService summaryService, EmailSenderService emailSenderService, MailService mailService,
                          TotalUserSettingsService totalUserSettingsService, TotalService totalService,
                          ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.fileStorageService = fileStorageService;
        this.mmkAcceptService = mmkAcceptService;
        this.summaryService = summaryService;
        this.emailSenderService = emailSenderService;
        this.mailService = mailService;
        this.totalUserSettingsService = totalUserSettingsService;
        this.totalService = totalService;
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
    }

        @PostMapping("/uploadMultipleFiles")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity uploadMultipleFiles(@RequestParam("otherFactories") MultipartFile otherFactories,
                                            @RequestParam("oracleMmk") MultipartFile oracleMmk,
                                            @RequestParam("dependenciesMmk") MultipartFile dependenciesMmk) {
        try {
            List<Path> savedFilesPaths = fileStorageService.storeFiles(otherFactories, oracleMmk, dependenciesMmk);
            summaryService.parseFilesToSummary(savedFilesPaths);
            summaryService.updateProductTypeTable();
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/undefinedRows")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<SummaryRowEntity> getUndefinedRows() {
        return summaryService.findAllUndefinedBranchRows();
    }

    @PostMapping("/uploadAccept")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity uploadAccept(@RequestParam("mmkAccept") MultipartFile mmkAccept) {
        try {
            Path mmkAcceptPath = fileStorageService.storeFile(mmkAccept);
            mmkAcceptService.parseFileToDatabase(mmkAcceptPath);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (IllegalFileExtensionException | AcceptParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendFiles")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<String> sendAllFiles() {
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
                    resultForUser.add(branchName + " - " + emailAddress);
                    TimeUnit.SECONDS.sleep(4L);
                }
            } catch (MessagingException | FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultForUser;
    }

    @PostMapping("/sendFiles")
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> sendFiles(@RequestBody String[] selectedBranches) {
        List<String> branchNames = Arrays.stream(selectedBranches).collect(Collectors.toList());
        List<String> resultForUser = new ArrayList<>();
        String message = "Это автоматическая рассылка, не нужно отвечать на это письмо";
        List<Path> createdBranchesFiles = summaryService.createAllBranchesFiles();
        for (Path filePath : createdBranchesFiles) {
            try {
                String branchName = filePath.getFileName().toString().replace(".xlsx", "");
                if(branchNames.contains(branchName)) {
                    List<MailEntity> mailEntities = mailService.getMailsByBranchName(branchName);
                    for (MailEntity mailEntity : mailEntities) {
                        String emailAddress = mailEntity.getEmailAddress();
                        String subject = "Акцепт-отгрузка " + branchName;
                        emailSenderService.sendMailWithAttachment(emailAddress, subject, message, filePath.toString());
                        resultForUser.add(branchName + " - " + emailAddress);
                        TimeUnit.SECONDS.sleep(4L);
                    }
                }
            } catch (MessagingException | FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultForUser;
    }

    @GetMapping("/loadTables/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<FactoryTotalTable> getLoadTables(@PathVariable String username) {
        return totalService.createFactoryTables(username);
    }

    @GetMapping("/loadTables/settings/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public TotalUserSettingsEntity getLoadTablesUserSettings(@PathVariable String username) {
        return totalUserSettingsService.getCurrentUserSettings(username);
    }

    @PostMapping("/loadTables/settings")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public TotalUserSettingsEntity updateLoadTablesUserSettings(@RequestBody TotalUserSettingsEntity userSettings) {
        totalUserSettingsService.updateCurrentUserSettings(userSettings);
        return totalUserSettingsService.getCurrentUserSettings(userSettings.getUsername());
    }

    @GetMapping("/productTypes/undefined")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<ProductTypeEntity> getUndefinedProductTypes() {
        return productTypeService.findUndefinedProductTypes();
    }

    @GetMapping("/productGroups")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<ProductGroupEntity> getAllProductGroups() {
        return productGroupService.findAllOrdered();
    }

    @PostMapping("/productTypes/undefined")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity updateProductTypes(@RequestBody List<ProductTypeEntity> productTypes) {
        productTypes.stream()
                .forEach(e -> productTypeService.save(e));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/specs")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<SummaryRowMinEntity> getAllSpecs() {
        return summaryService.getAllSpecsMinified();
    }

//    TODO create REST endpoint for download files
//    @GetMapping("/downloadAllFiles")
//    public ResponseEntity<Resource> downloadAllFiles(HttpServletRequest request) {
//        //TODO remove timer
//        TimerUtil timerUtilDownloadAllFiles = new TimerUtil();
//        //TODO remove timer
//        try {
//            List<Path> createdBranchesFiles = summaryService.createAllBranchesFiles();
//            return fileDownloadService.getZipFileAsResources(createdBranchesFiles, request);
//        } finally {
//            timerUtilDownloadAllFiles.consoleLogTime("createAllFiles");
//        }
//    }
}
