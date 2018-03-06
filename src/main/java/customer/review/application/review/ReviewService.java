package customer.review.application.review;


import customer.review.application.configuration.ConfigurationService;
import customer.review.application.product.Product;
import customer.review.application.product.ProductRepository;
import customer.review.application.user.User;
import customer.review.application.user.UserRepository;
import customer.review.framework.service.AbstractService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void initialize(){
        setRepository(reviewRepository);
        enableDatanItializion = false;
        super.initialize();
    }

    @Override
    public Review save(Review review) {

        final Product product = getProduct(review);
        final User user = getUser(review);

        validateRating(review);
        validateContent(review);

        review.setUser(user);
        review.setProduct(product);
        final Review saved = reviewRepository.save(review);

        user.getReviews().add(saved);
        userRepository.save(user);


        product.getReviews().add(saved);
        productRepository.save(product);

        return saved;
    }

    public List<Review> search(ReviewCriteria criteria){
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT r FROM Review r WHERE r.rating >= :minRating " +
                " AND r.rating <= :maxRating");
        query.setParameter("minRating", criteria.getMinimumRating());
        query.setParameter("maxRating", criteria.getMaximumRating());
        return query.getResultList();
    }

    private void validateRating(Review review) {

        final int minRating = configurationService.getMinRating();
        final int maxRating = configurationService.getMaxRating();

        if(review.getRating() < minRating){
            throw new IllegalArgumentException("Rating should not be smaller than: "+ minRating);
        }
        if(review.getRating() > maxRating){
            throw new IllegalArgumentException("Rating should not be greater than: "+ maxRating);
        }
    }

    private void validateContent(Review review) {
        if(review.getContent() == null || review.getContent().isEmpty()){
            throw new IllegalArgumentException("Content field value is required");
        }

        final List<String> forbiddenWords = configurationService.getForbiddenContentWords()
                .stream()
                .filter(word -> review.getContent().contains(word)).collect(Collectors.toList());

        if(!forbiddenWords.isEmpty()){
            throw new IllegalArgumentException("Content contains these forbidden words: "+forbiddenWords);
        }
    }

    private Product getProduct(Review review) {
        if(review.getReviewedItem() == null || review.getReviewedItem().isEmpty()){
            throw new IllegalArgumentException("Product field value is required");
        }

        final Product product = productRepository.findByName(review.getReviewedItem());
        if(product == null){
            throw new IllegalArgumentException("Cannot find product with name: "+review.getReviewedItem());
        }
        return product;
    }

    private User getUser(Review review) {
        if(review.getReviewer() == null || review.getReviewer().isEmpty()){
            throw new IllegalArgumentException("User field value is required");
        }

        final User user = userRepository.findByUsername(review.getReviewer());
        if(user == null){
            throw new IllegalArgumentException("Cannot find user with username: "+review.getReviewer());
        }
        return user;
    }
}
