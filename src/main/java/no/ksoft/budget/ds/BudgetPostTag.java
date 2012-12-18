package no.ksoft.budget.ds;

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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "budget_post_tag", schema = "public", uniqueConstraints =
@UniqueConstraint(columnNames = "tag_name"))
@NamedQueries(value = {
    @NamedQuery(name = "BudgetPostTag.findAll", query = "SELECT object(b) FROM BudgetPostTag as b")
    })
public class BudgetPostTag extends Any implements java.io.Serializable {

    @Column(name = "tag_name", unique = true, nullable = false, length = 256)
    private String tagName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "budgetPostTag")
    private Set<BudgetPost> budgetPosts = new HashSet(0);

    public BudgetPostTag() {
    }

    public BudgetPostTag(int id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public BudgetPostTag(int id, String tagName, Set<BudgetPost> budgetPosts) {
        this.id = id;
        this.tagName = tagName;
        this.budgetPosts = budgetPosts;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<BudgetPost> getBudgetPosts() {
        return this.budgetPosts;
    }

    public void setBudgetPosts(Set budgetPosts) {
        this.budgetPosts = budgetPosts;
    }
}
