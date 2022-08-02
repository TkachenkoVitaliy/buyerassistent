package ru.tkachenko.buyerassistant.mmk_accept.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MmkAcceptServiceTest {

    @Mock
    MmkAcceptDBService mmkAcceptDBService;

    @InjectMocks
    MmkAcceptService mockedMmkService;

    //Пока нет идей, как это потестить
    @Disabled
    @Test
    void parseFileToDatabase() {

    }
}