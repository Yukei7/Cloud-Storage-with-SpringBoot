package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final JavascriptExecutor js;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(name = "login-btn")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void login(String username, String password) {
        js.executeScript("arguments[0].value='" + username + "';", usernameField);
        js.executeScript("arguments[0].value='" + password + "';", passwordField);
        js.executeScript("arguments[0].click();", loginButton);
    }
}
