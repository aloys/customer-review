package customer.review.framework.view;

import customer.review.framework.model.Model;
import customer.review.framework.service.Service;
import customer.review.framework.util.ReflectionUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractCrudView<E extends Model> extends AbstractView<E> {

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected Service<E> service;

    protected Grid grid;

    protected Panel formPanel;

    protected final HorizontalSplitPanel mainContent = new HorizontalSplitPanel();

    protected final HorizontalLayout toolbar = new HorizontalLayout();

    private boolean readOnlyMode;

    @PostConstruct
    public void initialize() {
        super.initialize();

        grid = new Grid(entityClass);
        grid.setSizeFull();
        grid.setStyleName(ValoTheme.TABLE_SMALL);

        toolbar.addComponent(createButton("Refresh", VaadinIcons.REFRESH, (event) -> refresh()));

        if(!readOnlyMode){
            toolbar.addComponent(createButton("Add", VaadinIcons.PLUS_CIRCLE, (event) -> create()));
            toolbar.addComponent(createButton("Edit", VaadinIcons.EDIT, (event) -> update()));
            toolbar.addComponent(createButton("Delete", VaadinIcons.MINUS_CIRCLE, (event) -> delete()));
        }


        mainContent.setSizeFull();


        Optional<FormLayout> form = createForm();
        if (form.isPresent()) {
            formPanel = new Panel("Edit Form");
            formPanel.setSizeFull();
            formPanel.setContent(form.get());
        }

        addComponent(toolbar);
        addComponentsAndExpand(mainContent);

        refresh();
    }

    protected void refresh() {
        logger.debug("Executing table refresh");

        if (grid != null && service != null) {
            grid.setItems(service.findlAll());
        }
        hideFrom();
    }


    protected void create() {
        logger.debug("Executing create item");

        if (formPanel != null) {
            E instance = ReflectionUtil.newInstance(entityClass);
            getBinder().setBean(instance);
            showFrom();
        } else {
            NOTIFICATION_MANAGER.showWarnMessage(String.format("%s is not editable", entityClass.getSimpleName()));
        }
    }

    protected void delete() {
        logger.debug("Executing delete item");

        final E selected = (E) grid.asSingleSelect().getValue();

        if (selected != null) {
            service.delete(selected);
            refresh();
            NOTIFICATION_MANAGER.showTrayMessage(String.format("%s with id: %s was deleted", entityClass.getSimpleName(), selected.getId()));
        } else {
            NOTIFICATION_MANAGER.showWarnMessage(String.format("Please select a %s to delete", entityClass.getSimpleName()));
        }
    }

    protected void update() {
        logger.debug("Executing delete item");

        if (formPanel == null) {
            NOTIFICATION_MANAGER.showWarnMessage(String.format("%s is not editable", entityClass.getSimpleName()));
            return;
        }

        final E selected = (E) grid.asSingleSelect().getValue();
        if (selected == null) {
            NOTIFICATION_MANAGER.showWarnMessage(String.format("Please select a %s to delete", entityClass.getSimpleName()));
            return;
        }

        if (formPanel != null) {
            getBinder().setBean(selected);
            showFrom();
        }
    }


    protected void save() {
        logger.debug("Executing save item");

        final E entity = getBinder().getBean();
        service.save(entity);
        refresh();

        NOTIFICATION_MANAGER.showTrayMessage(String.format("%s with id: %s was saved", entityClass.getSimpleName(), entity.getId()));

    }

    protected void showFrom() {
        if (formPanel != null) {
            mainContent.setSplitPosition(100.0f / (float) GOLDEN_RATIO, Unit.PERCENTAGE);
            mainContent.setFirstComponent(grid);
            mainContent.setSecondComponent(formPanel);

            formPanel.setHeight(grid.getHeight(), grid.getHeightUnits());
        } else {
            mainContent.removeAllComponents();
            mainContent.setSplitPosition(100, Unit.PERCENTAGE);
            mainContent.setFirstComponent(grid);
        }

    }

    protected void hideFrom() {
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

    private Optional<FormLayout> createForm() {
        return createForm(createFormFields());
    }


    protected List<Component> createFormFields() {
        return Collections.emptyList();
    }

    protected Optional<FormLayout> createForm(final List<Component> fields) {
        if (!fields.isEmpty()) {
            final FormLayout form = new FormLayout();
            form.setSizeFull();

            fields.forEach(field -> form.addComponent(field));

            Button saveButton = createButton("Save", null, (event) -> save());
            saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            form.addComponent(saveButton);

            return Optional.of(form);
        }
        return Optional.empty();
    }

    protected final void setHidable(String columnName) {
        final Grid.Column column = grid.getColumn(columnName);
        if (column != null) {
            //Allow user to hide this column
            column.setHidable(true);

        } else {
            logger.error("No column with id:{} for grid in view of entity: {}", columnName, entityClass.getName());
        }
    }

    public boolean isReadOnlyMode() {
        return readOnlyMode;
    }

    public void setReadOnlyMode(boolean readOnlyMode) {
        this.readOnlyMode = readOnlyMode;
    }
}
