package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SummaryService {

    private final OtherFactoriesParser otherFactoriesParser;

    @Autowired
    public SummaryService(OtherFactoriesParser otherFactoriesParser) {
        this.otherFactoriesParser = otherFactoriesParser;
    }

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        //write otherFactories to SummaryDB
        otherFactoriesParser.parse(otherFactoriesPath);
        //write oracleMmk to SummaryDB
    }
}
