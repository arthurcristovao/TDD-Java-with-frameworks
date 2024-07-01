package testes;

import models.Pessoa;
import models.Tarefa;
import org.junit.jupiter.api.*;

import dao.*;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TarefaDAOTest {
    private TarefaDAO tarefaDAO;
    private PessoaDAO pessoaDAO;

    @BeforeEach
    public void setUp() {
        pessoaDAO = new PessoaDAO();
        tarefaDAO = new TarefaDAO();
    }

    @AfterEach
    public void tearDown() {
        pessoaDAO = null;
        tarefaDAO = null;
    }

    @Test
    public void testInsert() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoaDAO.insert(pessoa);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição Tarefa");
        tarefa.setData(new Date(System.currentTimeMillis()));
        tarefa.setPessoa(pessoa);
        tarefaDAO.insert(tarefa);

        assertNotNull(tarefa.getId());
    }

    @Test
    public void testDelete() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoaDAO.insert(pessoa);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição Tarefa");
        tarefa.setData(new Date(System.currentTimeMillis()));
        tarefa.setPessoa(pessoa);
        tarefaDAO.insert(tarefa);

        int id = tarefa.getId();
        tarefaDAO.delete(id);

        Tarefa deletedTarefa = tarefaDAO.get(id);
        assertNull(deletedTarefa);
    }

    @Test
    public void testUpdate() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoaDAO.insert(pessoa);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição Tarefa");
        tarefa.setData(new Date(System.currentTimeMillis()));
        tarefa.setPessoa(pessoa);
        tarefaDAO.insert(tarefa);

        tarefa.setTitulo("Teste Tarefa Atualizada");
        tarefaDAO.update(tarefa);

        Tarefa updatedTarefa = tarefaDAO.get(tarefa.getId());
        assertEquals("Teste Tarefa Atualizada", updatedTarefa.getTitulo());
    }

    @Test
    public void testList() {
        List<Tarefa> tarefas = tarefaDAO.list(10, 0);
        assertNotNull(tarefas);
    }

    @Test
    public void testGet() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoaDAO.insert(pessoa);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição Tarefa");
        tarefa.setData(new Date(System.currentTimeMillis()));
        tarefa.setPessoa(pessoa);
        tarefaDAO.insert(tarefa);

        Tarefa fetchedTarefa = tarefaDAO.get(tarefa.getId());
        assertNotNull(fetchedTarefa);
    }

    @Test
    public void testListByPessoaId() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoaDAO.insert(pessoa);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição Tarefa");
        tarefa.setData(new Date(System.currentTimeMillis()));
        tarefa.setPessoa(pessoa);
        tarefaDAO.insert(tarefa);

        List<Tarefa> tarefas = tarefaDAO.listByPessoaId(pessoa.getId());
        assertFalse(tarefas.isEmpty());
    }
}
