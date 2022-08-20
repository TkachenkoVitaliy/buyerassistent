package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterOfAuthorization;
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

    public void saveLetterOfAuthorization(LetterOfAuthorization letterOfAuthorization) {
        loaRepository.save(letterOfAuthorization);
    }
}
