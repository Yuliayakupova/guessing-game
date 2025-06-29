package dev.yuliayakupova.guessinggame.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class GameFlowTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/yuliayakupova/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
    }

    private void enterGuess(String guess) {
        List<WebElement> guessInputs = driver.findElements(By.cssSelector("form input.nes-input"));
        for (int i = 0; i < 4; i++) {
            guessInputs.get(i).clear();
            guessInputs.get(i).sendKeys(String.valueOf(guess.charAt(i)));
        }
        WebElement guessSubmitButton = driver.findElement(By.cssSelector("form button.nes-btn.is-primary[type='submit']"));
        guessSubmitButton.click();
    }

    @Test
    public void fullGameFlowTest() {
        driver.get("http://localhost:3000");

        WebElement title = driver.findElement(By.className("title"));
        Assertions.assertTrue(title.getText().contains("Game Rules"));

        WebElement startButton = driver.findElement(By.cssSelector("button.nes-btn.is-primary"));
        startButton.click();

        WebElement nameInput = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name_input")));
        nameInput.sendKeys("TestPlayer3");

        WebElement submitButton = driver.findElement(By.cssSelector("button.nes-btn.is-primary"));
        submitButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> {
            String text = d.findElement(By.cssSelector("p.title")).getText();
            return text.contains("Enter your guess");
        });

        WebElement guessTitle = driver.findElement(By.cssSelector("p.title"));
        System.out.println("Guess screen title text: " + guessTitle.getText());
        Assertions.assertTrue(guessTitle.getText().contains("Enter your guess"));

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> d.findElements(By.cssSelector("form input.nes-input")).size() == 4);

        String[] guesses = {"1234", "5678", "9012", "3456", "7890", "2345", "6789", "0123", "4567", "8901"};

        boolean won = false;
        int maxAttempts = guesses.length;

        for (int i = 0; i < maxAttempts && !won; i++) {
            enterGuess(guesses[i]);

            WebElement result = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nes-list li")));
            String resultText = result.getText();
            System.out.println("Attempt " + (i + 1) + ": " + resultText);

            Assertions.assertTrue(resultText.matches(".*M:\\d+, P:\\d+"));

            if (resultText.contains("M:4")) {
                won = true;
            }
        }

        WebElement gameOverTitle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nes-container p.title")));
        Assertions.assertEquals("Game Over", gameOverTitle.getText());

        WebElement newGameBtn = driver.findElement(By.xpath("//button[contains(text(),'New Game')]"));
        WebElement leaderboardBtn = driver.findElement(By.xpath("//button[contains(text(),'Leaderboard')]"));
        WebElement exitBtn = driver.findElement(By.xpath("//button[contains(text(),'Exit')]"));

        Assertions.assertTrue(newGameBtn.isDisplayed());
        Assertions.assertTrue(leaderboardBtn.isDisplayed());
        Assertions.assertTrue(exitBtn.isDisplayed());

        newGameBtn.click();

        WebElement nameInputAfterRestart = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name_input")));
        Assertions.assertTrue(nameInputAfterRestart.isDisplayed());
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
