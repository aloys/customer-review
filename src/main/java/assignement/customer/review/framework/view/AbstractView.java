package assignement.customer.review.framework.view;

import assignement.customer.review.framework.model.Model;
import assignement.customer.review.framework.service.Service;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractView<E extends Model> extends VerticalLayout implements View {

    protected Service<E> service;

    protected Grid grid;


    @PostConstruct
    public void initialize() {
        final Optional<Class<?>> entityClass = resolveGeneric(0);
        if(!entityClass.isPresent()){
            throw new ViewCreationException(String.format("Cannot resolve entity class of view: %s",getClass().getName()));
        }

        grid = new Grid(entityClass.get());
        grid.setSizeFull();
        grid.setItems(service.findlAll());

        addComponent(grid);
    }

    public void setItems(Collection<E> items) {
        grid.setItems(items);
    }


    public void setService(Service<E> service) {
        this.service = service;
    }

    private Optional<Class<?>> resolveGeneric(int index) {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        if (genericSuperclass != null) {
            final Type[] arguments = genericSuperclass.getActualTypeArguments();

            if (arguments != null && arguments.length >= index + 1) {
                final Type type = arguments[index];
                if (type.getClass() == Class.class) {
                    return Optional.ofNullable((Class<?>) type);
                }
            }
        }
        return Optional.empty();
    }
}
