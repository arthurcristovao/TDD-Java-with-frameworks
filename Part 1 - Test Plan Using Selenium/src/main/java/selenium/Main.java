package selenium;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class Main {
    public static void main(String[] args) {
        AcessPasteBin acessPasteBin = new AcessPasteBin();

        // Use Chrome to access the site
        acessPasteBin.useChrome();
        acessPasteBin.acessURL("https://pastebin.com");
        acessPasteBin.addDelay(1);
//        
//        acessPasteBin.setTitle("Titulo Teste");
//        acessPasteBin.addDelay(1);
//        
//        System.out.println("Title: " + acessPasteBin.getTitle());
//        acessPasteBin.addDelay(1);
//        
//        
//        acessPasteBin.setDescription("Essa é uma descrição!");
//        acessPasteBin.addDelay(1);
//        System.out.println("Description: " + acessPasteBin.getDescription());
//        acessPasteBin.addDelay(2);
        
        
        ArrayList<String> categories = acessPasteBin.getCategory();
        
        for (String category : categories) {
            System.out.println("Category aqui: " + category);
        }
        acessPasteBin.addDelay(1);
        // Set a specific category
        //acessPasteBin.setCategory("Cybersecurity");
        //acessPasteBin.addDelay(1);
        acessPasteBin.closeAcessURL();
    }
}
