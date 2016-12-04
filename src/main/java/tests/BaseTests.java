package tests;

import entities.PokerPlayer;
import helpers.RandomManager;

/**
 * Created by numash on 04.12.2016.
 */
public class BaseTests {
    private RandomManager randomManager = new RandomManager();

    //fills poker player fields with random data
    protected PokerPlayer createRandomPokerPlayer() {

        return new PokerPlayer(
                "user68_" + randomManager.getRandomAlphaAndNumberString(5),
                "user68_" + randomManager.getRandomAlphaAndNumberString(5) + "@gmail.com",
                "first" + randomManager.getRandomAlphaAndNumberString(5),
                "last" + randomManager.getRandomAlphaAndNumberString(5),
                "City." + randomManager.getRandomAlphaString(5),
                "UKRAINE",
                "Address68, " + randomManager.getRandomAlphaAndNumberString(5),
                "+312345678, 890",
                "Male",
                //"10-10-1990");
                randomManager.getRandomDate());
    }

    protected void updatePlayerData(PokerPlayer player){
        PokerPlayer randomPlayer = createRandomPokerPlayer();

        player.setEmail(randomPlayer.getEmail());
        player.setFirstname(randomPlayer.getFirstname());
        player.setLastname(randomPlayer.getLastname());
        player.setAddress(randomPlayer.getAddress());
        player.setCity(randomPlayer.getCity());
        player.setCountry(randomPlayer.getCountry());
        player.setPhone(randomPlayer.getPhone());
        player.setGender(randomPlayer.getGender());
        player.setBirthday(randomPlayer.getBirthday());
    }
}
