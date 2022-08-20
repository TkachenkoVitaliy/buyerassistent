package ru.tkachenko.buyerassistant.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterOfAuthorization;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Principal;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.Supplier;
import ru.tkachenko.buyerassistant.letter_of_authorization.service.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LetterOfAuthorizationController {

    private final LetterOfAuthorizationService letterOfAuthorizationService;
    private final DriverService driverService;
    private final LetterRowService letterRowService;
    private final NomenclatureService nomenclatureService;
    private final PrincipalService principalService;
    private final SupplierService supplierService;

    public LetterOfAuthorizationController(LetterOfAuthorizationService letterOfAuthorizationService, DriverService driverService, LetterRowService letterRowService, NomenclatureService nomenclatureService, PrincipalService principalService, SupplierService supplierService) {
        this.letterOfAuthorizationService = letterOfAuthorizationService;
        this.driverService = driverService;
        this.letterRowService = letterRowService;
        this.nomenclatureService = nomenclatureService;
        this.principalService = principalService;
        this.supplierService = supplierService;
    }

    @GetMapping("/lettersOfAuthorization") //REST-API
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LetterOfAuthorization> getAllLettersOfAuthorization() {
        return letterOfAuthorizationService.getAllLettersOfAuthorization();
    }

    @GetMapping("/principals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Principal> getAllPrincipals() {
        return principalService.getAllPrincipals();
    }

    @GetMapping("/suppliers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}
