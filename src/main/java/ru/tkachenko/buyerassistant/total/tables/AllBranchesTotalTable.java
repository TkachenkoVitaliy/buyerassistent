package ru.tkachenko.buyerassistant.total.tables;

import ru.tkachenko.buyerassistant.summary.service.SummaryInfoUtil;

import java.util.Arrays;
import java.util.List;

public class AllBranchesTotalTable {
    private List<String> branchesNames;
    private List<String> productGroups;
    private int[][] totalData;
    private int[] totalSellTypeData;

    public AllBranchesTotalTable(List<String> branchesNames, List<String> productGroups, int[][] totalData, int[] totalSellTypeData ) {
        this.branchesNames = branchesNames;
        this.productGroups = productGroups;
        this.totalData = totalData;
    }

    public List<String> getBranchesNames() {
        return branchesNames;
    }

    public List<String> getProductGroups() {
        return productGroups;
    }

    public int[] getTotalSellTypeData() {
        return totalSellTypeData;
    }

    public int[][] getTotalData() {
        return totalData;
    }

}
