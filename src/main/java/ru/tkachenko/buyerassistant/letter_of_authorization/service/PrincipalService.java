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

    public Principal getPrincipalById(Long id) {
        return principalRepository.findById(id).orElseThrow();//TODO cant find principal exception
    }

    public Principal savePrincipal(Principal principal) {
        return principalRepository.save(principal);
    }

    public void deletePrincipal(Long id) {
        principalRepository.deleteById(id);
    }
}
