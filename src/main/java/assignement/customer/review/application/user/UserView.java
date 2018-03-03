package assignement.customer.review.application.user;


import assignement.customer.review.framework.view.AbstractView;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = UserView.VIEW_NAME)
public class UserView  extends AbstractView<User> {

    public static final String VIEW_NAME = "User" ;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize(){
        setService(userService);
        super.initialize();
    }


}
