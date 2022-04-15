package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.repository.SummaryRowRepository;

import java.nio.file.Path;
import java.util.List;

@Service
public class SummaryService {

    private final OtherFactoriesParser otherFactoriesParser;
    private final SummaryDBService summaryDBService;

    @Autowired
    public SummaryService(OtherFactoriesParser otherFactoriesParser, SummaryDBService summaryDBService) {
        this.otherFactoriesParser = otherFactoriesParser;
        this.summaryDBService = summaryDBService;
    }

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        //TODO предварительно нужно очищать summary_table
        summaryDBService.truncateTable();
        otherFactoriesParser.parse(otherFactoriesPath);
        //write oracleMmk to SummaryDB
    }
}
