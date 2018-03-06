package customer.review.application.product;

import customer.review.application.review.Review;
import customer.review.framework.model.Model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class Product implements Model {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String code;

    @OneToMany(cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private Collection<Review> reviews;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return name;
    }
}
