package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tkachenko.buyerassistant.email.dto.RecipientRequest;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;
import ru.tkachenko.buyerassistant.email.service.MailService;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;
import ru.tkachenko.buyerassistant.summary.service.SummaryService;
import ru.tkachenko.buyerassistant.utils.CurrentDate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")//TODO for frontend app
public class SettingsController {
    private final BranchStartMonthService branchStartMonthService;

    private final SummaryService summaryService;
    private final MailService mailService;
    private final List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");

    @Autowired
    public SettingsController(BranchStartMonthService branchStartMonthService, SummaryService summaryService, MailService mailService) {
        this.branchStartMonthService = branchStartMonthService;
        this.summaryService = summaryService;
        this.mailService = mailService;
    }

    @GetMapping("/recipients") //REST-API
    public List<MailEntity> getRecipients() {
        return mailService.getAllMails();
    }

    @PutMapping("/recipients") //REST-API
    public ResponseEntity addRecipient(@RequestBody RecipientRequest recipientRequest) {
        MailEntity mailEntity = new MailEntity();
        mailEntity.setBranchName(recipientRequest.getBranchName());
        mailEntity.setEmailAddress(recipientRequest.getEmail());
        mailService.save(mailEntity);

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/recipients/{id}") //REST-API
    public ResponseEntity removeRecipient(@PathVariable Long id) {
        mailService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/branches") //REST-API
    public List<String> getAllBranches() {
        return branchStartMonthService.getAllBranchStartMonthEntitiesOrdered().stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    @PostMapping("/settings/save_month_settings")
    public ModelAndView saveMonthSettingsAndStay(@RequestParam("values[]") List<Integer> values,
                                                 @RequestParam("yearValues[]") List<Integer> yearValues, Model model) {
        CurrentDate currentDate = new CurrentDate();
        int currentYear = currentDate.getYearInt();
        List<Integer> years = List.of(currentYear - 1, currentYear, currentYear + 1, currentYear + 2,
                currentYear + 3, currentYear + 4);

        branchStartMonthService.saveMonthSettings(values, yearValues);
        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("years", years);
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        return new ModelAndView("settings");
    }

    @PostMapping("/settings/save_month_settings/to_main_page")
    public ModelAndView saveMonthSettingsAndGoToMainPage(@RequestParam("values[]") List<Integer> values,
                                                         @RequestParam("yearValues[]") List<Integer> yearValues,
                                                         Model model) {
        branchStartMonthService.saveMonthSettings(values, yearValues);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("undefinedBranchRows", summaryService.findAllUndefinedBranchRows());
        return modelAndView;
    }
//    @PostMapping("/settings/mail")
//    public ModelAndView addMail(@RequestParam("selectedBranch") String branchName,
//                                @RequestParam("addedEmail") String email, Model model) {
//        CurrentDate currentDate = new CurrentDate();
//        int currentYear = currentDate.getYearInt();
//        List<Integer> years = List.of(currentYear - 1, currentYear, currentYear + 1, currentYear + 2,
//                currentYear + 3, currentYear + 4);
//
//        MailEntity mailEntity = new MailEntity();
//        mailEntity.setBranchName(branchName);
//        mailEntity.setEmailAddress(email);
//        mailService.save(mailEntity);
//
//        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
//        model.addAttribute("years", years);
//        model.addAttribute("branchEntities", allBranches);
//        model.addAttribute("months", months);
//        List<MailEntity> allEmails = mailService.getAllMails();
//        model.addAttribute("emails", allEmails);
//        return new ModelAndView("settings");
//    }

    @DeleteMapping("/settings/mail")
    public ModelAndView removeMail(@RequestParam("removeId") Long id, Model model) {
        CurrentDate currentDate = new CurrentDate();
        int currentYear = currentDate.getYearInt();
        List<Integer> years = List.of(currentYear - 1, currentYear, currentYear + 1, currentYear + 2,
                currentYear + 3, currentYear + 4);

        mailService.deleteById(id);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("years", years);
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        return new ModelAndView("settings");
    }
}
