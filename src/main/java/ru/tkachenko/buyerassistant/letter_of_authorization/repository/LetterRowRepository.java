package ru.tkachenko.buyerassistant.letter_of_authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterRow;

@Repository
public interface LetterRowRepository extends JpaRepository<LetterRow, Long> {

}
