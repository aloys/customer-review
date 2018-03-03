package assignement.customer.review.framework;

import assignement.customer.review.framework.view.DefaultView;
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
import java.util.Date;

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


    @PostConstruct
    public void initialize() {
        setNavigator(navigator);

        navigator.setErrorView(DefaultView.class);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final Date date = new Date();

        HorizontalSplitPanel root = new HorizontalSplitPanel();
        root.setSizeFull();
        root.setSplitPosition(240, Unit.PIXELS);
        setContent(root);

        final CssLayout menuLayout = new CssLayout();
        //menuLayout.setPrimaryStyleName(MENU_STYLE_NAME);
        menuLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        menuLayout.setSizeFull();
        menuLayout.addComponent(createMenuTree());

        springViewDisplay.setContent(new Label());

        root.setFirstComponent(menuLayout);
        root.setSecondComponent(springViewDisplay);

        Responsive.makeResponsive(this);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    private Tree<String> createMenuTree() {
        final Tree<String> tree = new Tree<>();
        final TreeData<String> treeData = new TreeData<>();
        tree.setDataProvider(new TreeDataProvider<>(treeData));
        treeData.addRootItems(springViewProvider.getViewNamesForCurrentUI());

        tree.addItemClickListener(event -> {
                    final String node = event.getItem();
                    UI.getCurrent().getNavigator().navigateTo(node);
                }
        );

        return tree;
    }
}