package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by numash on 30.11.2016.
 */
public class PlayersPage extends BasePage{

    @FindBy(name = "button_ok")
    private WebElement okButton;

    @FindBy(xpath = ".//*[contains(@id, 'flashmessagespanel')]//*[text()='Player has been deleted']")
    private WebElement flashMessage;

    @FindBy(xpath = "//input[contains(@id, 'login') and not(contains(@id, 'last'))]")
    private WebElement loginInput;

    @FindBy(xpath = "//input[contains(@id, 'email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[contains(@id, 'city')]")
    private WebElement cityInput;

    @FindBy(xpath = "//input[contains(@id, 'firstname')]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[contains(@id, 'lastname')]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@name='reset']")
    private WebElement resetButton;

    @FindBy(name = "search")
    private WebElement searchButton;

    private String winHandleBefore;

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

    public InsertOrEditPlayerPage openEditPlayerPage(String username) {
        searchPlayerByUsername(username);
        WebElement playerEditLink = driver.findElement(By.xpath(".//tr[.//a[text()='" + username + "']]//a[img[@alt='Edit']]"));
        playerEditLink.click();

        return InsertOrEditPlayerPage.openEditPlayerPage(driver);
    }

    public InsertOrEditPlayerPage openViewPlayerPage(String username) {
        winHandleBefore = driver.getWindowHandle();
        searchPlayerByUsername(username);
        WebElement playerViewLink = driver.findElement(By.xpath(".//a[text()='" + username + "']"));
        playerViewLink.click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        InsertOrEditPlayerPage page = InsertOrEditPlayerPage.openEditPlayerPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(okButton));

        return page;
    }

    public void closeViewPlayerPage(){
        driver.close();
        driver.switchTo().window(winHandleBefore);
    }

    public void deletePlayer(String username, String answer){
        searchPlayerByUsername(username);
        WebElement playerDeleteLink = driver.findElement(By.xpath(".//tr[.//a[text()='"
                    + username + "']]//a[.//img[@alt='Delete']]"));
        playerDeleteLink.click();

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
            return flashMessage.getText();
        }catch(Exception e){
            return null;
        }
    }

    public void searchPlayerByUsername(String username) {
        clearAllFields();
        clearAndFillFieldWithValue(loginInput, username);

        searchButton.click();
    }

    public void searchPlayerByEmail(String email) {
        clearAllFields();
        clearAndFillFieldWithValue(emailInput, email);

        searchButton.click();
    }

    public void searchPlayerByCity(String city) {
        clearAllFields();
        clearAndFillFieldWithValue(cityInput, city);

        searchButton.click();
    }

    public void searchPlayerByFirstName(String firstName) {
        clearAllFields();
        clearAndFillFieldWithValue(firstNameInput, firstName);

        searchButton.click();
    }

    public void searchPlayerByLastName(String lastName) {
        clearAllFields();
        clearAndFillFieldWithValue(lastNameInput, lastName);

        searchButton.click();
    }

    public void clearAllFields(){
        clearField(loginInput);
        clearField(emailInput);
        clearField(cityInput);
        clearField(firstNameInput);
        clearField(lastNameInput);
    }

    public void resetFields(){
        resetButton.click();
    }
}
