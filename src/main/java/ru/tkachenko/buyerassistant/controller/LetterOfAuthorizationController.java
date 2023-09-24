package ru.tkachenko.buyerassistant.controller;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tkachenko.buyerassistant.file_storage.exceptions.IllegalFileExtensionException;
import ru.tkachenko.buyerassistant.file_storage.service.FileStorageService;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.*;
import ru.tkachenko.buyerassistant.letter_of_authorization.exceptions.AlreadyUsedException;
import ru.tkachenko.buyerassistant.letter_of_authorization.service.*;
import ru.tkachenko.buyerassistant.utils.PdfUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
    private final LetterOfAuthorizationCreator letterOfAuthorizationCreator;
    private final PdfUtil pdfUtil;
    private final FileStorageService fileStorageService;

    @Autowired
    public LetterOfAuthorizationController(LetterOfAuthorizationService letterOfAuthorizationService, DriverService driverService, LetterRowService letterRowService, NomenclatureService nomenclatureService, PrincipalService principalService, SupplierService supplierService, LetterOfAuthorizationCreator letterOfAuthorizationCreator, PdfUtil pdfUtil, FileStorageService fileStorageService) {
        this.letterOfAuthorizationService = letterOfAuthorizationService;
        this.driverService = driverService;
        this.letterRowService = letterRowService;
        this.nomenclatureService = nomenclatureService;
        this.principalService = principalService;
        this.supplierService = supplierService;
        this.letterOfAuthorizationCreator = letterOfAuthorizationCreator;
        this.pdfUtil = pdfUtil;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadTemplate/{inn}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity uploadTemplate(@RequestParam("template") MultipartFile template, @PathVariable String inn) {
        try {
            Path templatePath = fileStorageService.storeTemplateFile(template, inn);
        } catch (IllegalFileExtensionException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/lettersOfAuthorization")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LetterOfAuthorization> getAllLettersOfAuthorization(@RequestParam(required = false, name = "principal_id")
                                                                            Long principalId) {
        if (principalId == null) {
            return letterOfAuthorizationService.getAllLettersOfAuthorizationOrderedByIssuedDate();
        } else {
            Principal principal = principalService.getPrincipalById(principalId);
            return letterOfAuthorizationService.getAllLetterOfAuthorizationByPrincipal(principal);
        }
    }

    @DeleteMapping("/lettersOfAuthorization/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteLetterOfAuthorization(@PathVariable Long id) {
        letterOfAuthorizationService.deleteLetterOfAuthorization(id);
    }

    @GetMapping("/lettersOfAuthorization/xls/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadLoaAsXls(@PathVariable Long id) {
        return loaFileForDownload("xls", id);
    }

    @GetMapping("/lettersOfAuthorization/pdf/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadLoaAsPdf(@PathVariable Long id) {
        return loaFileForDownload("pdf", id);
    }

    private ResponseEntity<Resource> loaFileForDownload(String extension, Long id) {
        if (Objects.equals(extension, "xls") || Objects.equals(extension, "pdf")) {
            LetterOfAuthorization letterOfAuthorization = letterOfAuthorizationService.getLetterOfAuthorizationById(id);
            try {
                Path createdLoa = letterOfAuthorizationCreator.createNewLoa(letterOfAuthorization);
                if (extension.equals("pdf")) {

                    String loaPdf = createdLoa.toString().replace(".xls", ".pdf");
                    Workbook workbook = new Workbook(createdLoa.toString());
                    workbook.save(loaPdf, SaveFormat.PDF);


                    pdfUtil.createPDFFromImage(loaPdf,
                            loaPdf);

                    createdLoa = Paths.get(loaPdf);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(createdLoa));

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(createdLoa.toFile().length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping("/lettersOfAuthorization")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public LetterOfAuthorization createLetterOfAuthorization(@RequestBody LetterOfAuthorization letterOfAuthorization) {
        return letterOfAuthorizationService.saveLetterOfAuthorization(letterOfAuthorization);
    }

    @PutMapping("/lettersOfAuthorization")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public LetterOfAuthorization updateLetterOfAuthorization(@RequestBody LetterOfAuthorization letterOfAuthorization) {
        return letterOfAuthorizationService.saveLetterOfAuthorization(letterOfAuthorization);
    }

    @PostMapping("/letterRows")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LetterRow> createLetterRows(@RequestBody List<LetterRow> letterRows) {
        return letterRowService.saveAllLetterRows(letterRows);
    }

    @PutMapping("/letterRows")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LetterRow> updateLetterRows(@RequestBody List<LetterRow> letterRowsAfter) {
        LetterOfAuthorization letterOfAuthorization = letterRowsAfter.get(0).getLetterOfAuthorization();
        List<LetterRow> letterRowsBefore = letterRowService.findLetterRowsByLetterOfAuthorization(letterOfAuthorization);
        if(compareLetterRows(letterRowsBefore, letterRowsAfter)) return letterRowsAfter;

        letterRowsBefore.forEach(letterRow -> letterRowService.deleteRow(letterRow));

        return letterRowService.saveAllLetterRows(letterRowsAfter);
    }

    private boolean compareLetterRows(List<LetterRow> oldRows, List<LetterRow> newRows) {
        if(oldRows.size() != newRows.size()) return false;
        oldRows.sort(Comparator.comparingInt(LetterRow::getNumber));
        newRows.sort(Comparator.comparingInt(LetterRow::getNumber));

        for(int i = 0; i < oldRows.size(); i++) {
            if(oldRows.get(i) != newRows.get(i)) return false;
        }
        return true;
    }


    @GetMapping("/principals")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Principal> getAllPrincipals() {
        return principalService.getAllPrincipals();
    }

    @PostMapping("/principals")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Principal addPrincipal(@RequestBody Principal principal) {
        return principalService.savePrincipal(principal);
    }

    @PutMapping("/principals")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Principal editPrincipal(@RequestBody Principal principal) {
        return principalService.savePrincipal(principal);
    }

    @DeleteMapping("/principals/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deletePrincipal(@PathVariable Long id) {
        principalService.deletePrincipal(id);
    }

    @GetMapping("/suppliers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @PostMapping("/suppliers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.saveSupplier(supplier);
    }

    @PutMapping("/suppliers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Supplier editSupplier(@RequestBody Supplier supplier) {
        return supplierService.saveSupplier(supplier);
    }

    @DeleteMapping("/suppliers/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }

    @GetMapping("/drivers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping("/drivers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @PutMapping("/drivers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Driver editDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @DeleteMapping("/drivers/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }

    @GetMapping("/nomenclatures")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Nomenclature> getAllNomenclatures() {
        return nomenclatureService.getAllNomenclatures();
    }

    @PostMapping("/nomenclatures")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Nomenclature addNomenclature(@RequestBody Nomenclature nomenclature) {
        return nomenclatureService.saveNomenclature(nomenclature);
    }

    @PutMapping("/nomenclatures")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Nomenclature updateNomenclature(@RequestBody Nomenclature nomenclature) {
        return nomenclatureService.saveNomenclature(nomenclature);
    }

    @DeleteMapping("/nomenclatures/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteNomenclature(@PathVariable Long id) {
        if (letterRowService.findLetterRowsUseThisNomenclatureId(id).size() > 0) {
            throw new AlreadyUsedException("Эта номенклатура уже используется");
        } else {
            nomenclatureService.deleteNomenclature(id);
        }
    }

}
