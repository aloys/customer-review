package assignement.customer.review.application.user;


import assignement.customer.review.framework.view.AbstractCrudView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = UserView.VIEW_NAME)
public class UserView  extends AbstractCrudView<User> {

    public static final String VIEW_NAME = "User" ;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize(){
        setService(userService);
        super.initialize();
    }

    @Override
    protected List<Component> createFormFields(){
        final List<Component> components = new ArrayList<>();

        TextField idField = new TextField("Id");
        idField.setReadOnly(true);
        idField.setWidth(100, Unit.PIXELS);
        bindField(idField,"id",String.class,Long.TYPE);
        components.add(idField);

        TextField  usernameField = new TextField("Username");
        bindField(usernameField,"username",String.class,String.class);
        components.add(usernameField);

        return components;
    }


}
