package helpers;

import java.util.Random;

/**
 * Created by numash on 02.12.2016.
 */
public class RandomManager {
    private Random randomNumber;
    private String alphabet = "1234567890abcdefghijklmnopqrstuvwxyz";

    public RandomManager() {
        randomNumber = new Random();
    }

    //generates random string of a certain length
    public String getRandomString(int length) {
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += alphabet.charAt(randomNumber.nextInt(alphabet.length()));
        }
        return randomString;
    }

}
