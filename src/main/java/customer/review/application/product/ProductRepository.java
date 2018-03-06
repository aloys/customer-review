package customer.review.application.product;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByName(String name);

    Product findByCode(String code);
}
