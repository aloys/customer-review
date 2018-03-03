package assignement.customer.review.application.user;


import assignement.customer.review.framework.view.AbstractCrudView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.Normalizer;
import java.util.Optional;

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
    protected Optional<FormLayout> createForm() {
        final FormLayout form = new FormLayout();

        TextField idField = new TextField();
        idField.setReadOnly(true);
        form.addComponent(idField);

        TextField  usernameField = new TextField();
        form.addComponent(usernameField);

        return Optional.of(form);
    }
}
