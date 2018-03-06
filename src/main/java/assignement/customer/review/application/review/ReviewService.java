package assignement.customer.review.application.review;


import assignement.customer.review.application.product.Product;
import assignement.customer.review.application.product.ProductRepository;
import assignement.customer.review.application.user.User;
import assignement.customer.review.application.user.UserRepository;
import assignement.customer.review.framework.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Service
public class ReviewService extends AbstractService<Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initialize(){
        setRepository(reviewRepository);
        enableDatanItializion = false;
        super.initialize();
    }

    @Override
    public Review save(Review review) {

        final User user = userRepository.findByUsername(review.getReviewer());
        final Product product = productRepository.findByName(review.getReviewedItem());

        review.setUser(user);
        review.setProduct(product);

        final Review saved = reviewRepository.save(review);


        user.getReviews().add(saved);
        userRepository.save(user);


        product.getReviews().add(saved);
        productRepository.save(product);

        return saved;
    }
}
