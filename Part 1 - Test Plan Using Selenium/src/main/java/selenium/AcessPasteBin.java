package selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AcessPasteBin {
    
    private WebDriver driver;

    public void useChrome() {
        driver = new ChromeDriver();
    }

    public void acessURL(String url) {
            driver.get(url);
    }

    public void closeAcessURL() {
            driver.quit();
    }

    public void setTitle(String text) {
        WebElement description = driver.findElement(By.id("postform-name"));
        description.sendKeys(text);
    }

	public String getTitle() {
	        WebElement description = driver.findElement(By.id("postform-name"));
	        return description.getAttribute("value");
	}

    public void setDescription(String text) {
            WebElement description = driver.findElement(By.id("postform-text"));
            description.sendKeys(text);
    }

    public String getDescription() {
            WebElement description = driver.findElement(By.id("postform-text"));
            return description.getAttribute("value");
    }
    
    public ArrayList<String> getCategory() {
        ArrayList<String> categories = new ArrayList<>();
        
        // Open the dropdown to make the categories visible
        WebElement dropdown = driver.findElement(By.cssSelector("span.select2-selection--single"));
        dropdown.click();
        
        // Add a short delay to ensure the dropdown is open
        addDelay(2);
        
        // Find all category elements
        List<WebElement> categoryElements = driver.findElements(By.cssSelector("ul.select2-results__options li.select2-results__option"));
        
        System.out.println("Number of categories found: " + categoryElements.size());

        for (WebElement element : categoryElements) {
            String text = element.getText();
            //System.out.println("Category found: " + text);
            categories.add(text);
        }
        
        return categories;
    }



    // Set category based on the provided name
    public void setCategory(String categoryName) {
        List<WebElement> categoryElements = driver.findElements(By.cssSelector(".select2-results__options .select2-results__option"));
        
        for (WebElement element : categoryElements) {
            if (element.getText().equals(categoryName)) {
                element.click();
                break;
            }
        }
    }
    

    
    public void addDelay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Delay was interrupted");
        }
    }
}
