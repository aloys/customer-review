package assignement.customer.review.application.product;


import assignement.customer.review.framework.view.AbstractView;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = ProductView.VIEW_NAME)
public class ProductView  extends AbstractView<Product> {


    public static final String VIEW_NAME = "Product" ;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void initialize(){
        setService(productService);
        super.initialize();
    }



}
