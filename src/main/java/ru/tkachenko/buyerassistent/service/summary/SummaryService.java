package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SummaryService {

    private final SummaryDBService summaryDBService;
    private final DependencyParser dependencyParser;
    private final OtherFactoriesParser otherFactoriesParser;
    private final OracleParser oracleParser;

    @Autowired
    public SummaryService(SummaryDBService summaryDBService, DependencyParser dependencyParser,
                          OtherFactoriesParser otherFactoriesParser, OracleParser oracleParser) {
        this.summaryDBService = summaryDBService;
        this.dependencyParser = dependencyParser;
        this.otherFactoriesParser = otherFactoriesParser;
        this.oracleParser = oracleParser;
    }

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        //TODO предварительно нужно очищать summary_table

        summaryDBService.truncateTable();
        dependencyParser.parse(dependenciesMmkPath);
        otherFactoriesParser.parse(otherFactoriesPath);
        oracleParser.parse(oracleMmkPath);
        //write oracleMmk to SummaryDB
    }
}
