package no.ksoft.budget.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import no.ksoft.budget.ds.BudgetPost;
import no.ksoft.budget.service.BudgetPostService;

/**
 *
 * @author kevin
 */
@Stateless
public class BudgetPostServiceImpl implements BudgetPostService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public BudgetPost findById(int id) {
        return em.find(BudgetPost.class, id);
    }

    @Override
    public List<BudgetPost> findAll() {
        List l = em.createNamedQuery("BudgetPost.findAll").getResultList();

        if (l == null) {
            return new ArrayList<>();
        }
        return (List<BudgetPost>) l;
    }

    @Override
    public BudgetPost makePersistent(BudgetPost entity) {
        em.persist(entity);
        return entity;
    }
}
