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
import model.Produtos;

public class ProdutosJpaController implements Serializable {

    private final EntityManagerFactory emf;

    public ProdutosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Produtos> getProdutos() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Produtos p");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Produtos getProdutoById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtos.class, id);
        } finally {
            em.close();
        }
    }

    public void create(Produtos produtos) {
        if (produtos.getMovimentosCollection() == null) {
            produtos.setMovimentosCollection(new ArrayList<Movimentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Movimentos> attachedMovimentosCollection = new ArrayList<Movimentos>();
            for (Movimentos movimentosCollectionMovimentosToAttach : produtos.getMovimentosCollection()) {
                movimentosCollectionMovimentosToAttach = em.getReference(movimentosCollectionMovimentosToAttach.getClass(), movimentosCollectionMovimentosToAttach.getIdMovimento());
                attachedMovimentosCollection.add(movimentosCollectionMovimentosToAttach);
            }
            produtos.setMovimentosCollection(attachedMovimentosCollection);
            em.persist(produtos);
            for (Movimentos movimentosCollectionMovimentos : produtos.getMovimentosCollection()) {
                Produtos oldProdutoIDOfMovimentosCollectionMovimentos = movimentosCollectionMovimentos.getProdutoID();
                movimentosCollectionMovimentos.setProdutoID(produtos);
                movimentosCollectionMovimentos = em.merge(movimentosCollectionMovimentos);
                if (oldProdutoIDOfMovimentosCollectionMovimentos != null) {
                    oldProdutoIDOfMovimentosCollectionMovimentos.getMovimentosCollection().remove(movimentosCollectionMovimentos);
                    oldProdutoIDOfMovimentosCollectionMovimentos = em.merge(oldProdutoIDOfMovimentosCollectionMovimentos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produtos produtos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos persistentProdutos = em.find(Produtos.class, produtos.getIdProdutos());
            Collection<Movimentos> movimentosCollectionOld = persistentProdutos.getMovimentosCollection();
            Collection<Movimentos> movimentosCollectionNew = produtos.getMovimentosCollection();
            Collection<Movimentos> attachedMovimentosCollectionNew = new ArrayList<Movimentos>();
            for (Movimentos movimentosCollectionNewMovimentosToAttach : movimentosCollectionNew) {
                movimentosCollectionNewMovimentosToAttach = em.getReference(movimentosCollectionNewMovimentosToAttach.getClass(), movimentosCollectionNewMovimentosToAttach.getIdMovimento());
                attachedMovimentosCollectionNew.add(movimentosCollectionNewMovimentosToAttach);
            }
            movimentosCollectionNew = attachedMovimentosCollectionNew;
            produtos.setMovimentosCollection(movimentosCollectionNew);
            produtos = em.merge(produtos);
            for (Movimentos movimentosCollectionOldMovimentos : movimentosCollectionOld) {
                if (!movimentosCollectionNew.contains(movimentosCollectionOldMovimentos)) {
                    movimentosCollectionOldMovimentos.setProdutoID(null);
                    movimentosCollectionOldMovimentos = em.merge(movimentosCollectionOldMovimentos);
                }
            }
            for (Movimentos movimentosCollectionNewMovimentos : movimentosCollectionNew) {
                if (!movimentosCollectionOld.contains(movimentosCollectionNewMovimentos)) {
                    Produtos oldProdutoIDOfMovimentosCollectionNewMovimentos = movimentosCollectionNewMovimentos.getProdutoID();
                    movimentosCollectionNewMovimentos.setProdutoID(produtos);
                    movimentosCollectionNewMovimentos = em.merge(movimentosCollectionNewMovimentos);
                    if (oldProdutoIDOfMovimentosCollectionNewMovimentos != null && !oldProdutoIDOfMovimentosCollectionNewMovimentos.equals(produtos)) {
                        oldProdutoIDOfMovimentosCollectionNewMovimentos.getMovimentosCollection().remove(movimentosCollectionNewMovimentos);
                        oldProdutoIDOfMovimentosCollectionNewMovimentos = em.merge(oldProdutoIDOfMovimentosCollectionNewMovimentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produtos.getIdProdutos();
                if (findProdutos(id) == null) {
                    throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos produtos;
            try {
                produtos = em.getReference(Produtos.class, id);
                produtos.getIdProdutos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.", enfe);
            }
            Collection<Movimentos> movimentosCollection = produtos.getMovimentosCollection();
            for (Movimentos movimentosCollectionMovimentos : movimentosCollection) {
                movimentosCollectionMovimentos.setProdutoID(null);
                movimentosCollectionMovimentos = em.merge(movimentosCollectionMovimentos);
            }
            em.remove(produtos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produtos> findProdutosEntities() {
        return findProdutosEntities(true, -1, -1);
    }

    public List<Produtos> findProdutosEntities(int maxResults, int firstResult) {
        return findProdutosEntities(false, maxResults, firstResult);
    }

    private List<Produtos> findProdutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produtos.class));
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

    public Produtos findProdutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produtos> rt = cq.from(Produtos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
