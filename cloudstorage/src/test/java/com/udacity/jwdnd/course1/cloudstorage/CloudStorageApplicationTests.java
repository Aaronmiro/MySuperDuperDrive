package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-link")));
        WebElement buttonLogin = driver.findElement(By.id("login-link"));
        buttonLogin.click();
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        ;
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Bad", "UB", "123");
        doLogIn("UB", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
    }

    @Test
    public void unauthorizedAccessRestriction() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/result");
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/error");
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertEquals("Login", driver.getTitle());

        doMockSignUp("Unauthorized", "Access", "UA", "123");
        doLogIn("UA", "123");
        Assertions.assertEquals("Home", driver.getTitle());
        logout();
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    private void logout() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        WebElement loginButton = driver.findElement(By.id("logout-button"));
        loginButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }

    private void successResultToHome() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toHome")));
        WebElement toHomeHref = driver.findElement(By.id("toHome"));
        toHomeHref.click();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    @Test
    public void notesTest() {
        doMockSignUp("Notes", "Test", "NT", "123");
        doLogIn("NT", "123");
        Assertions.assertEquals("Home", driver.getTitle());

        String title = "title1";
        String description = "description1";
        doAddOrEditNote("add", title, description);
        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());
        Map<String, String> titleAndDescription = getTitleAndDescription();
        Assertions.assertEquals(title, titleAndDescription.get("title"));
        Assertions.assertEquals(description, titleAndDescription.get("description"));

        String newTitle = "title2";
        String newDescription = "description2";
        doAddOrEditNote("edit", newTitle, newDescription);

        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());
        Map<String, String> newTitleAndDescription = getTitleAndDescription();
        Assertions.assertEquals(newTitle, newTitleAndDescription.get("title"));
        Assertions.assertEquals(newDescription, newTitleAndDescription.get("description"));

        deleteNote();
        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());

        Map<String, String> emptyTitleAndDescription = getTitleAndDescription();
        Assertions.assertTrue(emptyTitleAndDescription.isEmpty());
    }

    private void deleteNote() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
        navNotesTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNote-button")));
        WebElement deleteNoteButton = driver.findElement(By.id("deleteNote-button"));
        deleteNoteButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    private Map<String, String> getTitleAndDescription() {
        Map<String, String> result = new HashMap<>();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
        navNotesTab.click();

        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("oneNoteTitle")));
            WebElement oneNoteTitle = driver.findElement(By.id("oneNoteTitle"));
            result.put("title", oneNoteTitle.getText());

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("oneNoteDescription")));
            WebElement oneNoteDescription = driver.findElement(By.id("oneNoteDescription"));
            result.put("description", oneNoteDescription.getText());
            return result;
        } catch (Exception exception) {
            return result;
        }
    }

    private void doAddOrEditNote(String type, String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
        navNotesTab.click();

        if (type.equals("edit")) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNote-button")));
            WebElement editNoteButton = driver.findElement(By.id("editNote-button"));
            editNoteButton.click();
        } else {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNote-button")));
            WebElement addNoteButton = driver.findElement(By.id("addNote-button"));
            addNoteButton.click();
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        if (type.equals("edit")) noteTitle.clear();
        noteTitle.sendKeys(title);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        if (type.equals("edit")) noteDescription.clear();
        noteDescription.sendKeys(description);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSaveChanges")));
        WebElement noteSaveChanges = driver.findElement(By.id("noteSaveChanges"));
        noteSaveChanges.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    @Test
    public void credentialsTest() {
        doMockSignUp("Credentials", "Test", "CT", "123");
        doLogIn("CT", "123");
        Assertions.assertEquals("Home", driver.getTitle());

        String url1 = "www.aaronmiro.com";
        String username1 = "aaronmiro";
        String password1 = "123456";
        doAddCredential(url1, username1, password1);
        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());

        String url2 = "www.baidu.com";
        String username2 = "aaron";
        String password2 = "654321";
        doAddCredential(url2, username2, password2);
        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());

        String url3 = "www.google.com";
        String username3 = "Jack";
        String password3 = "111111";
        doAddCredential(url3, username3, password3);
        Assertions.assertEquals("Result", driver.getTitle());
        successResultToHome();
        Assertions.assertEquals("Home", driver.getTitle());
    }

    private void doAddCredential(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement navCreTab = driver.findElement(By.id("nav-credentials-tab"));
        navCreTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential-button")));
        WebElement addCreButton = driver.findElement(By.id("addCredential-button"));
        addCreButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement creUrl = driver.findElement(By.id("credential-url"));
        creUrl.click();
        creUrl.sendKeys(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement creUsername = driver.findElement(By.id("credential-username"));
        creUsername.click();
        creUsername.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement crePassword = driver.findElement(By.id("credential-password"));
        crePassword.click();
        crePassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("creSaveChanges")));
        WebElement creSaveChanges = driver.findElement(By.id("creSaveChanges"));
        creSaveChanges.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }
}
