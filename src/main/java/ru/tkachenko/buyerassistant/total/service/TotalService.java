package ru.tkachenko.buyerassistant.total.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.service.TotalUserSettingsService;
import ru.tkachenko.buyerassistant.total.tables.FactoryTotalTable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TotalService {

    private static final String STOCK = "база";
    private static final String TRANSIT = "транзит";
    private static final String SUMMARY = "СВОДНАЯ";
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

    public List<FactoryTotalTable> createFactoryTables(String username) {
        System.out.println(username);
        TotalUserSettingsEntity userSettings = totalUserSettingsService.getCurrentUserSettings(username);
        List<ProductGroupEntity> productGroups = productGroupService.findAll();
        System.out.println(userSettings);
        List<SummaryRowEntity> allSummaryRows = summaryDBService.findAllRowsByMonthAndYear(userSettings.getMonth(),
                userSettings.getYear());

        List<String> factoryNames = allSummaryRows.stream()
                .map(e -> e.getSupplier())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        factoryNames.add(0, SUMMARY);

        List<FactoryTotalTable> factoryTables = factoryNames.stream()
                .map(e -> createFactoryTotalTable(e, productGroups, allSummaryRows))
                .collect(Collectors.toList());

        return factoryTables;
    }

    private FactoryTotalTable createFactoryTotalTable(String factoryName, List<ProductGroupEntity> productGroups,
                                                      List<SummaryRowEntity> allSummaryRows) {
        List<SummaryRowEntity> allFactoryRows = getSummaryRowEntitiesByFactory(factoryName, allSummaryRows);

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

        int[][] stockData = new int[branchesStock.size()][productGroups.size()];
        int[] stockTotalArray = new int[productGroups.size()];
        fillDataArrays(productGroups, allFactoryRows, branchesStock, stockData, stockTotalArray, STOCK);

        List<String> branchesTransit = allFactoryRows.stream()
                .filter(e -> e.getSellType() != null && e.getSellType().equals(TRANSIT))
                .map(e -> e.getBranch())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        branchesTransit.add("ТРАНЗИТ");

        int[][] transitData = new int[branchesTransit.size()][productGroupNames.size()];
        int[] transitTotalArray = new int[productGroups.size()];
        fillDataArrays(productGroups, allFactoryRows, branchesTransit, transitData, transitTotalArray, TRANSIT);

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

    private List<SummaryRowEntity> getSummaryRowEntitiesByFactory(String factoryName, List<SummaryRowEntity> allSummaryRows) {
        if(!factoryName.equals(SUMMARY)) {
            return allSummaryRows.stream()
                    .filter(e -> e.getSupplier().equals(factoryName) && e.getAccepted() > 0)
                    .collect(Collectors.toList());
        }
        return allSummaryRows;
    }


    private void fillDataArrays(List<ProductGroupEntity> productGroups, List<SummaryRowEntity> allFactoryRows,
                                List<String> branchesList, int[][] sellTypeData, int[] sellTypeSumArray,
                                String sellType) {
        for (int i = 0; i < branchesList.size(); i++) {
            String currentBranch = branchesList.get(i);
            int totalSum = 0;
            if (i == branchesList.size() - 1) {
                sellTypeData[i] = sellTypeSumArray;
                continue;
            }

            for (int j = 0; j < productGroups.size(); j++) {
                if (j == productGroups.size() - 1) {
                    sellTypeData[i][j] = totalSum;
                    sellTypeSumArray[j] += totalSum;
                } else {
                    ProductGroupEntity currentProductGroup = productGroups.get(j);
                    List<String> currentProductTypeNames = currentProductGroup.getProductTypes().stream()
                            .map(e -> e.getName())
                            .collect(Collectors.toList());
                    int sum = roundSumOfProductType(allFactoryRows, currentProductTypeNames, currentBranch, sellType);

                    sellTypeData[i][j] = sum;
                    totalSum += sum;
                    sellTypeSumArray[j] += sum;
                }
            }
        }
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
                .map(SummaryRowEntity::getAccepted)
                .reduce((double) 0, (x, y) -> x + y));
    }
}
