package assignement.customer.review.framework.service;

import assignement.customer.review.framework.model.Model;

import java.util.List;

/**
 * Created by amazimpaka on 2018-03-02
 */
public interface Service<E extends Model> {

    List<E> findlAll();

    E save(E entity);

    void delete(E entity);
}
