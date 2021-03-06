package customer.review.framework;

import customer.review.application.configuration.ConfigurationView;
import customer.review.application.product.Product;
import customer.review.application.product.ProductView;
import customer.review.application.review.ReviewView;
import customer.review.application.user.UserView;
import customer.review.framework.view.exception.ApplicationErrorHandler;
import customer.review.framework.view.navigation.DefaultView;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by amazimpaka on 2018-03-02
 */


@SpringUI
@SpringViewDisplay
public class ApplicationUI extends UI implements ViewDisplay {


    public static final String MENU_STYLE_NAME = "valo-menu";

    private Panel springViewDisplay;

    @Autowired
    private SpringViewProvider springViewProvider;

    @Autowired
    private transient SpringNavigator navigator;

    private static  final String[] VIEWS = {ReviewView.VIEW_NAME,
            UserView.VIEW_NAME,
            ProductView.VIEW_NAME,
            ConfigurationView.VIEW_NAME};


    @PostConstruct
    public void initialize() {
        setNavigator(navigator);

        navigator.setErrorView(DefaultView.class);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        UI.getCurrent().setErrorHandler(ApplicationErrorHandler.getInstance());

        HorizontalSplitPanel root = new HorizontalSplitPanel();
        root.setSizeFull();
        root.setSplitPosition(160, Unit.PIXELS);
        setContent(root);

        final CssLayout menuLayout = new CssLayout();
        menuLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        menuLayout.setSizeFull();
        menuLayout.addComponent(createMenuTree(VIEWS));

        springViewDisplay.setContent(new Label());

        root.setFirstComponent(menuLayout);
        root.setSecondComponent(springViewDisplay);

        Responsive.makeResponsive(this);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    private Tree<String> createMenuTree(final String[] views) {
        final Tree<String> tree = new Tree<>();
        final TreeData<String> treeData = new TreeData<>();
        tree.setDataProvider(new TreeDataProvider<>(treeData));

        treeData.addRootItems(views);

        tree.addItemClickListener(event -> {
                    final String node = event.getItem();
                    UI.getCurrent().getNavigator().navigateTo(node);
                }
        );

        return tree;
    }
}