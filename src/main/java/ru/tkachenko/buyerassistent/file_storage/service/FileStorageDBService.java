package ru.tkachenko.buyerassistent.file_storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistent.file_storage.repository.SavedFileRepository;

@Service
public class FileStorageDBService {

    SavedFileRepository savedFileRepository;

    @Autowired
    public FileStorageDBService(SavedFileRepository savedFileRepository) {
        this.savedFileRepository = savedFileRepository;
    }

    public void save(SavedFileEntity fileEntity) {
        SavedFileEntity previousActualEntity = findActual();
        previousActualEntity.setActual(false);
        savedFileRepository.save(previousActualEntity);
        savedFileRepository.save(fileEntity);
    }

    public SavedFileEntity findActual() {
        return savedFileRepository.findFirstByIsActual(true);
    }

}
