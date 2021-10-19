package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private final JavascriptExecutor js;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.js = (JavascriptExecutor) webDriver;
    }

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public void signup(String firstName, String lastName, String username, String password) {
        js.executeScript("arguments[0].value='" + firstName + "';", firstNameField);
        js.executeScript("arguments[0].value='" + lastName + "';", lastNameField);
        js.executeScript("arguments[0].value='" + username + "';", usernameField);
        js.executeScript("arguments[0].value='" + password + "';", passwordField);
        js.executeScript("arguments[0].click();", submitButton);
    }

}
