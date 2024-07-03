package frameworks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            System.out.println("Category found: " + text);
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

    public String getRandomCategory(ArrayList<String> categories) {
        if (categories.isEmpty()) {
            return null; // or handle this case appropriately
        }
        Random rand = new Random();
        return categories.get(rand.nextInt(categories.size()));
    }

    // Press "Create New Paste" button
    public void pressButtonByText(String text) {
        List<WebElement> buttons = webDriver.findElements(By.tagName("button"));

        for (WebElement button : buttons) {
            if (button.getText().equals(text)) {
                // Cast the WebDriver instance to JavascriptExecutor
                JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;

                // Execute JavaScript to click the button
                jsExecutor.executeScript("arguments[0].click();", button);
                break;
            }
        }
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

    public String captureTextByText(String text) {
        // List to store the text from divs or spans
        String capturedText = "";

        // Locate all div and span elements
        List<WebElement> elements = webDriver.findElements(By.cssSelector("div, span"));

        for (WebElement element : elements) {
            if (element.getText().contains(text)) {
                capturedText = element.getText();
                break;
            }
        }

        return capturedText;
    }

    public String getPostTitle() {
        List<WebElement> divElements = webDriver.findElements(By.tagName("div"));
        for (WebElement div : divElements) {
            if (div.getAttribute("class").contains("info-top")) {
                WebElement h1 = div.findElement(By.tagName("h1"));
                System.out.println(h1.getText());
                return h1.getText();
            }
        }
        return "";
    }

    public String getPostDescription() {
        List<WebElement> elements = webDriver.findElements(By.cssSelector("div, span"));

        String capturedText = "";

        for (WebElement element : elements) {
            if (element.getAttribute("class").contains("de1")) {
                capturedText = element.getText();
                break;
            }
        }
        System.out.println(capturedText);

        return capturedText;
    }
    
    public String getPostCategory() {
        // Lista todos os elementos span na página
        List<WebElement> spans = webDriver.findElements(By.tagName("span"));
    
        // Variável para armazenar o texto capturado
        String capturedText = "";
    
        // Itera sobre todos os spans
        for (WebElement span : spans) {
            // Verifica se o atributo title do span é "Category"
            if ("Category".equals(span.getAttribute("title"))) {
                // Captura o texto do span
                capturedText = span.getText().trim();
    
                // Quebra o loop quando o texto for encontrado
                break;
            }
        }
    
        // Processa o texto capturado para retornar a partir da primeira letra maiúscula
        for (int i = 0; i < capturedText.length(); i++) {
            if (Character.isUpperCase(capturedText.charAt(i))) {
                capturedText = capturedText.substring(i);
                break;
            }
        }
    
        // Imprime o texto capturado
        System.out.println(capturedText);
    
        // Retorna o texto capturado
        return capturedText;
    }
    

}