package frameworks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

/**
 * Unit test for Pastebin.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    PastebinAutomation pastebinAutomation = null;
    private String title = "8bzdGtu5VH5WEWy32AzgdMEGMOxxhqCz";
    private String description = "Essa é uma descrição! - rYQ_Z14s3esUYRUUSOuoODLEUtArMHvsvtuPCT-gFPxbI8aNYIA3AEMHm3ClatlpUXJthkwn8D5hzg8ofs4dNhY55FF3ZmqlsqDNQjCJkISUeG9kRYSHk_jVfDQECGG3j53SCP8qWvkpglIH9elLZG8uh9JasjEkwh6Qf94ts9E";
    private String category = "Source Code";
    private static String postURL = "https://pastebin.com/BmyngiGf";

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
    @Order(1)
    public void testeCriaPost() {
        // Gera título e descrição
        this.title = generateString().substring(0, 32);
        this.description = generateString();

        // Carrega a página
        pastebinAutomation.accessURL("https://pastebin.com");
        pastebinAutomation.addDelay(5000);

        // Define título
        pastebinAutomation.setTitle(title);
        pastebinAutomation.addDelay(2000);

        // Loga e verifica o campo título
        String retrievedTitle = pastebinAutomation.getTitle();
        System.out.println("Title: " + retrievedTitle);
        assertNotNull(retrievedTitle, "O título não deve ser nulo");
        assertEquals(title, retrievedTitle, "O título não corresponde ao esperado");
        pastebinAutomation.addDelay(2000);

        // Define descrição
        String fullDescription = "Essa é uma descrição! - " + description;
        pastebinAutomation.setDescription(fullDescription);
        pastebinAutomation.addDelay(2000);

        // Loga e verifica o campo descrição
        String retrievedDescription = pastebinAutomation.getDescription();
        System.out.println("Description: " + retrievedDescription);
        assertNotNull(retrievedDescription, "A descrição não deve ser nula");
        assertEquals(fullDescription, retrievedDescription, "A descrição não corresponde ao esperado");
        pastebinAutomation.addDelay(2000);

        // Abre dropdown list de categoria de post
        ArrayList<String> categories = pastebinAutomation.openCategoryList();
        pastebinAutomation.addDelay(2000);

        // Seleciona uma categoria aleatória
        this.category = pastebinAutomation.getRandomCategory(categories);
        System.out.println("Category: " + category);

        // Define categoria
        pastebinAutomation.setCategory(category);
        pastebinAutomation.addDelay(2000);

        // Verifica a categoria
        // String retrievedCategory = pastebinAutomation.getCategory();
        // assertNotNull(retrievedCategory, "A categoria não deve ser nula");
        // assertEquals(category, retrievedCategory, "A categoria não corresponde ao esperado");

        // Pressiona o botão de paste
        pastebinAutomation.pressPasteButton();
        pastebinAutomation.addDelay(20000);

        // Recupera a URL do post
        postURL = pastebinAutomation.getUrl();
        assertNotNull(postURL, "A URL do post não deve ser nula");
    }

    @Test
    @Order(2)
    public void testeVerificaPostPublicado(){
        // Acessa a URL do post
        pastebinAutomation.accessURL(postURL);
        pastebinAutomation.addDelay(5000);

        // Recupera os valores do post
        String retrievedTitle = pastebinAutomation.getPostTitle();
        String retrievedDescription = pastebinAutomation.getPostDescription();
        String retrievedCategory = pastebinAutomation.getPostCategory();
        String retrievedURL = pastebinAutomation.getUrl();

        // Verifica se os valores recuperados não são nulos
        assertNotNull(retrievedTitle, "O título do post não deve ser nulo");
        assertNotNull(retrievedDescription, "A descrição do post não deve ser nula");
        assertNotNull(retrievedCategory, "A categoria do post não deve ser nula");
        assertNotNull(retrievedURL, "A URL do post não deve ser nula");

        // Verifica se os dados recuperados são os mesmos que foram enviados
        assertEquals(title, retrievedTitle, "O título do post não corresponde ao esperado");
        assertEquals(description, retrievedDescription, "A descrição do post não corresponde ao esperado");
        assertEquals(category, retrievedCategory, "A categoria do post não corresponde ao esperado");
        assertEquals(postURL, retrievedURL, "A URL do post não corresponde ao esperado");
    }
}
