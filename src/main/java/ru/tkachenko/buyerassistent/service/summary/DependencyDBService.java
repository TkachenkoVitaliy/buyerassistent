package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.DependencyEntity;
import ru.tkachenko.buyerassistent.repository.DependencyRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DependencyDBService {

    private final DependencyRepository dependencyRepository;

    @Autowired
    public DependencyDBService(DependencyRepository dependencyRepository) {
        this.dependencyRepository = dependencyRepository;
    }

    public void save(DependencyEntity dependencyEntity) {
        dependencyRepository.save(dependencyEntity);
    }

    @Transactional
    public void truncateTable() {

        dependencyRepository.truncateTable();
    }

    public List<DependencyEntity> findFromExceptional(String spec) {
        return dependencyRepository.findBySpecAndPriority(spec, 4);
    }

    public List<DependencyEntity> findFromContainers(String consignee, String station) {
        return dependencyRepository.findByConsigneeAndStationAndPriority(consignee, station, 3);
    }

    public List<DependencyEntity> findFromTransits(String consignee) {
        return dependencyRepository.findByConsigneeAndPriority(consignee, 2);
    }

    public List<DependencyEntity> findFromDefault(String consignee) {
        return dependencyRepository.findByConsigneeAndPriority(consignee, 1);
    }
}
