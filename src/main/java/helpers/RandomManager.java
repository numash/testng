package helpers;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by numash on 02.12.2016.
 */
public class RandomManager {
    private Random randomNumber;
    private static final String alphaMixedCaseCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQSTUVWXYZ";
    private static final String numbers = "1234567890";
    private static final String allowedSpecialCharacters = "_.-";
    private static final String notAllowedSpecialCharacters = " !\"#$%&'()*+,/:;<=>?@[\\]^`{|}~";

    public RandomManager() {
        randomNumber = new Random();
    }

    //generates random string of a certain length
    public String getRandomAlphaString(int length) {
        String alphabet = alphaMixedCaseCharacters;
        return getRandomString(length, alphabet);
    }

    //generates random string of a certain length
    public String getRandomAlphaAndNumberString(int length) {
        String alphabet = alphaMixedCaseCharacters + numbers;
        return getRandomString(length, alphabet);
    }

    //generates random string of a certain length
    public String getRandomAllowedSpecialCharacterString(int length) {
        String alphabet = allowedSpecialCharacters;
        return getRandomString(length, alphabet);
    }

    //generates random string of a certain length
    public String getRandomNotAllowedSpecialCharacterString(int length) {
        String alphabet = notAllowedSpecialCharacters;
        return getRandomString(length, alphabet);
    }

    //generates random number string of a certain length
    public String getRandomNumberString(int length) {
        String alphabet = numbers;
        return getRandomString(length, alphabet);
    }

    //generates random date
    public String getRandomDate() {

        SimpleDateFormat dfDateTime = new SimpleDateFormat("dd-MM-yyyy");
        int year = randBetween(1950, 1997);
        int month = randBetween(0, 11);

        GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, calendar.getActualMaximum(calendar.DAY_OF_MONTH));

        calendar.set(year, month, day);
        return dfDateTime.format(calendar.getTime());
    }

    //generates random string of a certain length and characters
    private String getRandomString(int length, String alphabet) {
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += alphabet.charAt(randomNumber.nextInt(alphabet.length()));
        }
        return randomString;
    }

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
