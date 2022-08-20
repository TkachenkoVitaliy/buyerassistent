package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.LetterRow;
import ru.tkachenko.buyerassistant.letter_of_authorization.repository.LetterRowRepository;

import java.util.List;

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

    public void saveLetterRow(LetterRow letterRow) {
        letterRowRepository.save(letterRow);
    }
}
