package no.ksoft.budget.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.service.BudgetService;

/**
 *
 * @author kevin
 */
@Stateless
public class BudgetServiceImpl implements BudgetService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Budget findById(int id) {
        return em.find(Budget.class, id);
    }

    @Override
    public List<Budget> findAll() {
        List l = em.createNamedQuery("Budget.findAll").getResultList();

        if (l == null) {
            return new ArrayList<>();
        }
        return (List<Budget>) l;
    }

    @Override
    public Budget makePersistent(Budget entity) {
        em.persist(entity);
        return entity;
    }
}
