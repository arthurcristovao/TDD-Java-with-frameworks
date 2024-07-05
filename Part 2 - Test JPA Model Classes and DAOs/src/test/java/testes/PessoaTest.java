package testes;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import models.Pessoa;
import models.Tarefa;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaTest {

    private Pessoa pessoa;

    // no lifecycle do maven de test já faz, mas se define aqui também
    // se for rodar pelo junit
    @BeforeAll
    public void defineAsRunningTest() {
        System.setProperty("runningTests", "true");
    }

    @BeforeEach
    public void setUp() {
        pessoa = new Pessoa();
    }

    @Test
    public void testSetNome() {
        pessoa.setNome("João");
        assertEquals("João", pessoa.getNome());
    }

    @Test
    public void testSetNomeNull() {
        String nome = null;
        assertThrows(IllegalArgumentException.class, () -> pessoa.setNome(nome));
    }

    @Test
    public void testSetNomeBlank() {
        assertThrows(IllegalArgumentException.class, () -> pessoa.setNome(""));
    }

    @Test
    public void testGetTarefas() {
        for (int i = 0; i < 5; i++) {
            Tarefa tarefaMock = mock(Tarefa.class);
            pessoa.addTarefa(tarefaMock);
        }

        Assertions.assertAll(
                () -> assertNotEquals(null, pessoa.getTarefas()),
                () -> assertEquals(5, pessoa.getTarefas().size()));
    }

}
