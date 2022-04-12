package ru.tkachenko.buyerassistent.service.mmk_accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.repository.MmkAcceptRepository;

import java.util.List;

@Service
public class MmkAcceptDBService {

    MmkAcceptRepository mmkAcceptRepository;

    @Autowired
    public MmkAcceptDBService(MmkAcceptRepository mmkAcceptRepository) {
        this.mmkAcceptRepository = mmkAcceptRepository;
    }

    public void addUniqueEntity(MmkAcceptRowEntity mmkAcceptRowEntity) {
        MmkAcceptRowEntity entityFromDB = mmkAcceptRepository.findFirstBySpecAndPosition(mmkAcceptRowEntity.getSpec(),
                mmkAcceptRowEntity.getPosition());
        if(entityFromDB == null) {
            mmkAcceptRepository.save(mmkAcceptRowEntity);
        } else {
            MmkAcceptRowEntity updatedEntity = updateEntity(mmkAcceptRowEntity, entityFromDB);
            mmkAcceptRepository.save(updatedEntity);
        }
    }

    private MmkAcceptRowEntity updateEntity(MmkAcceptRowEntity sourceEntity, MmkAcceptRowEntity targetEntity) {
        targetEntity.setNomenclature(sourceEntity.getNomenclature());
        targetEntity.setGrade(sourceEntity.getGrade());
        targetEntity.setThickness(sourceEntity.getThickness());
        targetEntity.setWidth(sourceEntity.getWidth());
        targetEntity.setLength(sourceEntity.getLength());
        targetEntity.setAlterProfile(sourceEntity.getAlterProfile());
        targetEntity.setAccepted(sourceEntity.getAccepted());
        targetEntity.setAcceptMonth(sourceEntity.getAcceptMonth());
        targetEntity.setAdditionalRequirements(sourceEntity.getAdditionalRequirements());

        return targetEntity;
    }
}
