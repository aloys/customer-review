package assignement.customer.review.framework.service;

import assignement.customer.review.framework.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractService<E extends Model> implements Service<E>{

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected  CrudRepository<E, Long> repository;

    @Value("${application.initialize.test.data:true}")
    protected boolean enableDatanItializion;

    @Value("${application.test.data.max.size:5}")
    protected int maximumTestData;

    private static final AtomicBoolean initialized = new AtomicBoolean();

    @PostConstruct
    public void initialize(){
       initializeData();
    }

    public void setRepository(CrudRepository<E, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<E> findlAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }


    protected void initializeData() {
        if (enableDatanItializion) {

            repository.deleteAll();
            for (int i = 1; i <= maximumTestData; i++) {
                final Optional<E>  instace =  newInstance(i);
                if(instace.isPresent()){
                    save(instace.get());
                }
            }

            final long count = repository.count();
            logger.warn("Initialization - current: {} -  expected: {}", count, maximumTestData);
            initialized.set(true);
        }
    }

    protected  Optional<E> newInstance(int index) {
       return Optional.empty();
    }
}
