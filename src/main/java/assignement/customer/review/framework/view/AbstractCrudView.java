package assignement.customer.review.framework.view;

import assignement.customer.review.framework.model.Model;
import assignement.customer.review.framework.service.Service;
import assignement.customer.review.framework.util.ReflectionUtil;
import com.vaadin.data.*;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amazimpaka on 2018-03-02
 */
public abstract class AbstractCrudView<E extends Model> extends AbstractView {

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<E> entityClass;

    protected Service<E> service;

    protected Grid grid;

    private Panel formPanel;

    private final HorizontalSplitPanel mainContent = new HorizontalSplitPanel();

    private static final Map<Class<?>, PropertySet<?>> PROPERTIES_SET_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Binder<?>> BINDERS_CACHE = new ConcurrentHashMap<>();


    @PostConstruct
    public void initialize() {
        final Optional<Class<?>> genericType = ReflectionUtil.resolveGeneric(getClass(), 0);
        if (!genericType.isPresent()) {
            showErrorMessage(String.format("Cannot resolve entity class of view: %s", getClass().getName()));
        }
        entityClass = (Class<E>) genericType.get();

        grid = new Grid(entityClass);
        grid.setSizeFull();
        grid.setStyleName(ValoTheme.TABLE_SMALL);

        final HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createButton("Refresh", VaadinIcons.REFRESH, (event) -> refresh()));
        toolbar.addComponent(createButton("Add", VaadinIcons.PLUS_CIRCLE, (event) -> create()));
        toolbar.addComponent(createButton("Edit", VaadinIcons.EDIT, (event) -> update()));
        toolbar.addComponent(createButton("Delete", VaadinIcons.MINUS_CIRCLE, (event) -> delete()));


        Optional<FormLayout> form = createForm();
        if (form.isPresent()) {

            mainContent.setSizeFull();

            addComponent(toolbar);
            addComponent(mainContent);
            setExpandRatio(mainContent, 1);

            formPanel = new Panel("Edit Form");
            formPanel.setSizeFull();
            formPanel.setContent(form.get());
        } else {

            addComponent(toolbar);
            addComponent(grid);
            setExpandRatio(grid, 1);

        }

        refresh();
    }

    protected void refresh() {
        logger.debug("Executing table refresh");
        grid.setItems(service.findlAll());
        hideFrom();
    }


    protected void create() {
        logger.debug("Executing create item");

        if (formPanel != null) {
            E instance = ReflectionUtil.newInstance(entityClass);
            getBinder().setBean(instance);
            showFrom();
        } else {
            showWarnMessage(String.format("%s is not editable", entityClass.getSimpleName()));
        }
    }

    protected void delete() {
        logger.debug("Executing delete item");

        final E selected = (E) grid.asSingleSelect().getValue();

        if (selected != null) {
            service.delete(selected);
            refresh();
            showTrayMessage(String.format("%s with id: %s was deleted", entityClass.getSimpleName(), selected.getId()));
        } else {
            showWarnMessage(String.format("Please select a %s to delete", entityClass.getSimpleName()));
        }
    }

    protected void update() {
        logger.debug("Executing delete item");

        if (formPanel == null) {
            showWarnMessage(String.format("%s is not editable", entityClass.getSimpleName()));
            return;
        }

        final E selected = (E) grid.asSingleSelect().getValue();
        if (selected == null) {
            showWarnMessage(String.format("Please select a %s to delete", entityClass.getSimpleName()));
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

        showTrayMessage(String.format("%s with id: %s was saved", entityClass.getSimpleName(), entity.getId()));

    }

    protected void showFrom() {
        if (formPanel != null) {
            mainContent.setSplitPosition(75, Unit.PERCENTAGE);
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
        final List<Component> fields = createFormFields();
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

    protected <FV, BV> void bindField(HasValue<FV> field,
                                      String propertyName,
                                      Class<FV> fieldvalueClass,
                                      Class<BV> beanValueClass) {
        if (propertyName != null) {
            final PropertySet<E> propertySet = getBeanPropertySet();
            final Optional<PropertyDefinition<E, ?>> wrapper = propertySet.getProperty(propertyName);
            if (wrapper.isPresent()) {

                final PropertyDefinition<E, BV> propertyDefinition = (PropertyDefinition<E, BV>) wrapper.get();
                final Optional<Converter<?, BV>> optionalConverter = ConverterCache.getConverter(propertyDefinition.getType());

                if (optionalConverter.isPresent()) {
                    final Converter<FV, BV> converter = ((Converter<FV, BV>) optionalConverter.get());

                    Binder.BindingBuilder<E, BV> bindingBuilder = getBinder()
                            .forField(field)
                            .withValidator(new BeanValidator(entityClass, propertyName))
                            .withConverter(converter);

                    final ValueProvider<E, BV> getter = propertyDefinition.getGetter();
                    final Optional<Setter<E, BV>> setter = propertyDefinition.getSetter();
                    bindingBuilder.bind(getter, setter.get());

                }else if (fieldvalueClass == beanValueClass){
                    //No converter is needed
                    Binder.BindingBuilder<E, FV> bindingBuilder = getBinder()
                            .forField(field)
                            .withValidator(new BeanValidator(entityClass, propertyName));

                    final ValueProvider<E, BV> getter = propertyDefinition.getGetter();
                    final Optional<Setter<E, BV>> setter = propertyDefinition.getSetter();
                    bindingBuilder.bind((ValueProvider<E, FV>) getter, (Setter<E, FV>)setter.get());
                }
            }
        }
    }

    protected List<Component> createFormFields() {
        return Collections.emptyList();
    }

    private PropertySet<E> getBeanPropertySet() {
        return (PropertySet<E>) PROPERTIES_SET_CACHE.computeIfAbsent(entityClass,
                (key) -> BeanPropertySet.get(key));
    }

    public Binder<E> getBinder() {
        return (Binder<E>) BINDERS_CACHE.computeIfAbsent(entityClass,
                (key) -> new BeanValidationBinder<>(key));
    }


}
