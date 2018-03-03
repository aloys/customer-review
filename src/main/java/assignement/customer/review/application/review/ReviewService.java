package assignement.customer.review.application.review;


import assignement.customer.review.framework.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Service
public class ReviewService extends AbstractService {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void initialize(){
        setRepository(reviewRepository);
    }


}
