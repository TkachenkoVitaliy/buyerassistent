package ru.tkachenko.buyerassistant.total.settings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.repository.TotalUserSettingsRepository;

@Service
public class TotalUserSettingsService {
    private final TotalUserSettingsRepository settingsRepository;

    @Autowired
    public TotalUserSettingsService(TotalUserSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void updateCurrentUserSettings(int month, int year) {
        TotalUserSettingsEntity currentUserSettingsEntity = getCurrentUserSettings();
        currentUserSettingsEntity.setMonth(month);
        currentUserSettingsEntity.setYear(year);
        settingsRepository.save(currentUserSettingsEntity);
    }

    public TotalUserSettingsEntity getCurrentUserSettings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return getUserSettingsByName(currentUserName);
        }
        return null;
    }

    private TotalUserSettingsEntity getUserSettingsByName(String username) {
        return settingsRepository.getTotalUserSettingsEntityByUsername(username);
    }
}
