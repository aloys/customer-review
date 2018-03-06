package customer.review.application.configuration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {

    Configuration findByKey(String key);
}
