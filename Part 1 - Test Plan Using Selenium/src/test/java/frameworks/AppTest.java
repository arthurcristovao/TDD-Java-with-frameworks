package frameworks;

import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for Pastebin.
 */
public class AppTest {
    PastebinAutomation pastebinAutomation = null;

    public static String generateString() {
        byte[] randomBytes = new byte[128];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes).replace("=", "");
    }

    @BeforeEach
    public void setup() {
        pastebinAutomation = new PastebinAutomation();
        pastebinAutomation.useChrome();
    }

    @AfterEach
    public void cleanup() {
        if (pastebinAutomation != null) {
            pastebinAutomation.closeDriver();
        }
    }

    /**
     * 1. Teste Post Público: Verificar se é possível criar um novo post público sem
     * erros.
     * 2. Teste Post com Título: Verificar se é possível adicionar um título ao
     * post.
     * 3. Teste Post com Categoria: Verificar se é possível selecionar uma categoria
     * para o post.
     */
    @Test
    public void testePostPublico() {
        System.out.println(generateString());

        // Carrega pagina
        pastebinAutomation.accessURL("https://pastebin.com");
        pastebinAutomation.addDelay(5000);

        // Define titulo
        pastebinAutomation.setTitle(generateString().substring(0, 32));
        pastebinAutomation.addDelay(2000);

        // Loga campo titulo
        System.out.println("Title: " + pastebinAutomation.getTitle());
        pastebinAutomation.addDelay(2000);

        // Define descricao
        pastebinAutomation.setDescription("Essa é uma descrição! - " + generateString());
        pastebinAutomation.addDelay(2000);

        // Loga campo descricao
        System.out.println("Description: " + pastebinAutomation.getDescription());
        pastebinAutomation.addDelay(2000);

        // Abre dropdownlist categoria de post
        pastebinAutomation.openCategoryList();
        pastebinAutomation.addDelay(2000);

        // Define categoria Cybersecurity
        pastebinAutomation.setCategory("Cybersecurity");
        pastebinAutomation.addDelay(2000);

        pastebinAutomation.pressPasteButton();

        pastebinAutomation.addDelay(20000);
        System.out.println(pastebinAutomation.getUrl());

        // W.I.P.
        assertTrue(false);
    }
}
