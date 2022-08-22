package ru.tkachenko.buyerassistant.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.*;
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

    @PostMapping("/principals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Principal addPrincipal(@RequestBody Principal principal) {
        return principalService.savePrincipal(principal);
    }

    @GetMapping("/suppliers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @PostMapping("/suppliers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.saveSupplier(supplier);
    }

    @GetMapping("/drivers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping("/drivers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @GetMapping("/nomenclatures")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Nomenclature> getAllNomenclatures() {
        return nomenclatureService.getAllNomenclatures();
    }

    @PostMapping("/nomenclatures")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Nomenclature addNomenclature(@RequestBody Nomenclature nomenclature) {
        return nomenclatureService.saveNomenclature(nomenclature);
    }
}
