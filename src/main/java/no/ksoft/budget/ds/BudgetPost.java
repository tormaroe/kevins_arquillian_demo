package no.ksoft.budget.ds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "budget_post", schema = "public")
@NamedQueries(value = {
    @NamedQuery(name = "BudgetPost.findAll", query = "SELECT object(b) FROM BudgetPost as b")
})
public class BudgetPost extends Any implements java.io.Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_post_tag_id", nullable = false)
    private BudgetPostTag budgetPostTag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;
    @Column(name = "amount", precision = 17, scale = 17)
    private Double amount;
    @Column(name = "amount_type")
    private Integer amountType;
    @Column(name = "description", length = 2000)
    private String description;

    public BudgetPost() {
    }

    public BudgetPost(int id, BudgetPostTag budgetPostTag, Budget budget) {
        this.id = id;
        this.budgetPostTag = budgetPostTag;
        this.budget = budget;
    }

    public BudgetPost(int id, BudgetPostTag budgetPostTag, Budget budget, Double amount, Integer amountType, String description) {
        this.id = id;
        this.budgetPostTag = budgetPostTag;
        this.budget = budget;
        this.amount = amount;
        this.amountType = amountType;
        this.description = description;
    }

    public BudgetPostTag getBudgetPostTag() {
        return this.budgetPostTag;
    }

    public void setBudgetPostTag(BudgetPostTag budgetPostTag) {
        this.budgetPostTag = budgetPostTag;
    }

    public Budget getBudget() {
        return this.budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAmountType() {
        return this.amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
