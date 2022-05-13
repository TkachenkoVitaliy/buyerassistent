package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;

import java.util.List;

@RestController
public class SettingsController {
    private final BranchStartMonthService branchStartMonthService;
    private final List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");

    @Autowired
    public SettingsController(BranchStartMonthService branchStartMonthService) {
        this.branchStartMonthService = branchStartMonthService;
    }

    @PostMapping("/settings/save_month_settings")
    public ModelAndView saveMonthSettingsAndStay(@RequestParam("values[]") List<Integer> values, Model model) {
        branchStartMonthService.saveMonthSettings(values);

        List<BranchStartMonthEntity> allBranches = branchStartMonthService.getAllBranchStartMonthEntitiesOrdered();
        model.addAttribute("branchEntities", allBranches);
        model.addAttribute("months", months);
        return new ModelAndView("settings");
    }

    @PostMapping("/settings/save_month_settings/to_main_page")
    public ModelAndView saveMonthSettingsAndGoToMainPage(@RequestParam("values[]") List<Integer> values) {
        branchStartMonthService.saveMonthSettings(values);
        return new ModelAndView("index");
    }
}
