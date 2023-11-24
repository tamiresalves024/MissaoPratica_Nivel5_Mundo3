package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Movimentos;
import model.Usuarios;

public class UsuariosJpaController implements Serializable {

    private final EntityManagerFactory emf;

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Usuarios findUsuario(String login, String senha) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.nomeUsuarios = :login AND u.senhaUsuarios = :senha");
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            List<Usuarios> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0);
            }
            return null;
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuarioById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getMovimentosCollection() == null) {
            usuarios.setMovimentosCollection(new ArrayList<Movimentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Movimentos> attachedMovimentosCollection = new ArrayList<Movimentos>();
            for (Movimentos movimentosCollectionMovimentosToAttach : usuarios.getMovimentosCollection()) {
                movimentosCollectionMovimentosToAttach = em.getReference(movimentosCollectionMovimentosToAttach.getClass(), movimentosCollectionMovimentosToAttach.getIdMovimento());
                attachedMovimentosCollection.add(movimentosCollectionMovimentosToAttach);
            }
            usuarios.setMovimentosCollection(attachedMovimentosCollection);
            em.persist(usuarios);
            for (Movimentos movimentosCollectionMovimentos : usuarios.getMovimentosCollection()) {
                Usuarios oldOperadorIDOfMovimentosCollectionMovimentos = movimentosCollectionMovimentos.getOperadorID();
                movimentosCollectionMovimentos.setOperadorID(usuarios);
                movimentosCollectionMovimentos = em.merge(movimentosCollectionMovimentos);
                if (oldOperadorIDOfMovimentosCollectionMovimentos != null) {
                    oldOperadorIDOfMovimentosCollectionMovimentos.getMovimentosCollection().remove(movimentosCollectionMovimentos);
                    oldOperadorIDOfMovimentosCollectionMovimentos = em.merge(oldOperadorIDOfMovimentosCollectionMovimentos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuarios = em.merge(usuarios);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = usuarios.getIdUsuarios();
                if (findUsuarioById(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Collection<Movimentos> movimentosCollection = usuarios.getMovimentosCollection();
            for (Movimentos movimentosCollectionMovimentos : movimentosCollection) {
                movimentosCollectionMovimentos.setOperadorID(null);
                movimentosCollectionMovimentos = em.merge(movimentosCollectionMovimentos);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
