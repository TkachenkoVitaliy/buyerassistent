package ru.tkachenko.buyerassistent.service.mmk_accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.repository.MmkAcceptRepository;

@Service
public class MmkAcceptDBService {

    MmkAcceptRepository mmkAcceptRepository;

    @Autowired
    public MmkAcceptDBService(MmkAcceptRepository mmkAcceptRepository) {
        this.mmkAcceptRepository = mmkAcceptRepository;
    }

    public void addUniqueEntity(MmkAcceptRowEntity mmkAcceptRowEntity) {

    }
}
