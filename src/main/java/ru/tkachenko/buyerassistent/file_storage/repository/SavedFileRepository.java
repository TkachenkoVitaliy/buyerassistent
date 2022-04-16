package ru.tkachenko.buyerassistent.file_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.file_storage.entity.SavedFileEntity;

@Repository
public interface SavedFileRepository extends JpaRepository<SavedFileEntity, Long> {
}
