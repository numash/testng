package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by numash on 30.11.2016.
 */
public class PlayersPage extends BasePage{

    public PlayersPage(WebDriver driver) {
        super(driver);
    }

    //initialize a new instance and open page
    public static PlayersPage openPlayersPage(WebDriver driver){
        PlayersPage page = new PlayersPage(driver);
        page.open();

        return page;
    }

    protected String getRelativeUrl() {
        return "players";
    }

    private void open(){
        driver.get(getFullUrl());
    }

    public void refresh(){
        driver.get(getFullUrl());
    }

    public void clickOnInsertLink(){
        WebElement insertLink = driver.findElement(By.linkText("Insert"));
        insertLink.click();
    }

    public InsertOrEditPlayerPage openEditPlayerPage(String username) {
        searchPlayerByUsername(username);
        WebElement playerEditLink = driver.findElement(By.xpath(".//tr[.//a[text()='" + username + "']]//a[img[@alt='Edit']]"));
        playerEditLink.click();

        return InsertOrEditPlayerPage.openEditPlayerPage(driver);
    }

    public void deletePlayer(String username){
        searchPlayerByUsername(username);
        WebElement deleteLink = driver.findElement(By.xpath(".//tr[.//a[text()='"
                    + username + "']]//a[.//img[@alt='Delete']]"));
        deleteLink.click();
        //click "OK" on alert
        driver.switchTo().alert().accept();
    }

    //checks the presence of certain player in the search result table
    public boolean doesTableContainPlayer(String username) {
        searchPlayerByUsername(username);
        try {
            driver.findElement(By.xpath(".//tr[.//a[text()='"
                    + username + "']]"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public String getFlashMessage(){
        try {
            WebElement message = driver.findElement(By.xpath(".//*[contains(@id, 'flashmessagespanel')]//*[text()='Player has been deleted']"));
            return message.getText();
        }catch(Exception e){
            return null;
        }
    }

    private void searchPlayerByUsername(String username) {
        clearAndFillFieldWithValue("//input[contains(@id, 'login') and not(contains(@id, 'last'))]", username);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }
}
