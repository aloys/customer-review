package assignement.customer.review.application.review;

import assignement.customer.review.framework.model.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class Review implements Model {

    @Id
    @GeneratedValue
    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
