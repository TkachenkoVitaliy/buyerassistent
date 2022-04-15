package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.DependencyEntity;
import ru.tkachenko.buyerassistent.repository.DependencyRepository;

import javax.transaction.Transactional;

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
}
