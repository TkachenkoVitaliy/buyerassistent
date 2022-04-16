package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.dto.OracleDTO;
import ru.tkachenko.buyerassistent.entity.DependencyEntity;

import java.util.List;

@Service
public class DependencyWorker {

    @Autowired
    private final DependencyDBService dependencyDBService;

    public DependencyWorker(DependencyDBService dependencyDBService) {
        this.dependencyDBService = dependencyDBService;
    }

    public DependencyEntity findDependencyEntity(OracleDTO oracleDTO) {
        DependencyEntity dependencyEntity = null;

        dependencyEntity = findFromExceptional(oracleDTO.getSpec(), oracleDTO.getPosition());
        if(dependencyEntity != null) return dependencyEntity;

        dependencyEntity = findFromContainers(oracleDTO.getConsignee(), oracleDTO.getStation());
        if(dependencyEntity != null) return dependencyEntity;

        dependencyEntity = findFromTransits(oracleDTO.getConsignee());
        if(dependencyEntity != null) return dependencyEntity;

        dependencyEntity = findFromDefault(oracleDTO.getConsignee());
        return dependencyEntity;
    }


    private DependencyEntity findFromExceptional(String spec, int position) {
        List<DependencyEntity> entities = dependencyDBService.findFromExceptional(spec);
        if(entities != null) {
            for (DependencyEntity entity : entities) {
                if(entity.getPosition() == 0 || entity.getPosition() == position) return entity;
            }
        }
        return null;
    }

    private DependencyEntity findFromContainers(String consignee, String station) {
        List<DependencyEntity> entities = dependencyDBService.findFromContainers(consignee, station);
        if(entities != null && entities.size() == 1) {
            return entities.get(0);
        }
        return null;
    }

    private DependencyEntity findFromTransits(String consignee) {
        List<DependencyEntity> entities = dependencyDBService.findFromTransits(consignee);
        if(entities != null && entities.size() == 1) {
            return entities.get(0);
        }
        return null;
    }

    private DependencyEntity findFromDefault(String consignee) {
        List<DependencyEntity> entities = dependencyDBService.findFromDefault(consignee);
        if(entities != null && entities.size() == 1) {
            return entities.get(0);
        }
        return null;
    }
}
