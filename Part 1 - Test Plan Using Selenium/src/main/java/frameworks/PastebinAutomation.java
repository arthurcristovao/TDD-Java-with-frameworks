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

        WebElement dropdown = webDriver.findElement(By.cssSelector("span.select2-selection--single"));
        dropdown.click();

        addDelay(2000);

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

                JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;

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
        List<WebElement> spans = webDriver.findElements(By.tagName("span"));
        String capturedText = "";

        for (WebElement span : spans) {
            if ("Category".equals(span.getAttribute("title"))) {
                capturedText = span.getText().trim();
                break;
            }
        }

        for (int i = 0; i < capturedText.length(); i++) {
            if (Character.isUpperCase(capturedText.charAt(i))) {
                capturedText = capturedText.substring(i);
                break;
            }
        }
        System.out.println(capturedText);
        return capturedText;
    }
    
    public void setBurnAfterRead(boolean valor) {
        List<WebElement> labels = webDriver.findElements(By.cssSelector("label"));
    
        for (WebElement label : labels) {
            if (label.getText().contains("Burn after read")) {

                ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", label);

                WebElement span = label.findElement(By.tagName("span"));

                if (span != null) {
                    if (valor) {
                        span.click();
                        System.out.println("Checkbox 'Burn after read' marcado.");
                    } else {
                        System.out.println("Checkbox 'Burn after read' não marcado, valor = false.");
                    }
                }
                return;
            }
        }
    
        // Caso a label não seja encontrada
        System.out.println("Label 'Burn after read' não encontrada.");
    }

    public void pressShowPasteBurnAfterRead(){
        pressButtonByText("Ok, show me the paste");
    }
    
    public boolean verifyIfPagesBurn() {
        // Usar JavaScriptExecutor para procurar a div com a classe 'content__title'
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement div = (WebElement) js.executeScript(
            "return document.querySelector('div.content__title');"
        );

        // Verificar se a div foi encontrada e se contém o texto '404'
        if (div != null) {
            String divText = div.getText();
            return divText.contains("404");
        }

        // Retornar falso se a div não for encontrada ou não contém '404'
        return false;
    }
}