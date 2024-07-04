package frameworks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class PastebinAutomation {

    private WebDriver webDriver;
    
    // Configura o WebDriver para tela cheia
    private void configureWebDriver() {
        webDriver.manage().window().fullscreen();
    }

    // Inicializa o WebDriver para usar o Chrome
    public void useChrome() {
        webDriver = new ChromeDriver();
        configureWebDriver();
    }

    // Acessa a URL especificada
    public void accessURL(String url) {
        webDriver.get(url);
    }

    // Obtém a URL atual
    public String getUrl() {
        return webDriver.getCurrentUrl();
    }

    // Fecha o WebDriver
    public void closeDriver() {
        webDriver.quit();
    }

    // Define o título do post
    public void setTitle(String text) {
        WebElement description = webDriver.findElement(By.id("postform-name"));
        description.sendKeys(text);
    }

    // Obtém o título do post
    public String getTitle() {
        WebElement description = webDriver.findElement(By.id("postform-name"));
        return description.getAttribute("value");
    }

    // Define a descrição do post
    public void setDescription(String text) {
        WebElement description = webDriver.findElement(By.id("postform-text"));
        description.sendKeys(text);
    }

    // Obtém a descrição do post
    public String getDescription() {
        WebElement description = webDriver.findElement(By.id("postform-text"));
        return description.getAttribute("value");
    }

    // Abre a lista de categorias e retorna as categorias disponíveis
    public ArrayList<String> openCategoryList() {
        ArrayList<String> categories = new ArrayList<>();

        WebElement dropdown = webDriver.findElement(By.cssSelector("span.select2-selection--single"));
        dropdown.click();

        addDelay(2000);

        List<WebElement> categoryElements = webDriver
                .findElements(By.cssSelector("ul.select2-results__options li.select2-results__option"));

        //System.out.println("Number of categories found: " + categoryElements.size());

        for (WebElement element : categoryElements) {
            String text = element.getText();
            //System.out.println("Category found: " + text);
            categories.add(text);
        }

        return categories;
    }

    // Define a categoria do post baseada no nome fornecido
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

    // Retorna uma categoria aleatória da lista fornecida
    public String getRandomCategory(ArrayList<String> categories) {
        if (categories.isEmpty()) {
            return null; 
        }
        Random rand = new Random();
        return categories.get(rand.nextInt(categories.size()));
    }

    // Pressiona um botão baseado no texto fornecido
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

    // Pressiona o botão "Create New Paste"
    public void pressPasteButton() {
        pressButtonByText("Create New Paste");
    }

    // Adiciona um atraso de tempo em milissegundos
    public void addDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Delay foi interrompido");
        }
    }

    // Obtém o título do post publicado
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

    // Obtém a descrição do post publicado
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
    
    // Obtém a categoria do post publicado
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
    
    // Define a opção "Burn After Read" do post
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
        System.out.println("Label 'Burn after read' não encontrada.");
    }

    // Pressiona o botão "Ok, show me the paste" para posts com "Burn After Read"
    public void pressShowPasteBurnAfterRead(){
        pressButtonByText("Ok, show me the paste");
    }
    
    // Verifica se a página indica que o post expirou
    public boolean verifyIfPagesBurn() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement div = (WebElement) js.executeScript(
            "return document.querySelector('div.content__title');"
        );

        if (div != null) {
            String divText = div.getText();
            return divText.contains("404");
        }

        return false;
    }
}
