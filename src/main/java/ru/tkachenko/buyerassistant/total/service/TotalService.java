package ru.tkachenko.buyerassistant.total.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TotalService {

    private final SummaryDBService summaryDBService;
    private final ProductGroupService productGroupService;
    private final TotalUserSettingsService totalUserSettingsService;
    private static final String STOCK = "база";
    private static final String TRANSIT = "транзит";

    @Autowired
    public TotalService(SummaryDBService summaryDBService, ProductGroupService productGroupService,
                        TotalUserSettingsService totalUserSettingsService) {
        this.summaryDBService = summaryDBService;
        this.productGroupService = productGroupService;
        this.totalUserSettingsService = totalUserSettingsService;
    }

    public List<FactoryTotalTable> createFactoryTables() {
        TotalUserSettingsEntity userSettings = totalUserSettingsService.getCurrentUserSettings();
        List<ProductGroupEntity> productGroups = productGroupService.findAll();

        List<SummaryRowEntity> allSummaryRows = summaryDBService.findAllRowsByMonthAndYear(userSettings.getMonth(),
                userSettings.getYear());

        List<String> factoryNames = allSummaryRows.stream()
                .map(e -> e.getSupplier())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<FactoryTotalTable> factoryTables = factoryNames.stream()
                .map(e -> createFactoryTotalTable(e, productGroups, userSettings, allSummaryRows))
                .collect(Collectors.toList());

        return factoryTables;
    }

    private FactoryTotalTable createFactoryTotalTable(String factoryName, List<ProductGroupEntity> productGroups,
                                                      TotalUserSettingsEntity userSettings,
                                                      List<SummaryRowEntity> allSummaryRows) {
        List<SummaryRowEntity> allFactoryRows = allSummaryRows.stream()
                .filter(e -> e.getSupplier().equals(factoryName) && e.getAccepted() > 0)
                .collect(Collectors.toList());

        List<String> productGroupNames = productGroups.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        productGroupNames.remove("Не определена");
        productGroupNames.add("ИТОГО");

        List<String> branchesStock = allFactoryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(STOCK))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> branchesTransit = allFactoryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(TRANSIT))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        int[][] stockData = new int[branchesStock.size()][productGroups.size()];
        for (int i = 0; i < branchesStock.size(); i++) {
            String currentBranch = branchesStock.get(i);
            int totalSum = 0;
            for (int j = 0; j < productGroups.size(); j++) {
                if(j == productGroups.size()-1) {
                    stockData[i][j] = totalSum;
                } else {
                    ProductGroupEntity currentProductGroup = productGroups.get(j);
                    List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                            .map(e -> e.getName())
                            .collect(Collectors.toList());
                    Double sum = allFactoryRows.stream()
                            .filter(e -> Objects.nonNull(e))
                            .filter(e -> Objects.nonNull(e.getBranch()) && e.getBranch().equals(currentBranch))
                            .filter(e -> Objects.nonNull(e.getSellType()) && e.getSellType().equals(STOCK))
                            .filter(e -> Objects.nonNull(e.getProductType()) && currentProductTypeNames.contains(e.getProductType()))
                            .map(e -> e.getAccepted())
                            .reduce((double) 0, (x, y) -> x + y);
                    int roundSum = (int) Math.round(sum);

                    stockData[i][j] = roundSum;
                    totalSum += roundSum;
                }
            }
        }

        int[][] transitData = new int[branchesTransit.size()][productGroupNames.size()];

        for (int i = 0; i < branchesTransit.size(); i++) {
            String currentBranch = branchesTransit.get(i);
            int totalSum = 0;
            for (int j = 0; j < productGroups.size(); j++) {
                if(j == productGroups.size()-1) {
                    transitData[i][j] = totalSum;
                } else {
                    ProductGroupEntity currentProductGroup = productGroups.get(j);
                    List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                            .map(e -> e.getName())
                            .collect(Collectors.toList());
                    Double sum = allFactoryRows.stream()
                            .filter(e -> Objects.nonNull(e))
                            .filter(e -> Objects.nonNull(e.getBranch()) && e.getBranch().equals(currentBranch))
                            .filter(e -> Objects.nonNull(e.getSellType()) && e.getSellType().equals(TRANSIT))
                            .filter(e -> Objects.nonNull(e.getProductType()) && currentProductTypeNames.contains(e.getProductType()))
                            .map(e -> e.getAccepted())
                            .reduce((double) 0, (x, y) -> x + y);
                    int roundSum = (int) Math.round(sum);

                    transitData[i][j] = roundSum;
                    totalSum += roundSum;
                }
            }
        }

        FactoryTotalTable factoryTable = new FactoryTotalTable.Builder()
                .withName(factoryName)
                .withProductGroups(productGroupNames)
                .withBranchesStock(branchesStock)
                .withBranchesTransit(branchesTransit)
                .withStockData(stockData)
                .withTransitData(transitData)
                .build();

        return factoryTable;
    }
}
