package ru.tkachenko.buyerassistant.total.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.total.entity.TotalUserSettingsEntity;

@Repository
public interface TotalUserSettingsRepository extends JpaRepository<TotalUserSettingsEntity, Long> {

    public TotalUserSettingsEntity getTotalUserSettingsEntityByUsername(String username);
}
