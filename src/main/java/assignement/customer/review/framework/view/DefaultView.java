package assignement.customer.review.framework.view;

import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
public class DefaultView extends AbstractView {

    @PostConstruct
    protected void initialize() {
        addComponent(new Label(""));
    }

}
