package ru.tkachenko.buyerassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.entity.SavedFileEntity;

@Repository
public interface SavedFileRepository extends JpaRepository<SavedFileEntity, Long> {
}
