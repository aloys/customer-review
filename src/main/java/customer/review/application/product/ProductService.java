package customer.review.application.product;

import customer.review.framework.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Service
public class ProductService extends AbstractService<Product> {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initialize(){
        setRepository(productRepository);
        super.initialize();
    }

    @Override
    protected Optional newInstance(int index) {
        Product product = new Product();
        product.setName("Product-"+index);
        product.setCode(UUID.randomUUID().toString());
        return Optional.of(product);
    }
}
