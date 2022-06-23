package ru.tkachenko.buyerassistant.total.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;

import java.util.List;
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

//        List<String> factoryNames = summaryDBService.findUniqueSuppliersByMonthAndYear(userSettings.getMonth(),
//                userSettings.getYear());

        List<FactoryTotalTable> factoryTables = factoryNames.stream()
                .map(e -> createFactoryTotalTable(e, productGroups, userSettings, allSummaryRows))
                .collect(Collectors.toList());

        return factoryTables;
    }

    private FactoryTotalTable createFactoryTotalTable(String factoryName, List<ProductGroupEntity> productGroups,
                                                      TotalUserSettingsEntity userSettings,
                                                      List<SummaryRowEntity> allSummaryRows) {
        List<SummaryRowEntity> factorySummaryRows = allSummaryRows.stream()
                .filter(e -> e.getSupplier().equals(factoryName) && e.getAccepted() > 0)
                .collect(Collectors.toList());

        List<String> productGroupNames = productGroups.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());

        List<String> branchesStock = factorySummaryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(STOCK))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> branchesTransit = factorySummaryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(TRANSIT))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        int[][] stockData = new int[branchesStock.size()][productGroups.size()];
        for (int i = 0; i < branchesStock.size(); i++) {
            String currentBranch = branchesStock.get(i);
            for (int j = 0; j < productGroups.size(); j++) {
                ProductGroupEntity currentProductGroup = productGroups.get(j);
                List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                        .map(e -> e.getName())
                        .collect(Collectors.toList());
                Double sum = factorySummaryRows.stream()
                        .filter(e -> currentProductTypeNames.contains(e.getProductType()))
                        .filter(e -> e.getBranch().equals(currentBranch))
                        .filter(e -> e.getSellType().equals(STOCK))
                        .map(e -> e.getAccepted())
                        .reduce((double) 0, (x, y) -> x + y);
                int roundSum = (int) Math.round(sum);

                stockData[i][j] = roundSum;
            }
        }

        System.out.println(factoryName);
        for (int a = 0; a < branchesStock.size(); a++) {
            for(int b = 0; b < productGroups.size(); b++) {
                System.out.print(stockData[a][b]);
                System.out.print("  ");
            }
            System.out.println();
        }

        System.out.println("\n\n");


        int[][] transitData = new int[branchesTransit.size()][productGroupNames.size()];


//        List<String> branchesStock = summaryDBService
//                .findUniqueBranchesByMonthAndYearAndSellType(userSettings.getMonth(), userSettings.getYear(), STOCK);
//        List<String> branchesTransit = summaryDBService
//                .findUniqueBranchesByMonthAndYearAndSellType(userSettings.getMonth(), userSettings.getYear(), TRANSIT);

        FactoryTotalTable factoryTable = new FactoryTotalTable.Builder()
                .withName(factoryName)
                .withProductGroups(productGroupNames)
                .withBranchesStock(branchesStock)
                .withBranchesTransit(branchesTransit)
                .withStockData(null)
                .withTransitData(null)
                .build();

        return factoryTable;
    }
}
