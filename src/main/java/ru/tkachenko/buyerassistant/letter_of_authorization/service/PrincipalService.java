package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Principal;
import ru.tkachenko.buyerassistant.letter_of_authorization.repository.PrincipalRepository;

import java.util.List;

@Service
public class PrincipalService {

    private final PrincipalRepository principalRepository;

    @Autowired
    public PrincipalService(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    public List<Principal> getAllPrincipals() {
        return principalRepository.findAll();
    }

    public void savePrincipal(Principal principal) {
        principalRepository.save(principal);
    }
}
