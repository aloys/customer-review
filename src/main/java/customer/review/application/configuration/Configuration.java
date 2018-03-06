package customer.review.application.configuration;

import customer.review.framework.model.Model;

import javax.persistence.*;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Entity
public class Configuration implements Model {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true,nullable = false)
    private String key;

    @Column(nullable = false)
    private String value;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
