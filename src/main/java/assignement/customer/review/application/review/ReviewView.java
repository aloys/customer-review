package assignement.customer.review.application.review;

import assignement.customer.review.framework.view.AbstractView;
import com.vaadin.spring.annotation.SpringView;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = ReviewView.VIEW_NAME)
public class ReviewView extends AbstractView<Review> {


    public static final String VIEW_NAME = "Review" ;


}