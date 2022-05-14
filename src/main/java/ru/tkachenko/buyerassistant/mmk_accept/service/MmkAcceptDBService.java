package ru.tkachenko.buyerassistant.mmk_accept.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.mmk_accept.repository.MmkAcceptRepository;


@Service
public class MmkAcceptDBService {

    MmkAcceptRepository mmkAcceptRepository;

    @Autowired
    public MmkAcceptDBService(MmkAcceptRepository mmkAcceptRepository) {
        this.mmkAcceptRepository = mmkAcceptRepository;
    }

    public void addUniqueEntity(MmkAcceptRowEntity entityFromFile) throws AcceptParseException {
        if (entityFromFile.getSpec() != null && entityFromFile.getPosition() != 0) {
            MmkAcceptRowEntity entityFromDB = mmkAcceptRepository.findFirstBySpecAndPosition(entityFromFile.getSpec(),
                    entityFromFile.getPosition());
            if (entityFromDB == null) {
                mmkAcceptRepository.save(entityFromFile);
            } else {
                updateEntity(entityFromDB, entityFromFile);
                mmkAcceptRepository.save(entityFromDB);
            }
        } else {
            throw new AcceptParseException("Can't parse AcceptFile because file contains row with Specification number "
                    + "or position number - blank value");
        }
    }

    private MmkAcceptRowEntity updateEntity(MmkAcceptRowEntity targetEntity, MmkAcceptRowEntity sourceEntity) {
        final double MIN_ACCEPT_VALUE = 0.1;
        if (sourceEntity.getAccepted() > MIN_ACCEPT_VALUE) {
            targetEntity.setNomenclature(sourceEntity.getNomenclature());
            targetEntity.setGrade(sourceEntity.getGrade());
            targetEntity.setThickness(sourceEntity.getThickness());
            targetEntity.setWidth(sourceEntity.getWidth());
            targetEntity.setLength(sourceEntity.getLength());
            targetEntity.setAlterProfile(sourceEntity.getAlterProfile());
            targetEntity.setAccepted(sourceEntity.getAccepted());
            targetEntity.setAcceptMonth(sourceEntity.getAcceptMonth());
            targetEntity.setAdditionalRequirements(sourceEntity.getAdditionalRequirements());
        }
        return targetEntity;
    }

    public MmkAcceptRowEntity findEntityBySpecAndPosition(String spec, int position) {
        return mmkAcceptRepository.findFirstBySpecAndPosition(spec, position);
    }
}
