package testes;

import models.Pessoa;
import org.junit.jupiter.api.*;

import dao.PessoaDAO;
import dao.TarefaDAO;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaDAOTest {
    private PessoaDAO pessoaDAO;

    @BeforeEach
    public void setUp() {
        pessoaDAO = new PessoaDAO();
    }

    @AfterEach
    public void tearDown() {
        pessoaDAO = null;
    }

    @Test
    public void testInsert() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoaDAO.insert(pessoa);
        assertNotNull(pessoa.getId());
    }

    @Test
    public void testDelete() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoaDAO.insert(pessoa);

        int id = pessoa.getId();
        pessoaDAO.delete(id);

        Pessoa deletedPessoa = pessoaDAO.get(id);
        assertNull(deletedPessoa);
    }

    @Test
    public void testUpdate() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoaDAO.insert(pessoa);

        pessoa.setNome("Teste Atualizado");
        pessoaDAO.update(pessoa);

        Pessoa updatedPessoa = pessoaDAO.get(pessoa.getId());
        assertEquals("Teste Atualizado", updatedPessoa.getNome());
    }

    @Test
    public void testList() {
        List<Pessoa> pessoas = pessoaDAO.list(10, 0);
        assertNotNull(pessoas);
    }

    @Test
    public void testGet() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoaDAO.insert(pessoa);

        Pessoa fetchedPessoa = pessoaDAO.get(pessoa.getId());
        assertNotNull(fetchedPessoa);
    }

    @Test
    public void testListByName() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Nome");
        pessoaDAO.insert(pessoa);

        List<Pessoa> pessoas = pessoaDAO.listByName("Teste Nome");
        assertFalse(pessoas.isEmpty());
    }
}
