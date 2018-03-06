package assignement.customer.review.application.review;

import assignement.customer.review.application.product.ProductService;
import assignement.customer.review.application.user.UserService;
import assignement.customer.review.framework.view.AbstractCrudView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by amazimpaka on 2018-03-02
 */
@SpringView(name = ReviewView.VIEW_NAME)
public class ReviewView extends AbstractCrudView<Review> {


    public static final String VIEW_NAME = "Review" ;

    private  final TabSheet tabSheet = new TabSheet();


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    private final ComboBox<String> productsBox = new ComboBox<>("Product");

    private final  ComboBox<String> usersBox = new ComboBox<>("User");

    @PostConstruct
    public void initialize(){
        setService(reviewService);
        setReadOnlyMode(true);

        final Button createButton = createButton("Create a Review", VaadinIcons.ALARM, (event) -> create());
        createButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        toolbar.addComponent(createButton);

       super.initialize();

        grid.setColumns("id","content","reviewer","reviewedItem","createdDate","updatedDate");

        setHidable("createdDate");
        setHidable("updatedDate");


    }

    @Override
    protected void update() {
        super.update();

        final Review review = getBinder().getBean();
        productsBox.setValue(review.getReviewedItem());
        usersBox.setValue(review.getReviewer());
        getBinder().setBean(review);
    }

    @Override
    protected void save() {
        final Review review = getBinder().getBean();
        if(review.getId() == 0){
            review.setCreatedDate(new Date());
        }
        review.setUpdatedDate(new Date());
        super.save();
    }

    @Override
    protected List<Component> createFormFields(){
        final List<Component> components = new ArrayList<>();



        final List<String> products = productService.findlAll()
                .stream().map(user -> user.getName())
                .collect(Collectors.toList());
        productsBox.setItems(products);
        bindField(productsBox,"reviewedItem",String.class,String.class);
        components.add(productsBox);

        final List<String> users = userService.findlAll()
                .stream().map(user -> user.getUsername())
                .collect(Collectors.toList());
        usersBox.setItems(users);
        bindField(usersBox,"reviewer",String.class,String.class);
        components.add(usersBox);


        TextArea  contentField = new TextArea("Content");
        contentField.setWidth(240, Unit.PIXELS);
        bindField(contentField,"content",String.class,String.class);
        components.add(contentField);

        return components;
    }

}