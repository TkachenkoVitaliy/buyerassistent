package ru.tkachenko.buyerassistant.letter_of_authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Nomenclature;

import java.util.List;

@Repository
public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {

    public List<Nomenclature> findByOrderByName();

}
