package ru.tkachenko.buyerassistant.letter_of_authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterOfAuthorization;

@Repository
public interface LetterOfAuthorizationRepository extends JpaRepository<LetterOfAuthorization, Long> {
}
