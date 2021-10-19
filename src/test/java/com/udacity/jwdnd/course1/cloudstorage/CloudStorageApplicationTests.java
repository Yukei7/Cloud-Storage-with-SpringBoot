package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.view.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	NoteService noteService;
	@Autowired
	CredentialService credentialService;
	@Autowired
	EncryptionService encryptionService;

	private String username = "testuser";
	private String password = "testpw";
	private WebDriver driver;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void accessHomeWithoutAuth() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		driver.get(baseURL + "/home");
		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupLoginAuth() {
		driver.get(baseURL + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 2);
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("testFirstname", "testLastName", "b", "b");

		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		// successfully signin
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("b", "b");

		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		// successfully login
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement logoutButton = driver.findElement(By.name("logout-btn"));
		logoutButton.click();

		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		// Test accessibility to home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Test
	public void addEditDeleteNoteAndVerifyDisplay() {
		String noteTitle = "test title";
		String noteDescription = "test description";

		WebDriverWait wait = new WebDriverWait(driver, 2);

		driver.get(baseURL);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(this.username, this.password);

		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));

		NoteNav noteNav = new NoteNav(driver, noteService);
		Assertions.assertEquals("Home", driver.getTitle());

		// add note
		ResultPage resultPage = new ResultPage(driver);
		noteNav.addNote(noteTitle, noteDescription);
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		// check display
		Assertions.assertTrue(noteNav.checkIfNoteExists(noteTitle));
		Assertions.assertTrue(noteNav.checkNoteDescription(noteTitle, noteDescription));

		// edit note
		noteNav.editNote(noteTitle, noteTitle + 1, noteDescription + 1);
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		Assertions.assertTrue(noteNav.checkIfNoteExists(noteTitle + 1));
		Assertions.assertTrue(noteNav.checkNoteDescription(noteTitle + 1, noteDescription + 1));

		// delete
		noteNav.deleteNote(noteTitle);
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		// check display
		Assertions.assertFalse(noteNav.checkIfNoteExists(noteTitle + 1));
		Assertions.assertFalse(noteNav.checkNoteDescription(noteTitle + 1, noteDescription + 1));
	}


	@Test
	public void addEditDeleteCredentialAndVerifyDisplay() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		String url = "google.com";
		String username = "testuser";
		String password = "testpw";

		driver.get(baseURL);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(this.username, this.password);
		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		Assertions.assertEquals("Home", driver.getTitle());

		// add
		CredentialNav credentialNav = new CredentialNav(driver, credentialService, encryptionService);
		credentialNav.addCredential(url, username, password);
		ResultPage resultPage = new ResultPage(driver);
		// check display
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		Assertions.assertTrue(credentialNav.checkIfCredentialExists(url));
		Assertions.assertTrue(credentialNav.checkCredentialContent(url, username, password));

		// edit
		credentialNav.editCredential(url, url + 1, username + 1, password + 1);
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		Assertions.assertTrue(credentialNav.checkIfCredentialExists(url + 1));
		Assertions.assertTrue(credentialNav.checkCredentialContent(url + 1, username + 1, password + 1));

		// delete
		credentialNav.deleteCredential(url + 1);
		Assertions.assertTrue(resultPage.checkSuccessMessage());
		resultPage.clickContinue();
		// check display
		Assertions.assertFalse(credentialNav.checkIfCredentialExists(url + 1));
		Assertions.assertFalse(credentialNav.checkCredentialContent(url + 1, username + 1, password + 1));
	}
}
