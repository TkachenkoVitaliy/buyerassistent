package ru.tkachenko.buyerassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tkachenko.buyerassistent.entity.SavedFileEntity;

public interface SavedFileRepository extends JpaRepository<SavedFileEntity, Long> {
}
