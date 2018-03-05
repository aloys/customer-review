package assignement.customer.review.application.review;

import assignement.customer.review.application.product.Product;
import assignement.customer.review.application.user.User;
import assignement.customer.review.framework.model.Model;

import javax.persistence.*;

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
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
