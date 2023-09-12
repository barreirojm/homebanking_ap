package com.ap.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {
    }

    public static String getCardNumber() {
        Random rand = new Random();
        return String.format("%04d-%04d-%04d-%04d",
                rand.nextInt(10000), rand.nextInt(10000),
                rand.nextInt(10000), rand.nextInt(10000));
    }

    public static int getCVV() {
        Random rand = new Random();
        return rand.nextInt(1000);
    }
}
