package com.udacity.jwdnd.course1.cloudstorage.view;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NoteNav {

    private final JavascriptExecutor js;
    private final WebDriver driver;
    private NoteService noteService;

    public NoteNav(WebDriver webDriver, NoteService noteService) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
        this.js = (JavascriptExecutor) webDriver;
        this.noteService = noteService;
    }

    @FindBy(id = "nav-notes-tab")
    private WebElement tabNotes;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "edit-note-btn")
    private WebElement editNoteBtn;

    @FindBy(id = "del-note-btn")
    private WebElement delNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(name = "logout-btn")
    private WebElement logoutBtn;

    @FindBy(id = "notes")
    private List<WebElement> notes;


    public void addNote(String title, String description) {
        js.executeScript("arguments[0].click();", tabNotes);
        js.executeScript("arguments[0].click();", addNoteBtn);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", noteSubmit);
    }

    public void logout() {
        js.executeScript("arguments[0].click()", logoutBtn);
    }

    public Boolean checkIfNoteExists(String title) {
        js.executeScript("arguments[0].click();", tabNotes);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(webDriver -> webDriver.findElement(By.id("add-note-btn")));

        for (WebElement note : notes) {
            String noteTitle = note.getAttribute("innerHTML");
            if (noteTitle.contains(title))
                return true;
        }
        return false;
    }

    public Boolean checkNoteDescription(String title, String description) {
        Note note = noteService.getByTitle(title);
        if (note != null
                && note.getNoteDescription() != null
                && note.getNoteDescription().equals(description))
            return true;
        return false;
    }

    public void editNote(String title, String newTitle, String newDescription) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        js.executeScript("arguments[0].click();", tabNotes);
        wait.until(webDriver -> webDriver.findElement(By.id("del-note-btn")));
        for (WebElement note : notes) {
            String noteRowHTML = note.getAttribute("innerHTML");
            if (noteRowHTML.contains(title)) {
                WebElement btnEditItem = note.findElement(By.id("edit-note-btn"));
                js.executeScript("arguments[0].click();", btnEditItem);
                js.executeScript("arguments[0].value='" + newTitle + "';", noteTitle);
                js.executeScript("arguments[0].value='" + newDescription + "';", noteDescription);
                js.executeScript("arguments[0].click();", noteSubmit);
                return;
            }
        }
    }

    public void deleteNote(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        js.executeScript("arguments[0].click();", tabNotes);
        wait.until(webDriver -> webDriver.findElement(By.id("del-note-btn")));

        for (WebElement note : notes) {
            String noteTitle = note.getAttribute("innerHTML");
            if (noteTitle.contains(title)) {
                WebElement btnDeleteNote = driver.findElement(By.id("del-note-btn"));
                js.executeScript("arguments[0].click()", btnDeleteNote);
                return;
            }
        }
    }

}
