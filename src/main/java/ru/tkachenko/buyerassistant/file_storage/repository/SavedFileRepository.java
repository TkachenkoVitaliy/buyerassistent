package ru.tkachenko.buyerassistant.file_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.file_storage.entity.SavedFileEntity;

@Repository
public interface SavedFileRepository extends JpaRepository<SavedFileEntity, Long> {
    public SavedFileEntity findFirstByIsActual(boolean isTrue);
}
