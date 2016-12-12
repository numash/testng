package pages;

import entities.PokerPlayer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by numash on 02.12.2016.
 */
public class InsertOrEditPlayerPage extends BasePage{

    @FindBy(xpath = "//input[contains(@id, 'login')]")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[contains(@id, 'email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[contains(@id, 'password') and not(contains(@id, 'confirm'))]")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[contains(@id, 'confirm_password')]")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//input[contains(@id, 'fname')]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[contains(@id, 'lname')]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[contains(@id, 'city')]")
    private WebElement cityInput;

    @FindBy(xpath = "//select[contains(@id, 'us_country')]")
    private WebElement countrySelect;

    @FindBy(xpath = "//textarea[contains(@id, 'us_address')]")
    private WebElement addressInput;

    @FindBy(xpath = "//input[contains(@id, 'us_phone')]")
    private WebElement phoneInput;

    @FindBy(xpath = ".//select[contains(@id, 'gender')]")
    private WebElement genderSelect;

    @FindBy(xpath = ".//input[contains(@id, 'birthday')]")
    private WebElement birthdayInput;

    @FindBy(xpath = "//select[contains(@id, 'us_country')]/option[@selected='selected']")
    private WebElement selectedCountryOption;

    @FindBy(xpath = "//select[contains(@id, 'gender')]/option[@selected='selected']")
    private WebElement selectedGenderOption;

    @FindBy(name = "button_save")
    private WebElement saveButton;

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
        clearAndFillFieldWithValue(usernameInput, username);
    }

    public void setEmailFieldValue(String email){
        clearAndFillFieldWithValue(emailInput, email);
    }

    public void setPasswordFieldValue(String password){
        clearAndFillFieldWithValue(passwordInput, password);
    }

    public void setConfirmPasswordFieldValue(String password){
        clearAndFillFieldWithValue(confirmPasswordInput, password);
    }

    public void setFirstNameFieldValue(String firstName){
        clearAndFillFieldWithValue(firstNameInput, firstName);
    }

    public void setLastNameFieldValue(String lastName){
        clearAndFillFieldWithValue(lastNameInput, lastName);
    }

    public void setCityFieldValue(String city){
        clearAndFillFieldWithValue(cityInput, city);
    }

    public void setCountryValue(String country){
        fillFieldWithValue(countrySelect, country);
    }

    public void setAddressFieldValue(String address){
        clearAndFillFieldWithValue(addressInput, address);
    }

    public void setPhoneFieldValue(String phone){
        clearAndFillFieldWithValue(phoneInput, phone);
    }

    public void setGenderValue(String gender){
        fillFieldWithValue(genderSelect, gender);
    }

    public void setBirthdayFieldValue(String birthday){
        clearAndFillFieldWithValue(birthdayInput, birthday);
    }

    public String getUsernameFieldValue(){
        return usernameInput.getAttribute("Value");
    }

    public String getEmailFieldValue(){
        return emailInput.getAttribute("Value");
    }

    public String getFirstNameFieldValue(){
        return firstNameInput.getAttribute("Value");
    }

    public String getLastNameFieldValue(){
        return lastNameInput.getAttribute("Value");
    }

    public String getCityFieldValue(){
        return cityInput.getAttribute("Value");
    }

    public String getCountryValue(){
        try {
            return selectedCountryOption.getText();
        } catch(NoSuchElementException e){
            return "";
        }
    }

    public String getAddressFieldValue(){
        return addressInput.getText();
    }

    public String getPhoneFieldValue(){
        return phoneInput.getAttribute("Value");
    }

    public String getGenderValue(){
        return selectedGenderOption.getText();
    }

    public String getBirthdayFieldValue(){
        return birthdayInput.getAttribute("Value");
    }

    public String getFieldValidationMessage(String fieldName) {
        try {
            WebElement message = driver.findElement(By.xpath(".//*[contains(@class, 'errors')]/ul/li[contains(text(), '" + fieldName + "')]"));
            return message.getText();
        }catch(Exception e){
            return null;
        }
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
        saveButton.click();
    }
}
