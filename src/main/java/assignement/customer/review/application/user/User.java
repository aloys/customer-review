package assignement.customer.review.application.user;

import assignement.customer.review.application.review.Review;
import assignement.customer.review.framework.model.Model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class User implements Model {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true,nullable = false)
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return username;
    }
}
