package edu.hw3;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Task6 {

    private Task6() {
    }

    static class DefaultStockMarket implements StockMarket {

        PriorityQueue<Stock> stocks = new PriorityQueue<>(Comparator.comparingDouble((Stock a) -> a.cost).reversed());

        @Override
        public void add(@NotNull Stock stock) {
            stocks.add(stock);
        }

        @Override
        public void remove(@NotNull Stock stock) {
            stocks.remove(stock);
        }

        @Override
        @Nullable public Stock mostValuableStock() {
            return stocks.peek();
        }
    }

    interface StockMarket {
        /** Добавить акцию */
        void add(Stock stock);

        /** Удалить акцию */
        void remove(Stock stock);

        /** Самая дорогая акция */
        Stock mostValuableStock();
    }

    record Stock(String name, double cost) {
    }

}
