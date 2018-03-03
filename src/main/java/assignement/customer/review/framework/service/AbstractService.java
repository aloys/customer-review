package assignement.customer.review.framework.service;

import assignement.customer.review.framework.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractService<E extends Model> implements Service<E>{

    protected  CrudRepository<E, Long> repository;


    public void setRepository(CrudRepository<E, Long> repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }
}
