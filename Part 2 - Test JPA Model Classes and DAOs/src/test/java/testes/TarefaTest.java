package testes;

import models.Pessoa;
import models.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TarefaTest {

    private Tarefa tarefa;
    private Pessoa pessoa;

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

    // Work in progress
}