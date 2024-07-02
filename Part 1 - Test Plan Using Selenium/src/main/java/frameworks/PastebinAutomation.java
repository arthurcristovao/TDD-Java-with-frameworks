package frameworks;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class PastebinAutomation {

    private WebDriver webDriver;

    private void configureWebDriver() {
        webDriver.manage().window().fullscreen();
    }

    public void useChrome() {
        webDriver = new ChromeDriver();
        configureWebDriver();
    }

    public void accessURL(String url) {
        webDriver.get(url);
    }

    public String getUrl() {
        return webDriver.getCurrentUrl();
    }

    public void closeDriver() {
        webDriver.quit();
    }

    public void setTitle(String text) {
        WebElement description = webDriver.findElement(By.id("postform-name"));
        description.sendKeys(text);
    }

    public String getTitle() {
        WebElement description = webDriver.findElement(By.id("postform-name"));
        return description.getAttribute("value");
    }

    public void setDescription(String text) {
        WebElement description = webDriver.findElement(By.id("postform-text"));
        description.sendKeys(text);
    }

    public String getDescription() {
        WebElement description = webDriver.findElement(By.id("postform-text"));
        return description.getAttribute("value");
    }

    public ArrayList<String> openCategoryList() {
        ArrayList<String> categories = new ArrayList<>();

        // Open the dropdown to make the categories visible
        WebElement dropdown = webDriver.findElement(By.cssSelector("span.select2-selection--single"));
        dropdown.click();

        // Add a short delay to ensure the dropdown is open
        addDelay(2000);

        // Find all category elements
        List<WebElement> categoryElements = webDriver
                .findElements(By.cssSelector("ul.select2-results__options li.select2-results__option"));

        System.out.println("Number of categories found: " + categoryElements.size());

        for (WebElement element : categoryElements) {
            String text = element.getText();
            // System.out.println("Category found: " + text);
            categories.add(text);
        }

        return categories;
    }

    // Set category based on the provided name
    public void setCategory(String categoryName) {
        List<WebElement> categoryElements = webDriver
                .findElements(By.cssSelector(".select2-results__options .select2-results__option"));

        for (WebElement element : categoryElements) {
            if (element.getText().equals(categoryName)) {
                element.click();
                break;
            }
        }
    }

    // Press button based on it's innerText
    public void pressButtonByText(String text) {
        WebElement createNewPasteButton = webDriver
                .findElement(By.xpath(String.format("//button[text()='%s']", text)));
        createNewPasteButton.click();
    }

    // Press "Create New Paste" button
    public void pressPasteButton() {
        pressButtonByText("Create New Paste");
    }

    public void addDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Delay was interrupted");
        }
    }
}
