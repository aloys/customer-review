package assignement.customer.review.application.product;

import assignement.customer.review.framework.model.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class Product implements Model {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String code;

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
}
