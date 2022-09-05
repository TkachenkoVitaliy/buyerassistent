package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Nomenclature;
import ru.tkachenko.buyerassistant.letter_of_authorization.repository.NomenclatureRepository;

import java.util.List;

@Service
public class NomenclatureService {

    private final NomenclatureRepository nomenclatureRepository;

    @Autowired
    public NomenclatureService(NomenclatureRepository nomenclatureRepository) {
        this.nomenclatureRepository = nomenclatureRepository;
    }

    public List<Nomenclature> getAllNomenclatures() {
        return nomenclatureRepository.findByOrderByName();
    }

    public Nomenclature saveNomenclature(Nomenclature nomenclature) {
        return nomenclatureRepository.save(nomenclature);
    }

    public void deleteNomenclature(Long id) {
        nomenclatureRepository.deleteById(id);
    }
}
