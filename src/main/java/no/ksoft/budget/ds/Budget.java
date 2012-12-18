package no.ksoft.budget.ds;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "budget", schema = "public")
@NamedQueries(value = {@NamedQuery(name = "Budget.findAll", query = "SELECT object(b) FROM Budget as b")})
public class Budget extends Any implements java.io.Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 29)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, length = 29)
    private Date endDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "budget")
    private Set<BudgetPost> budgetPosts = new HashSet(0);

    public Budget() {
    }

    public Budget(int id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Budget(int id, Date startDate, Date endDate, Set<BudgetPost> budgetPosts) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetPosts = budgetPosts;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<BudgetPost> getBudgetPosts() {
        return this.budgetPosts;
    }

    public void setBudgetPosts(Set budgetPosts) {
        this.budgetPosts = budgetPosts;
    }
}
