package ru.tkachenko.buyerassistant.total.service;

import java.util.Arrays;
import java.util.List;

public class FactoryTotalTable {
    private String name;
    private List<String> productGroups;
    private List<String> branchesStock;
    private List<String> branchesTransit;
    private int[][] stockData;
    private int[][] transitData;
    private int[] totalData;

    public String getName() {
        return name;
    }

    public List<String> getProductGroups() {
        return productGroups;
    }

    public List<String> getBranchesStock() {
        return branchesStock;
    }

    public List<String> getBranchesTransit() {
        return branchesTransit;
    }

    public int[][] getStockData() {
        return stockData;
    }

    public int[][] getTransitData() {
        return transitData;
    }

    public int[] getTotalData() {
        return totalData;
    }

    private FactoryTotalTable() {
    }

    @Override
    public String toString() {
        return "FactoryTotalTable{" +
                "name='" + name + '\'' +
                ", productGroups=" + productGroups +
                ", branchesStock=" + branchesStock +
                ", branchesTransit=" + branchesTransit +
                ", stockData=" + Arrays.toString(stockData) +
                ", transitData=" + Arrays.toString(transitData) +
                ", totalData=" + Arrays.toString(totalData) +
                '}';
    }

    public static class Builder {
        private FactoryTotalTable newFactoryTotalTable;

        public Builder() {
            newFactoryTotalTable = new FactoryTotalTable();
        }

        public Builder withName(String name) {
            newFactoryTotalTable.name = name;
            return this;
        }

        public Builder withProductGroups(List<String> productGroups) {
            newFactoryTotalTable.productGroups = productGroups;
            return this;
        }

        public Builder withBranchesStock(List<String> branchesStock) {
            newFactoryTotalTable.branchesStock = branchesStock;
            return this;
        }

        public Builder withBranchesTransit(List<String> branchesTransit) {
            newFactoryTotalTable.branchesTransit = branchesTransit;
            return this;
        }

        public Builder withStockData(int[][] stockData) {
            newFactoryTotalTable.stockData = stockData;
            return this;
        }

        public Builder withTransitData(int[][] transitData) {
            newFactoryTotalTable.transitData = transitData;
            return this;
        }

        public Builder withTotalData(int[] totalData) {
            newFactoryTotalTable.totalData = totalData;
            return this;
        }

        public FactoryTotalTable build() {
            return newFactoryTotalTable;
        }
    }
}
