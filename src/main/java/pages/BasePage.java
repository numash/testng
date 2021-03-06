package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by numash on 02.12.2016.
 */
public abstract class BasePage {
    private static final String baseUrl = "http://80.92.229.236:81/";

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected abstract String getRelativeUrl();

    //finds an element by xpath, clears it and fills it with value
    protected void clearAndFillFieldWithValue(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    //finds an element by xpath and fills it with value
    protected void fillFieldWithValue(WebElement element, String value){
        element.sendKeys(value);
    }

    //finds an element by xpath, clears it and fills it with value
    protected void clearField(WebElement element){
        element.clear();
    }

    public String getFullUrl(){
        return baseUrl + getRelativeUrl();
    }
}
