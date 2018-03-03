package assignement.customer.review.framework.view;

import assignement.customer.review.framework.model.Model;
import assignement.customer.review.framework.service.Service;
import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractView<E extends Model> extends VerticalLayout implements View {

    protected Service<E> service;

    @PostConstruct
    protected void init() {
        addComponent(new Label(getClass().getName()));
    }

    @Autowired
    public void setService(Service<E> service) {
        this.service = service;
    }
}
