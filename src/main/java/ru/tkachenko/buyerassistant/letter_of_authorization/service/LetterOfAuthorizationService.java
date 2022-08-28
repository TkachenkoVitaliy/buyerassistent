package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterOfAuthorization;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Principal;
import ru.tkachenko.buyerassistant.letter_of_authorization.repository.LetterOfAuthorizationRepository;

import java.util.List;

@Service
public class LetterOfAuthorizationService {

    private final LetterOfAuthorizationRepository loaRepository;

    @Autowired
    public LetterOfAuthorizationService(LetterOfAuthorizationRepository loadRepository) {
        this.loaRepository = loadRepository;
    }

    public List<LetterOfAuthorization> getAllLettersOfAuthorization() {
        return loaRepository.findAll();
    }

    public List<LetterOfAuthorization> getAllLettersOfAuthorizationOrderedByIssuedDate() {
        return loaRepository.findAll(Sort.by(Sort.Direction.DESC, "issuedDate", "principal", "number"));
    }

    public LetterOfAuthorization getLetterOfAuthorizationById(Long id) {
        return loaRepository.getById(id);
    }

    public LetterOfAuthorization saveLetterOfAuthorization(LetterOfAuthorization letterOfAuthorization) {
        return loaRepository.save(letterOfAuthorization);
    }

    public List<LetterOfAuthorization> getAllLetterOfAuthorizationByPrincipal(Principal principal) {
        return loaRepository.findByPrincipal(principal);
    }
}
