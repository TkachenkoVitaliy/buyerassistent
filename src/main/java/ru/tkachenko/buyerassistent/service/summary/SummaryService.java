package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SummaryService {

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        //write otherFactories to SummaryDB
        OtherFactoriesParser otherFactoriesParser = new OtherFactoriesParser(otherFactoriesPath);
        otherFactoriesParser.parse();
        //write oracleMmk to SummaryDB
    }
}
