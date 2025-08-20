package com.bennu.app;

import java.util.Random;

public class RandomNumberGenerator {

    int randomNumber;

    public int generateRandomNumber() {
        randomNumber = new Random().nextInt(1_000);
        return randomNumber;
    }

}
