package testes;

import models.Pessoa;
import models.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TarefaTest {

    private Tarefa tarefa;
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
        tarefa = new Tarefa();
    }

    @Test
    public void testTituloNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tarefa.setTitulo(null));
    }

    @Test
    public void testTituloBlankThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tarefa.setTitulo(""));
    }

    @Test
    public void testPessoaNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tarefa.setPessoa(null));
    }
}