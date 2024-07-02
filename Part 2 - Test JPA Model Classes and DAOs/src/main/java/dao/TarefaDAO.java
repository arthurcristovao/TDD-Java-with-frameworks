package dao;

import models.Tarefa;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TarefaDAO implements DAO<Tarefa> {
    private EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public void insert(Tarefa tarefa) {
        em.getTransaction().begin();
        em.persist(tarefa);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        em.getTransaction().begin();
        Tarefa tarefa = em.find(Tarefa.class, id);
        if (tarefa != null) {
            em.remove(tarefa);
        }
        em.getTransaction().commit();
    }

    @Override
    public void update(Tarefa tarefa) {
        em.getTransaction().begin();
        em.merge(tarefa);
        em.getTransaction().commit();
    }

    @Override
    public List<Tarefa> list(int limit, int offset) {
        return em.createQuery("SELECT t FROM Tarefa t", Tarefa.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tarefa get(int id) {
        return em.find(Tarefa.class, id);
    }

    public List<Tarefa> listByPessoaId(int pessoaId) {
        return em.createQuery("SELECT t FROM Tarefa t WHERE t.pessoa.id = :pessoaId", Tarefa.class)
                .setParameter("pessoaId", pessoaId)
                .getResultList();
    }
}
