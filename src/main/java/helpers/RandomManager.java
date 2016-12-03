package helpers;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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

    //generates random date
    public String getRandomDate() {

        SimpleDateFormat dfDateTime = new SimpleDateFormat("dd-MM-yyyy");
        int year = randBetween(1950, 1997);
        int month = randBetween(0, 11);

        GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, calendar.getActualMaximum(calendar.DAY_OF_MONTH));

        calendar.set(year, month, day);

        System.out.println(dfDateTime.format(calendar.getTime()));
        return dfDateTime.format(calendar.getTime());
    }

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
