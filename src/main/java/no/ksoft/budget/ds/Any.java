package no.ksoft.budget.ds;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author kevin
 */
@MappedSuperclass
@EqualsAndHashCode
public class Any {

    @SuppressWarnings("unused")
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    @Getter
    @Setter
    protected int id;
}
