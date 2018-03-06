package assignement.customer.review.framework.view.navigation;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
public class DefaultView extends VerticalLayout implements View {


    public DefaultView() {
        super();
        setSizeFull();
        addComponent(new Label(""));
    }

}
