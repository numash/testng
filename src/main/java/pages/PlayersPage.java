package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public InsertOrEditPlayerPage openEditPlayerPage() {
        WebElement playerEditLink = driver.findElement(By.xpath("(.//tr[.//a]//a[img[@alt='Edit']])[1]"));
        playerEditLink.click();

        return InsertOrEditPlayerPage.openEditPlayerPage(driver);
    }

    public InsertOrEditPlayerPage openViewPlayerPage(String username) {
        searchPlayerByUsername(username);
        WebElement playerViewLink = driver.findElement(By.xpath(".//a[text()='" + username + "']"));
        playerViewLink.click();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        InsertOrEditPlayerPage page = InsertOrEditPlayerPage.openEditPlayerPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("button_ok")));

        return page;
    }

    public void deletePlayer(String username, String answer){
        searchPlayerByUsername(username);
        WebElement deleteLink = driver.findElement(By.xpath(".//tr[.//a[text()='"
                    + username + "']]//a[.//img[@alt='Delete']]"));
        deleteLink.click();

        if (answer.equals("Ok")){
            //click "OK" on alert
            driver.switchTo().alert().accept();
        } else{
            driver.switchTo().alert().dismiss();
        }
    }

    //checks the presence of certain player in the search result table
    public boolean doesTableContainPlayer(String username) {
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

    public void searchPlayerByUsername(String username) {
        clearAllFields();
        clearAndFillFieldWithValue("//input[contains(@id, 'login') and not(contains(@id, 'last'))]", username);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }

    public void searchPlayerByEmail(String email) {
        clearAllFields();
        clearAndFillFieldWithValue("//input[contains(@id, 'email')]", email);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }

    public void searchPlayerByCity(String city) {
        clearAllFields();
        clearAndFillFieldWithValue("//input[contains(@id, 'city')]", city);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }

    public void searchPlayerByFirstName(String firstName) {
        clearAllFields();
        clearAndFillFieldWithValue("//input[contains(@id, 'firstname')]", firstName);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }

    public void searchPlayerByLastName(String lastName) {
        clearAllFields();
        clearAndFillFieldWithValue("//input[contains(@id, 'lastname')]", lastName);

        WebElement searchBtn = driver.findElement(By.name("search"));
        searchBtn.click();
    }

    public void clearAllFields(){
        clearField("//input[contains(@id, 'login') and not(contains(@id, 'last'))]");
        clearField("//input[contains(@id, 'email')]");
        clearField("//input[contains(@id, 'city')]");
        clearField("//input[contains(@id, 'firstname')]");
        clearField("//input[contains(@id, 'lastname')]");
    }

    public void resetFields(){
        WebElement resetBtn = driver.findElement(By.xpath("//input[@name='reset']"));
        resetBtn.click();
    }
}
