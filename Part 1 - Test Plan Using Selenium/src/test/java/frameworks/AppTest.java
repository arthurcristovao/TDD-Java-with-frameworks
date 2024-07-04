package frameworks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    private PastebinAutomation pastebinAutomation = null;
    private static String title;
    private static String description;
    private static String category;
    private static String postURL;

    // Método para gerar uma string aleatória
    public static String generateString() {
        byte[] randomBytes = new byte[128];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes).replace("=", "");
    }

    @BeforeEach
    public void setup() {
        // Configuração inicial antes de cada teste
        pastebinAutomation = new PastebinAutomation();
        pastebinAutomation.useChrome();
    }

    @AfterEach
    public void cleanup() {
        // Limpeza após cada teste
        if (pastebinAutomation != null) {
            pastebinAutomation.closeDriver();
        }
    }

    // Método auxiliar para criar um post
    private void createPost(boolean burnAfterRead) {
        // Gera título e descrição
        title = generateString().substring(0, 32);
        description = "Essa é uma descrição! - " + generateString();

        // Acessa a página do Pastebin
        pastebinAutomation.accessURL("https://pastebin.com");
        pastebinAutomation.addDelay(5000);

        // Define e verifica o título
        setTitleAndVerify();

        // Define e verifica a descrição
        setDescriptionAndVerify();

        // Abre a lista de categorias e seleciona uma categoria aleatória
        openCategoryListAndSetRandom();
        pastebinAutomation.addDelay(2000);

        // Se burnAfterRead for verdadeiro, ativa a opção "Burn After Read"
        if (burnAfterRead) {
            pastebinAutomation.setBurnAfterRead(true);
            pastebinAutomation.addDelay(5000);
        }

        // Pressiona o botão de paste
        pastebinAutomation.pressPasteButton();
        pastebinAutomation.addDelay(7000);

        // Recupera a URL do post
        postURL = pastebinAutomation.getUrl();
        assertNotNull(postURL, "A URL do post não deve ser nula");
    }

    // Método auxiliar para definir e verificar o título
    private void setTitleAndVerify() {
        // Define o título do post
        pastebinAutomation.setTitle(title);
        pastebinAutomation.addDelay(2000);

        // Verifica se o título foi definido corretamente
        String retrievedTitle = pastebinAutomation.getTitle();
        assertNotNull(retrievedTitle, "O título não deve ser nulo");
        assertEquals(title, retrievedTitle, "O título não corresponde ao esperado");
    }

    // Método auxiliar para definir e verificar a descrição
    private void setDescriptionAndVerify() {
        // Define a descrição do post
        pastebinAutomation.setDescription(description);
        pastebinAutomation.addDelay(2000);

        // Verifica se a descrição foi definida corretamente
        String retrievedDescription = pastebinAutomation.getDescription();
        assertNotNull(retrievedDescription, "A descrição não deve ser nula");
        assertEquals(description, retrievedDescription, "A descrição não corresponde ao esperado");
    }

    // Método auxiliar para abrir a lista de categorias e definir uma categoria aleatória
    private void openCategoryListAndSetRandom() {
        // Abre a lista de categorias
        ArrayList<String> categories = pastebinAutomation.openCategoryList();
        pastebinAutomation.addDelay(2000);

        // Seleciona uma categoria aleatória e a define
        category = pastebinAutomation.getRandomCategory(categories);
        pastebinAutomation.setCategory(category);
        pastebinAutomation.addDelay(2000);

        // Verifica se a categoria foi selecionada corretamente
        assertNotNull(categories, "A lista de categorias não deve ser nula");
        assertTrue(categories.contains(category), "A categoria selecionada não está na lista de categorias");
    }

    // Método auxiliar para verificar os dados do post
    private void verifyPost(String expectedTitle, String expectedDescription, String expectedCategory, String expectedURL) {
        // Recupera os dados do post
        String retrievedTitle = pastebinAutomation.getPostTitle();
        String retrievedDescription = pastebinAutomation.getPostDescription();
        String retrievedCategory = pastebinAutomation.getPostCategory();
        String retrievedURL = pastebinAutomation.getUrl();

        // Verifica se os dados recuperados não são nulos
        assertNotNull(retrievedTitle, "O título do post não deve ser nulo");
        assertNotNull(retrievedDescription, "A descrição do post não deve ser nula");
        assertNotNull(retrievedCategory, "A categoria do post não deve ser nula");
        assertNotNull(retrievedURL, "A URL do post não deve ser nula");

        // Verifica se os dados recuperados correspondem aos valores esperados
        assertEquals(expectedTitle, retrievedTitle, "O título do post não corresponde ao esperado");
        assertEquals(expectedDescription, retrievedDescription, "A descrição do post não corresponde ao esperado");
        assertEquals(expectedCategory, retrievedCategory, "A categoria do post não corresponde ao esperado");
        assertEquals(expectedURL, retrievedURL, "A URL do post não corresponde ao esperado");
    }

    @Test
    @Order(1)
    public void testeCriaPost() {
        // Teste para criar um post sem Burn After Read
        createPost(false);
    }

    @Test
    @Order(2)
    public void testeVerificaPostPublicado() {
        // Teste para verificar se o post foi publicado corretamente
        assertNotNull(postURL, "A URL do post não deve ser nula");
        pastebinAutomation.accessURL(postURL);
        pastebinAutomation.addDelay(5000);
        verifyPost(title, description, category, postURL);
    }

    @Test
    @Order(3)
    public void testeCriaPostBurnAfterRead() {
        // Teste para criar um post com Burn After Read
        createPost(true);
    }

    @Test
    @Order(4)
    public void testeVerificaPostBurnAfterRead() {
        // Teste para verificar se o post com Burn After Read foi publicado corretamente
        assertNotNull(postURL, "A URL do post não deve ser nula");
        pastebinAutomation.accessURL(postURL);
        pastebinAutomation.addDelay(5000);
        pastebinAutomation.pressShowPasteBurnAfterRead();
        pastebinAutomation.addDelay(5000);
        verifyPost(title, description, category, postURL);
        postURL = pastebinAutomation.getUrl();
        pastebinAutomation.addDelay(2000);
        assertNotNull(postURL, "A URL do post não deve ser nula");
    }

    @Test
    @Order(5)
    public void testeVerificaPostExpirado() {
        // Teste para verificar se o post expirou
        assertNotNull(postURL, "A URL do post não deve ser nula");
        pastebinAutomation.accessURL(postURL);
        pastebinAutomation.addDelay(5000);

        // Verifica se a página indica que o post expirou
        boolean isPageBurned = pastebinAutomation.verifyIfPagesBurn();
        assertTrue(isPageBurned, "A página não indica que o post expirou como esperado");
    }
}
