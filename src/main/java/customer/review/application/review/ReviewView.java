package customer.review.application.review;

import com.vaadin.data.HasValue;
import customer.review.application.configuration.ConfigurationService;
import customer.review.application.product.ProductService;
import customer.review.application.user.UserService;
import customer.review.framework.util.ReflectionUtil;
import customer.review.framework.view.AbstractCrudView;
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
    public static final String DIGITS_REGEX = "[0-9]+";

    private  final TabSheet tabSheet = new TabSheet();


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ConfigurationService configurationService;

    private final ComboBox<String> productsBox = new ComboBox<>("Product");

    private final  ComboBox<String> usersBox = new ComboBox<>("User");

    private final Slider ratingField = new Slider();

    private final TextField minRating = new TextField();

    private final TextField maxRating = new TextField();

    private final ReviewCriteria criteria = new ReviewCriteria();

    @PostConstruct
    public void initialize(){
        setService(reviewService);
        setReadOnlyMode(true);

        super.initialize();
        toolbar.removeAllComponents();

        final Label minRatingLabel = new Label("Min Rating");
        toolbar.addComponent(minRatingLabel);
        minRating.setStyleName(ValoTheme.TEXTFIELD_SMALL);
        toolbar.addComponent(minRating);

        final Label maxRatingLabel = new Label("Min Rating");
        toolbar.addComponent(maxRatingLabel);
        maxRating.setStyleName(ValoTheme.TEXTFIELD_SMALL);
        toolbar.addComponent(maxRating);

        final Button searchButton = createButton("Search", VaadinIcons.SEARCH, (event) -> search());
        searchButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        toolbar.addComponent(searchButton);

        final Button createButton = createButton("Create a Review", VaadinIcons.ALARM, (event) -> create());
        createButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        toolbar.addComponent(createButton);


        grid.setColumns("id","content","reviewedItem","reviewer","rating","createdDate","updatedDate");

        setHidable("id");
        setHidable("content");
        setHidable("reviewedItem");
        setHidable("reviewer");
        setHidable("rating");
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

        review.setRating(ratingField.getValue());

        if(review.getId() == 0){
            review.setCreatedDate(new Date());
        }
        review.setUpdatedDate(new Date());
        super.save();
    }



    protected void search() {
        final ReviewCriteria criteria = new ReviewCriteria();

        if(minRating.getValue() == null || minRating.getValue().isEmpty()){
            criteria.setMinimumRating(configurationService.getMinRating());
        }else if(!minRating.getValue().matches(DIGITS_REGEX)){
            throw new IllegalArgumentException("Invalid min rating: "+minRating.getValue());
        }else {
            criteria.setMinimumRating(Double.parseDouble(minRating.getValue()));
        }

        if(maxRating.getValue() == null || maxRating.getValue().isEmpty()){
            criteria.setMaximumRating(configurationService.getMaxRating());
        }else if(maxRating.getValue() == null || !maxRating.getValue().matches(DIGITS_REGEX)){
            throw new IllegalArgumentException("Invalid max rating: "+maxRating.getValue());
        }else {
            criteria.setMaximumRating(Double.parseDouble(maxRating.getValue()));
        }

        final List<Review> reviews = reviewService.search(criteria);
        grid.setItems(reviews);

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


        final int minRating = configurationService.getMinRating();
        final int maxRating = configurationService.getMaxRating();
        ratingField.setMin(minRating);
        ratingField.setMax(maxRating);
        ratingField.setCaption("Rating");
        components.add(ratingField);

        TextArea  contentField = new TextArea("Content");
        contentField.setWidth(240, Unit.PIXELS);
        bindField(contentField,"content",String.class,String.class);
        components.add(contentField);

        return components;
    }


}