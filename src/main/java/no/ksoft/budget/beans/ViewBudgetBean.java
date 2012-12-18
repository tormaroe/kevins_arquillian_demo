package no.ksoft.budget.beans;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.service.BudgetService;
import org.jboss.solder.servlet.http.RequestParam;

/**
 *
 * @author kevin
 */
@Named("viewBudgetBean")
@RequestScoped
public class ViewBudgetBean {

    @Inject
    BudgetService budgetService;
    @Getter
    @Setter
    @Inject
    @RequestParam("budgetId")
    Instance<Integer> budgetId;

    public Budget getCurrentBudget() {
        return budgetService.findById(getBudgetId().get().intValue());
    }
}
