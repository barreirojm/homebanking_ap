package com.ap.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardCVVCreated(){
        int cardCVV = CardUtils.getCVV();
        assertThat(cardCVV, is(notNullValue()));
        assertThat(String.valueOf(cardCVV), is(not(emptyString())));
    }
}
