package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Биржа")
class Task6Test {

    @Test
    @DisplayName("TestNormalData")
    public void normalTest() {
        Task6.StockMarket market = new Task6.DefaultStockMarket();

        Task6.Stock tinkoff = new Task6.Stock("Tinkoff", 1000);
        Task6.Stock myBigComp = new Task6.Stock("MyBigComp", 10000);
        Task6.Stock mySmallComp = new Task6.Stock("MySmallComp", 500);
        Task6.Stock company = new Task6.Stock("Company", 999.999);

        market.add(tinkoff);
        market.add(company);
        market.add(myBigComp);
        market.add(mySmallComp);
        Task6.Stock res1 = market.mostValuableStock();
        market.remove(res1);
        Task6.Stock res2 = market.mostValuableStock();
        market.remove(res2);
        Task6.Stock res3 = market.mostValuableStock();

        assertThat(res1).isEqualTo(myBigComp);
        assertThat(res2).isEqualTo(tinkoff);
        assertThat(res3).isEqualTo(company);

    }

    @Test
    @DisplayName("TestFailData")
    public void failTest() {
        Task6.StockMarket market = new Task6.DefaultStockMarket();

        Task6.Stock company = new Task6.Stock("", 0);
        Task6.Stock mySmallComp = new Task6.Stock("MySmallComp", Double.MIN_VALUE);
        Task6.Stock myBigComp = new Task6.Stock(null, Double.MAX_VALUE);

        market.add(company);
        market.add(myBigComp);
        market.add(mySmallComp);
        Task6.Stock res1 = market.mostValuableStock();
        market.remove(res1);
        Task6.Stock res2 = market.mostValuableStock();
        market.remove(res2);
        Task6.Stock res3 = market.mostValuableStock();

        assertThat(res1).isEqualTo(myBigComp);
        assertThat(res2).isEqualTo(mySmallComp);
        assertThat(res3).isEqualTo(company);

    }

}
