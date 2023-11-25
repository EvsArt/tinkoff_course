package edu.hw8.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceTest {

    QuoteService service = new QuoteService();

    @Test
    @DisplayName("Test getting quotes")
    void getQuoteByWord() {

        String keyWord1 = "голова";
        String keyWord2 = "acwsydcf7326";

        String quote1 = service.getQuoteByWord(keyWord1);
        String quote2 = service.getQuoteByWord(keyWord2);

        assertNotEquals(quote1.length(), 0);
        assertEquals(quote2, QuoteService.QUOTE_NOT_FOUND_MSG);

        assertThrows(NullPointerException.class, () -> service.getQuoteByWord(null));
    }
}
