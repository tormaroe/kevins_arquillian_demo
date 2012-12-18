package no.ksoft.budget.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import no.ksoft.budget.ds.BudgetPostTag;
import no.ksoft.budget.service.BudgetPostTagService;

/**
 *
 * @author kevin
 */
@Stateless
public class BudgetPostTagServiceImpl implements BudgetPostTagService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public BudgetPostTag findById(int id) {
        return em.find(BudgetPostTag.class, id);
    }

    @Override
    public List<BudgetPostTag> findAll() {
        List l = em.createNamedQuery("BudgetPostTag.findAll").getResultList();

        if (l == null) {
            return new ArrayList<>();
        }
        return (List<BudgetPostTag>) l;
    }

    @Override
    public BudgetPostTag makePersistent(BudgetPostTag entity) {
        em.persist(entity);
        return entity;
    }
}
