package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;
import ru.tkachenko.buyerassistant.email.service.MailService;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;

import java.util.List;

@RestController
public class SettingsController {
    private final BranchStartMonthService branchStartMonthService;
    private final MailService mailService;
    private final List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");

    @Autowired
    public SettingsController(BranchStartMonthService branchStartMonthService, MailService mailService) {
        this.branchStartMonthService = branchStartMonthService;
        this.mailService = mailService;
    }

    @PostMapping("/settings/save_month_settings")
    public ModelAndView saveMonthSettingsAndStay(@RequestParam("values[]") List<Integer> values, Model model) {
        branchStartMonthService.saveMonthSettings(values);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        return new ModelAndView("settings");
    }

    @PostMapping("/settings/save_month_settings/to_main_page")
    public ModelAndView saveMonthSettingsAndGoToMainPage(@RequestParam("values[]") List<Integer> values) {
        branchStartMonthService.saveMonthSettings(values);
        return new ModelAndView("index");
    }
    @PostMapping("/settings/mail")
    public ModelAndView addMail(@RequestParam("selectedBranch") String branchName,
                                @RequestParam("addedEmail") String email, Model model) {
        MailEntity mailEntity = new MailEntity();
        mailEntity.setBranchName(branchName);
        mailEntity.setEmailAddress(email);
        mailService.save(mailEntity);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        return new ModelAndView("settings");
    }

    @DeleteMapping("/settings/mail")
    public ModelAndView removeMail(@RequestParam("removeId") Long id, Model model) {

//    @DeleteMapping("/setting/mail/{id}")
//    public ModelAndView removeMail(@PathVariable("id") Long id, Model model) {
        mailService.deleteById(id);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        List<MailEntity> allEmails = mailService.getAllMails();
        model.addAttribute("emails", allEmails);
        return new ModelAndView("settings");
    }
}
