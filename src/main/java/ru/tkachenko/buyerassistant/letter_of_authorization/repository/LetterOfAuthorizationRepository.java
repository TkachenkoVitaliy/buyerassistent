package ru.tkachenko.buyerassistant.letter_of_authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterOfAuthorization;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Principal;

import java.util.List;

@Repository
public interface LetterOfAuthorizationRepository extends JpaRepository<LetterOfAuthorization, Long> {

    public List<LetterOfAuthorization> findByPrincipal(Principal principal);
}
