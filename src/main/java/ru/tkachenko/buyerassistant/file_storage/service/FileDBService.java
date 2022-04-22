package ru.tkachenko.buyerassistant.file_storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistant.file_storage.repository.SavedFileRepository;

@Service
public class FileDBService {

    SavedFileRepository savedFileRepository;

    @Autowired
    public FileDBService(SavedFileRepository savedFileRepository) {
        this.savedFileRepository = savedFileRepository;
    }

    public void save(SavedFileEntity fileEntity) {
        SavedFileEntity previousActualEntity = findActual();
        if(previousActualEntity != null) {
            previousActualEntity.setActual(false);
            savedFileRepository.save(previousActualEntity);
        }
        savedFileRepository.save(fileEntity);
    }

    public SavedFileEntity findActual() {
        return savedFileRepository.findFirstByIsActual(true);
    }

}
