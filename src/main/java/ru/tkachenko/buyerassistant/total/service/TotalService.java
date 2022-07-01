package ru.tkachenko.buyerassistant.total.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.summary.service.SummaryInfoUtil;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;
import ru.tkachenko.buyerassistant.total.tables.AllBranchesTotalTable;
import ru.tkachenko.buyerassistant.total.tables.FactoryTotalTable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TotalService {

    private static final String STOCK = "база";
    private static final String TRANSIT = "транзит";
    private final SummaryDBService summaryDBService;
    private final ProductGroupService productGroupService;
    private final TotalUserSettingsService totalUserSettingsService;

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
        branchesStock.add("БАЗА");

        List<String> branchesTransit = allFactoryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(TRANSIT))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        branchesTransit.add("ТРАНЗИТ");

        int[][] stockData = new int[branchesStock.size()][productGroups.size()];
        int[] stockTotalArray = new int[productGroups.size()];
        for (int i = 0; i < branchesStock.size(); i++) {
            String currentBranch = branchesStock.get(i);
            int totalSum = 0;
            if (i == branchesStock.size() - 1) {
                stockData[i] = stockTotalArray;
                continue;
            }

            for (int j = 0; j < productGroups.size(); j++) {
                if (j == productGroups.size() - 1) {
                    stockData[i][j] = totalSum;
                    stockTotalArray[j] += totalSum;
                } else {
                    ProductGroupEntity currentProductGroup = productGroups.get(j);
                    List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                            .map(e -> e.getName())
                            .collect(Collectors.toList());
                    int sum = roundSumOfProductType(allFactoryRows, currentProductTypeNames, currentBranch, STOCK);

                    stockData[i][j] = sum;
                    totalSum += sum;
                    stockTotalArray[j] += sum;
                }
            }
        }
        //возможно ошибка, смотри выше
        int[][] transitData = new int[branchesTransit.size()][productGroupNames.size()];
        int[] transitTotalArray = new int[productGroups.size()];
        for (int i = 0; i < branchesTransit.size(); i++) {
            String currentBranch = branchesTransit.get(i);
            int totalSum = 0;
            if (i == branchesTransit.size() - 1) {
                transitData[i] = transitTotalArray;
                continue;
            }

            for (int j = 0; j < productGroups.size(); j++) {
                if (j == productGroups.size() - 1) {
                    transitData[i][j] = totalSum;
                    transitTotalArray[j] += totalSum;
                } else {
                    ProductGroupEntity currentProductGroup = productGroups.get(j);
                    List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                            .map(e -> e.getName())
                            .collect(Collectors.toList());
                    int sum = roundSumOfProductType(allFactoryRows, currentProductTypeNames, currentBranch, TRANSIT);

                    transitData[i][j] = sum;
                    totalSum += sum;
                    transitTotalArray[j] += sum;
                }
            }
        }

        int[] finalTotal = new int[productGroups.size()];
        for (int i = 0; i < finalTotal.length; i++) {
            finalTotal[i] = stockTotalArray[i] + transitTotalArray[i];
        }

        FactoryTotalTable factoryTable = new FactoryTotalTable.Builder()
                .withName(factoryName)
                .withProductGroups(productGroupNames)
                .withBranchesStock(branchesStock)
                .withBranchesTransit(branchesTransit)
                .withStockData(stockData)
                .withTransitData(transitData)
                .withTotalData(finalTotal)
                .build();
        return factoryTable;
    }

    public AllBranchesTotalTable createAllBranchesTotalTable() {
        List<ProductGroupEntity> productGroups = productGroupService.findAll();
        TotalUserSettingsEntity userSettings = totalUserSettingsService.getCurrentUserSettings();
        List<SummaryRowEntity> allSummaryRows = summaryDBService.findAllRowsByMonthAndYear(userSettings.getMonth(),
                userSettings.getYear());
        List<String> productGroupNames = productGroups.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        productGroupNames.add(0, "");
        productGroupNames.remove("Не определена");
        productGroupNames.add("ИТОГО");

        String[] allBranchesNames = SummaryInfoUtil.getAllBranchesNames();
        int countForCurrentBranch = 0;
        int[] totalSellTypeData = new int[productGroups.size()];
        int[][] data = new int[(allBranchesNames.length * 2) + 1][productGroups.size()];
        for (int i = 0; i < data.length - 1; i++) {
            int sumCountProductGroupForBranch = 0;
            if (i != 0 && i % 2 == 0) {
                countForCurrentBranch++;
            }
            String currentBranch = allBranchesNames[countForCurrentBranch];
            for (int j = 0; j < productGroups.size(); j++) {
                List<String> currentProductTypeNames = productGroups.get(j).getProductTypes().stream()
                        .map(e -> e.getName())
                        .collect(Collectors.toList());
                int sum;
                if (i % 2 == 0) {
                    sum = roundSumOfProductType(allSummaryRows, currentProductTypeNames, currentBranch, STOCK);
                    data[i][j] = sum;
                    sumCountProductGroupForBranch += sum;
                    totalSellTypeData[j] += sum;
                } else {
                    sum = roundSumOfProductType(allSummaryRows, currentProductTypeNames, currentBranch, TRANSIT);
                    data[i][j] = sum;
                    totalSellTypeData[j] += sum;
                    sumCountProductGroupForBranch += sum;
                }
                if (j == productGroups.size() - 1) {
                    data[i][j] = sumCountProductGroupForBranch;
                    totalSellTypeData[j] += sumCountProductGroupForBranch;
                }
            }
        }
        List<String> branchesNamesWithSellType = new ArrayList<>();
        for (int i = 0; i < allBranchesNames.length; i++) {
            branchesNamesWithSellType.add(allBranchesNames[i] + " База");
            branchesNamesWithSellType.add(allBranchesNames[i] + " Транзит");
        }
        //заглушки
        branchesNamesWithSellType.add("ИТОГО");
        data[data.length - 1] = totalSellTypeData;
        ////
        return new AllBranchesTotalTable(branchesNamesWithSellType, productGroupNames, data, totalSellTypeData);
    }

    private int roundSumOfProductType(List<SummaryRowEntity> allSummaryRows,
                                      List<String> currentProductTypeNames,
                                      String currentBranch,
                                      String sellType) {
        return (int) Math.round(allSummaryRows.stream()
                .filter(Objects::nonNull)
                .filter(e -> Objects.nonNull(e.getBranch()) && e.getBranch().equals(currentBranch))
                .filter(e -> Objects.nonNull(e.getSellType()) && e.getSellType().equals(sellType))
                .filter(e -> Objects.nonNull(e.getProductType()) &&
                        currentProductTypeNames.contains(e.getProductType()))
                .distinct()//не уверен что это необходимо
                .map(SummaryRowEntity::getAccepted)
                .reduce((double) 0, (x, y) -> x + y));
    }
}
