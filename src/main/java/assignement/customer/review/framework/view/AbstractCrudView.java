package assignement.customer.review.framework.view;

import assignement.customer.review.framework.model.Model;
import assignement.customer.review.framework.service.Service;
import assignement.customer.review.framework.util.ReflectionUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
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


    protected Optional<FormLayout> form;

    private final  HorizontalSplitPanel mainContent = new HorizontalSplitPanel();


    @PostConstruct
    public void initialize() {
        final Optional<Class<?>> genericType = ReflectionUtil.resolveGeneric(getClass(),0);
        if(!genericType.isPresent()){
            showErrorMessage(String.format("Cannot resolve entity class of view: %s",getClass().getName()));
        }
        entityClass = (Class<E>) genericType.get();

        grid = new Grid(entityClass);
        grid.setSizeFull();
        grid.setStyleName(ValoTheme.TABLE_SMALL);

        final HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createButton("Refresh",VaadinIcons.REFRESH,(event) -> refresh()));
        toolbar.addComponent(createButton("Add",VaadinIcons.PLUS_CIRCLE,(event) -> create()));
        toolbar.addComponent(createButton("Edit",VaadinIcons.EDIT,null));
        toolbar.addComponent(createButton("Delete",VaadinIcons.MINUS_CIRCLE,(event) -> delete()));


        form = createForm();
        if(form.isPresent()){

            mainContent.setSizeFull();

            addComponent(toolbar);
            addComponent(mainContent);
            setExpandRatio(mainContent, 1);
        }else{

            addComponent(toolbar);
            addComponent(grid);
            setExpandRatio(grid, 1);

        }

        refresh();
    }

    protected void refresh(){
        logger.debug("Executing table refresh");
        grid.setItems(service.findlAll());
        hideFrom();
    }


    protected void create(){
        logger.debug("Executing create item");

        if(form.isPresent()){
            showFrom();
        }else{
            showWarnMessage(String.format("%s is not editable",entityClass.getSimpleName()));
        }
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

    protected void showFrom(){
        mainContent.setSplitPosition(75, Unit.PERCENTAGE);
        mainContent.setFirstComponent(grid);
        mainContent.setSecondComponent(form.get());
    }

    protected void hideFrom(){
        mainContent.removeAllComponents();
        mainContent.setSplitPosition(100, Unit.PERCENTAGE);
        mainContent.setFirstComponent(grid);
    }


    public void setItems(Collection<E> items) {
        grid.setItems(items);
    }


    public void setService(Service<E> service) {
        this.service = service;
    }

    protected Optional<FormLayout> createForm(){
        return Optional.empty();
    }



}
