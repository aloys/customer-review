package assignement.customer.review.application.user;


import assignement.customer.review.framework.view.AbstractView;
import com.vaadin.spring.annotation.SpringView;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = UserView.VIEW_NAME)
public class UserView  extends AbstractView<User> {

    public static final String VIEW_NAME = "User" ;


}
