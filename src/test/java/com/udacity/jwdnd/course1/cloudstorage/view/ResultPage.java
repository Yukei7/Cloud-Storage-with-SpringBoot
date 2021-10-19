package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    private final JavascriptExecutor js;
    private final WebDriver driver;

    @FindBy(tagName = "a")
    private WebElement aHere;

    @FindBy(tagName = "h1")
    private WebElement h1Success;

    @FindBy(tagName = "h5")
    private WebElement errorMessage;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public Boolean checkSuccessMessage() {
        return h1Success.getAttribute("innerHTML").contains("Success");
    }

    public void clickContinue() {
        js.executeScript("arguments[0].click();", aHere);
    }
}
