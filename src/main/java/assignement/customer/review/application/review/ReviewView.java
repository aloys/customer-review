package assignement.customer.review.application.review;

import assignement.customer.review.framework.view.AbstractCrudView;
import assignement.customer.review.framework.view.AbstractView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = ReviewView.VIEW_NAME)
public class ReviewView extends AbstractCrudView<Review> {


    public static final String VIEW_NAME = "Review" ;

    private  final TabSheet tabSheet = new TabSheet();


    @Autowired
    private ReviewService reviewService;

    @PostConstruct
    public void initialize(){
        final Button createButton = createButton("Create a Review", VaadinIcons.ALARM, (event) -> create());
        createButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        toolbar.addComponent(createButton);

       super.initialize();
    }


    @Override
    protected List<Component> createFormFields(){
        final List<Component> components = new ArrayList<>();

        TextField idField = new TextField("Id");
        idField.setReadOnly(true);
        idField.setWidth(100, Unit.PIXELS);
        bindField(idField,"id",String.class,Long.TYPE);
        components.add(idField);

        TextArea  contentField = new TextArea("Content");
        bindField(contentField,"content",String.class,String.class);
        components.add(contentField);

        return components;
    }

}