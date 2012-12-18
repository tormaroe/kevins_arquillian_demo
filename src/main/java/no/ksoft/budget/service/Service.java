package no.ksoft.budget.service;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kevin
 */
@Remote
public interface Service<T> {

    T findById(int id);

    List<T> findAll();

    T makePersistent(T entity);
}
