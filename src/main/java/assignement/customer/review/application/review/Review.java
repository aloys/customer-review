package assignement.customer.review.application.review;

import assignement.customer.review.application.product.Product;
import assignement.customer.review.application.user.User;
import assignement.customer.review.framework.model.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class Review implements Model {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="USER_ID", nullable=false, updatable=false)
    private User user;

    @ManyToOne(optional=false)
    @JoinColumn(name="PRODUCT_ID", nullable=false, updatable=false)
    private Product product;

    private String content;

    private Date createdDate;

    private Date updatedDate;

    @Transient
    private  String reviewer;

    @Transient
    private  String reviewedItem;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.reviewer = user.getUsername();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.reviewedItem = product.getName();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewer() {
        final User user = getUser();
        return  user != null ? user.getUsername() : reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewedItem() {
        final Product product = getProduct();
        return  product != null ? product.getName() : reviewedItem;
    }

    public void setReviewedItem(String reviewedItem) {
        this.reviewedItem = reviewedItem;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
