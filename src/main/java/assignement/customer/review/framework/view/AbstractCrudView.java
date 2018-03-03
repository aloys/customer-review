package assignement.customer.review.framework.view;

import assignement.customer.review.framework.model.Model;
import assignement.customer.review.framework.service.Service;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractCrudView<E extends Model> extends AbstractView {

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<E> entityClass;

    protected Service<E> service;

    protected Grid grid;


    @PostConstruct
    public void initialize() {
        final Optional<Class<?>> genericType = resolveGeneric(0);
        if(!genericType.isPresent()){
            showErrorMessage(String.format("Cannot resolve entity class of view: %s",getClass().getName()));
        }
        entityClass = (Class<E>) genericType.get();

        grid = new Grid(entityClass);
        grid.setSizeFull();
        grid.setStyleName(ValoTheme.TABLE_SMALL);

        final HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createButton("Refresh",VaadinIcons.REFRESH,(event) -> refresh()));
        toolbar.addComponent(createButton("Add",VaadinIcons.PLUS_CIRCLE,null));
        toolbar.addComponent(createButton("Edit",VaadinIcons.EDIT,null));
        toolbar.addComponent(createButton("Delete",VaadinIcons.MINUS_CIRCLE,(event) -> delete()));

        addComponent(toolbar);
        addComponent(grid);
        setExpandRatio(grid, 1);

        refresh();
    }

    protected void refresh(){
        logger.debug("Executing table refresh");
        grid.setItems(service.findlAll());
    }


    protected void delete(){
        logger.debug("Executing delete item");

        final E selected = (E)grid.asSingleSelect().getValue();

        if(selected != null){
            service.delete(selected);
            refresh();
            showTrayMessage(String.format("%s with id: %s was deleted",entityClass.getSimpleName(),selected.getId()));
        }else {
            showWarnMessage(String.format("Please select a %s to delete",entityClass.getSimpleName()));
        }

    }


    public void setItems(Collection<E> items) {
        grid.setItems(items);
    }


    public void setService(Service<E> service) {
        this.service = service;
    }

    private static Button createButton(String caption, VaadinIcons icon, Button.ClickListener clickListener){
        final Button button = new Button();

        if(caption != null){
            button.setCaption(caption);
        }

        if(icon != null){
            button.setIcon(icon);
            button.setDescription(caption);
            button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        }

        if(clickListener != null){
            button.addClickListener(clickListener);
        }
        return button;
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
