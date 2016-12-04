package pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by numash on 03.12.2016.
 */
public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    //initialize a new instance and open page
    public static SearchPage openSearchPage(WebDriver driver){
        SearchPage page = new SearchPage(driver);
        page.openSearchPage();

        return page;
    }

    protected String getRelativeUrl() {
        return null;
    }

    private void openSearchPage(){
        driver.get(getFullUrl());
    }
}
