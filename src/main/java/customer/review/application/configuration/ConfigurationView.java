package customer.review.application.configuration;


import customer.review.framework.view.AbstractCrudView;
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
@SpringView(name = ConfigurationView.VIEW_NAME)
public class ConfigurationView  extends AbstractCrudView<Configuration> {

    public static final String VIEW_NAME = "Configuration" ;

    @Autowired
    private ConfigurationService configurationService;

    @PostConstruct
    public void initialize(){
        setService(configurationService);
        super.initialize();

        grid.setColumns("id","key","value");
    }

    @Override
    protected List<Component> createFormFields(){
        final List<Component> components = new ArrayList<>();

        TextField  usernameField = new TextField("Username");
        bindField(usernameField,"username",String.class,String.class);
        components.add(usernameField);

        return components;
    }


}
