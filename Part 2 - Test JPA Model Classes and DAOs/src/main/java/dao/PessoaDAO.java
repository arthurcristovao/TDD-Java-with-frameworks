package dao;

import models.Pessoa;
import models.Tarefa;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class PessoaDAO implements DAO<Pessoa> {
    private EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public void insert(Pessoa pessoa) {
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        em.getTransaction().begin();
        Pessoa pessoa = em.find(Pessoa.class, id);
        if (pessoa != null) {
            List<Tarefa> tarefas = em.createQuery("SELECT t FROM Tarefa t WHERE t.pessoa.id = :pessoaId", Tarefa.class)
                                     .setParameter("pessoaId", id)
                                     .getResultList();
            for (Tarefa tarefa : tarefas) {
                em.remove(tarefa);
            }
            em.remove(pessoa);
        }
        em.getTransaction().commit();
    }

    @Override
    public void update(Pessoa pessoa) {
        em.getTransaction().begin();
        em.merge(pessoa);
        em.getTransaction().commit();
    }

    @Override
    public List<Pessoa> list(int limit, int offset) {
        return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class)
                 .setFirstResult(offset)
                 .setMaxResults(limit)
                 .getResultList();
    }

    @Override
    public Pessoa get(int id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> listByName(String name) {
        return em.createQuery("SELECT p FROM Pessoa p WHERE p.nome LIKE :nome", Pessoa.class)
                 .setParameter("nome", "%" + name + "%")
                 .getResultList();
    }
}
