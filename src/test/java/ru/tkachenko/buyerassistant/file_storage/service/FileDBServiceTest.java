package ru.tkachenko.buyerassistant.file_storage.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tkachenko.buyerassistant.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistant.file_storage.repository.SavedFileRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileDBServiceTest {
    @Mock
    private SavedFileRepository mockedSavedFileRepository;

    @InjectMocks
    private FileDBService fileDBService;

    @Test
    void checkSaveWithCorrectInput() {
        SavedFileEntity savedFileEntity = new SavedFileEntity();
        fileDBService.save(savedFileEntity);
        verify(mockedSavedFileRepository).save(savedFileEntity);
    }

    //TODO Обрати внимание на этот тест
    @Test
    void checkSaveWithIncorrectInput() {
        fileDBService.save(null);
        verify(mockedSavedFileRepository).save(null);
    }

    @Test
    void checkFindActual() {
        fileDBService.findActual();
        verify(mockedSavedFileRepository).findFirstByIsActual(true);

    }
}