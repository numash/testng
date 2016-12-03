package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by numash on 02.12.2016.
 */
public abstract class BasePage {
    private static final String baseUrl = "http://80.92.229.236:81/";

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }
    protected abstract String getRelativeUrl();

    protected String getFullUrl(){
        return baseUrl + getRelativeUrl();
    }

    //finds an element by xpath, clears it and fills it with value
    protected void clearAndFillFieldWithValue(String fieldXpath, String value){
        WebElement field = driver.findElement(By.xpath(fieldXpath));
        field.clear();
        field.sendKeys(value);
    }

    //finds an element by xpath and fills it with value
    protected void fillFieldWithValue(String fieldXpath, String value){
        WebElement field = driver.findElement(By.xpath(fieldXpath));
        field.sendKeys(value);
    }
}
