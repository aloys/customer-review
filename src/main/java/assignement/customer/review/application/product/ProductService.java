package assignement.customer.review.application.product;

import assignement.customer.review.framework.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Service
public class ProductService extends AbstractService {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initialize(){
        setRepository(productRepository);
    }


}
