package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SummaryService {

    private final OtherFactoriesParser otherFactoriesParser;
    private final SummaryDBService summaryDBService;
    private final OracleParser oracleParser;

    @Autowired
    public SummaryService(OtherFactoriesParser otherFactoriesParser, SummaryDBService summaryDBService,
                          OracleParser oracleParser) {
        this.otherFactoriesParser = otherFactoriesParser;
        this.summaryDBService = summaryDBService;
        this.oracleParser = oracleParser;
    }

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        //TODO предварительно нужно очищать summary_table
        summaryDBService.truncateTable();
        otherFactoriesParser.parse(otherFactoriesPath);
        oracleParser.parse(oracleMmkPath, dependenciesMmkPath);
        //write oracleMmk to SummaryDB
    }
}
