package pages;

import entities.PokerPlayer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by numash on 02.12.2016.
 */
public class InsertOrEditPlayerPage extends BasePage{

    private InsertOrEditPlayerPage(WebDriver driver) {
        super(driver);
    }

    //initialize a new instance and open page
    public static InsertOrEditPlayerPage openInsertPlayerPage(WebDriver driver){
        InsertOrEditPlayerPage page = new InsertOrEditPlayerPage(driver);
        page.openInsertPlayerPage();

        return page;
    }

    public static InsertOrEditPlayerPage openEditPlayerPage(WebDriver driver){
        return new InsertOrEditPlayerPage(driver);
    }

    protected String getRelativeUrl() {
        return "players/insert";
    }

    private void openInsertPlayerPage(){
        driver.get(getFullUrl());
    }

    public void createPlayer(PokerPlayer player, String password){
        setUsernameFieldValue(player.getUsername());
        setPasswordFieldValue(password);
        setConfirmPasswordFieldValue(password);

        fillUpdatePlayerForm(player);

        clickSaveButton();
    }

    public void createPlayer(PokerPlayer player, String password, String confirmPassword){
        setUsernameFieldValue(player.getUsername());
        setPasswordFieldValue(password);
        setConfirmPasswordFieldValue(confirmPassword);

        fillUpdatePlayerForm(player);

        clickSaveButton();
    }

    public void updatePlayer(PokerPlayer player) {
        fillUpdatePlayerForm(player);

        clickSaveButton();
    }

    public PokerPlayer readPokerPlayerFromPage(){
        PokerPlayer player = new PokerPlayer(
                getUsernameFieldValue(),
                getEmailFieldValue(),
                getFirstNameFieldValue(),
                getLastNameFieldValue(),
                getCityFieldValue(),
                getCountryValue(),
                getAddressFieldValue(),
                getPhoneFieldValue(),
                getGenderValue(),
                getBirthdayFieldValue()
        );
        return player;
    }

    public void setUsernameFieldValue(String username){
        clearAndFillFieldWithValue("//input[contains(@id, 'login')]", username);
    }

    public void setEmailFieldValue(String email){
        clearAndFillFieldWithValue("//input[contains(@id, 'email')]", email);
    }

    public void setPasswordFieldValue(String password){
        clearAndFillFieldWithValue("//input[contains(@id, 'password') and not(contains(@id, 'confirm'))]", password);
    }

    public void setConfirmPasswordFieldValue(String password){
        clearAndFillFieldWithValue("//input[contains(@id, 'confirm_password')]", password);
    }

    public void setFirstNameFieldValue(String firstName){
        clearAndFillFieldWithValue("//input[contains(@id, 'fname')]", firstName);
    }

    public void setLastNameFieldValue(String lastName){
        clearAndFillFieldWithValue("//input[contains(@id, 'lname')]", lastName);
    }

    public void setCityFieldValue(String city){
        clearAndFillFieldWithValue("//input[contains(@id, 'city')]", city);
    }

    public void setCountryValue(String country){
        fillFieldWithValue("//select[contains(@id, 'us_country')]", country);
    }

    public void setAddressFieldValue(String address){
        clearAndFillFieldWithValue("//textarea[contains(@id, 'us_address')]", address);
    }

    public void setPhoneFieldValue(String phone){
        clearAndFillFieldWithValue("//input[contains(@id, 'us_phone')]", phone);
    }

    public void setGenderValue(String gender){
        fillFieldWithValue(".//select[contains(@id, 'gender')]", gender);
    }

    public void setBirthdayFieldValue(String birthday){
        clearAndFillFieldWithValue(".//input[contains(@id, 'birthday')]", birthday);
    }

    public String getUsernameFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'login')]")).getAttribute("Value");
    }

    public String getEmailFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'email')]")).getAttribute("Value");
    }

    public String getFirstNameFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'fname')]")).getAttribute("Value");
    }

    public String getLastNameFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'lname')]")).getAttribute("Value");
    }

    public String getCityFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'us_city')]")).getAttribute("Value");
    }

    public String getCountryValue(){
        try {
            return driver.findElement(By.xpath("//select[contains(@id, 'us_country')]/option[@selected='selected']")).getText();
        } catch(NoSuchElementException e){
            return "";
        }
    }

    public String getAddressFieldValue(){
        return driver.findElement(By.xpath("//textarea[contains(@id, 'us_address')]")).getText();
    }

    public String getPhoneFieldValue(){
        return driver.findElement(By.xpath("//input[contains(@id, 'us_phone')]")).getAttribute("Value");
    }

    public String getGenderValue(){
        return driver.findElement(By.xpath("//select[contains(@id, 'gender')]/option[@selected='selected']")).getText();
    }

    public String getBirthdayFieldValue(){
        return driver.findElement(By.xpath(".//input[contains(@id, 'birthday')]")).getAttribute("Value");
    }

    public String getFieldValidationMessage(String fieldName) {
        try {
            WebElement message = driver.findElement(By.xpath(".//*[contains(@class, 'errors')]/ul/li[contains(text(), ':" + fieldName + "')]"));
            return message.getText();
        }catch(Exception e){
            return null;
        }
    }

    public void closePage(){
        driver.close();
    }

    private void fillUpdatePlayerForm(PokerPlayer player){
        setEmailFieldValue(player.getEmail());
        setFirstNameFieldValue(player.getFirstname());
        setLastNameFieldValue(player.getLastname());
        setCityFieldValue(player.getCity());
        setCountryValue(player.getCountry());
        setAddressFieldValue(player.getAddress());
        setPhoneFieldValue(player.getPhone());
        setGenderValue(player.getGender());
        setBirthdayFieldValue(player.getBirthday());
    }

    private void clickSaveButton(){
        WebElement saveBtn = driver.findElement(By.name("button_save"));
        saveBtn.click();
    }
}
