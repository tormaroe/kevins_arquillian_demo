package no.ksoft.budget.beans;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.service.BudgetService;

/**s
 *
 * @author kevin
 */
@Named("indexBean")
@RequestScoped
public class IndexBean {
    
    @Inject
    BudgetService budgetService;

    public List<Budget> getAllBudgets() {
        return budgetService.findAll();
    }
}
