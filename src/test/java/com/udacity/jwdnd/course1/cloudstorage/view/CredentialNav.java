package com.udacity.jwdnd.course1.cloudstorage.view;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialNav {
    private final JavascriptExecutor js;
    private final WebDriver driver;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    @FindBy(id = "nav-credentials-tab")
    private WebElement tabCredentials;

    @FindBy(id = "btn-add-credential")
    private WebElement btnAddCredential;

    @FindBy(id = "btn-edit-credential")
    private WebElement btnEditCredential;

    @FindBy(id = "btn-delete-credential")
    private WebElement btnDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "credentials")
    private List<WebElement> credentials;

    public CredentialNav(WebDriver driver, CredentialService credentialService, EncryptionService encryptionService) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    public void addCredential(String url,
                              String username,
                              String password) {
        js.executeScript("arguments[0].click();", tabCredentials);
        js.executeScript("arguments[0].click();", btnAddCredential);
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", credentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public Boolean checkIfCredentialExists(String url) {
        js.executeScript("arguments[0].click();", tabCredentials);
        for (WebElement cred : credentials) {
            String noteTitle = cred.getAttribute("innerHTML");
            if (noteTitle.contains(url))
                return true;
        }
        return false;
    }

    public Boolean checkCredentialContent(String url, String username, String password) {
        Credential credential = credentialService.getByURL(url);
        if (credential != null && credential.getUsername() != null
                && credential.getUsername().equals(username)
                && credential.getPassword() != null
                && credential.getPassword().equals(encryptionService
                .encryptValue(password, credential.getKey())))
            return true;
        return false;
    }

    public void editCredential(String url, String newUrl, String newUsername,
                               String newPassword) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        js.executeScript("arguments[0].click();", tabCredentials);
        wait.until(webDriver -> webDriver.findElement(By.id("btn-delete-credential")));
        for (WebElement cred : credentials) {
            String noteRowHTML = cred.getAttribute("innerHTML");
            System.out.println(noteRowHTML);
            if (noteRowHTML.contains(url)) {
                WebElement btnEditItem =
                        cred.findElement(By.id("btn-edit-credential"));
                js.executeScript("arguments[0].click();", btnEditItem);
                js.executeScript("arguments[0].click();", tabCredentials);
                js.executeScript("arguments[0].value='" + newUrl + "';", credentialUrl);
                js.executeScript("arguments[0].value='" + newUsername + "';", credentialUsername);
                js.executeScript("arguments[0].value='" + newPassword + "';", credentialPassword);
                js.executeScript("arguments[0].click();", credentialSubmit);
                return;
            }
        }
    }

    public void deleteCredential(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        js.executeScript("arguments[0].click();", tabCredentials);
        wait.until(webDriver -> webDriver.findElement(By.id("btn-delete-credential")));
        for (WebElement cred : credentials) {
            String noteRowHTML = cred.getAttribute("innerHTML");
            System.out.println(noteRowHTML);
            if (noteRowHTML.contains(url)) {
                WebElement btnDelItem = cred.findElement(By.id("btn-delete-credential"));
                js.executeScript("arguments[0].click();", btnDelItem);
                return;
            }
        }
    }

}
