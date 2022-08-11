package ru.tkachenko.buyerassistant.total.settings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.repository.TotalUserSettingsRepository;
import ru.tkachenko.buyerassistant.utils.CurrentDate;

@Service
public class TotalUserSettingsService {
    private final TotalUserSettingsRepository settingsRepository;

    @Autowired
    public TotalUserSettingsService(TotalUserSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void updateCurrentUserSettings(TotalUserSettingsEntity currentUserSettingsEntity) {
        settingsRepository.save(currentUserSettingsEntity);
    }

    public TotalUserSettingsEntity getCurrentUserSettings(String username) {
        return getUserSettingsByName(username);
    }


    private TotalUserSettingsEntity getUserSettingsByName(String username) {
        if(username != null) {
            return settingsRepository.getTotalUserSettingsEntityByUsername(username);
        } else {
            TotalUserSettingsEntity defaultUserSetting = new TotalUserSettingsEntity();
            CurrentDate currentDate = new CurrentDate();
            defaultUserSetting.setMonth(currentDate.getMonthInt());
            defaultUserSetting.setYear(currentDate.getYearInt());
            return defaultUserSetting;
        }
    }
}
