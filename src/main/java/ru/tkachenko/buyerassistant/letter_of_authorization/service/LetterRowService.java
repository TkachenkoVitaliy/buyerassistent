package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterRow;
import ru.tkachenko.buyerassistant.letter_of_authorization.repository.LetterRowRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LetterRowService {

    private final LetterRowRepository letterRowRepository;

    @Autowired
    public LetterRowService(LetterRowRepository letterRowRepository) {
        this.letterRowRepository = letterRowRepository;
    }

    public List<LetterRow> getAllLetterRows() {
        return letterRowRepository.findAll();
    }

    public LetterRow saveLetterRow(LetterRow letterRow) {
        return letterRowRepository.save(letterRow);
    }

    public List<LetterRow> saveAllLetterRows (List<LetterRow> letterRows) {
        return letterRows.stream()
                .map(letterRowRepository::save)
                .collect(Collectors.toList());

    }
}
