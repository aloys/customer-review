package customer.review.application.configuration;

import customer.review.framework.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by amazimpaka on 2018-03-02
 */
@Service
public class ConfigurationService extends AbstractService<Configuration> {

    @Autowired
    private ConfigurationRepository configurationRepository;


    private static final String MAX_RATING_KEY = "review.max.rating";

    private static final String MIN_RATING_KEY = "review.min.rating";

    private static final String FORBIDDEN_CONTENT_WORDS = "review.forbidden.content.words";


    @PostConstruct
    public void initialize(){
        setRepository(configurationRepository);
        super.initialize();
    }

    public void createConfiguration(String key, String value){
        Configuration configuration = configurationRepository.findByKey(key);
        if(configuration == null){
            configuration = new Configuration();
        }
        configuration.setKey(key);
        configuration.setValue(value);
        configurationRepository.save(configuration);
    }

    public int getMinRating(){
        Configuration configuration = configurationRepository.findByKey(MIN_RATING_KEY);
        if(configuration == null){
            throw new IllegalStateException("Undefined min rating configuration");
        }
        return Integer.parseInt(configuration.getValue());
    }

    public int getMaxRating(){
        Configuration configuration = configurationRepository.findByKey(MAX_RATING_KEY);
        if(configuration == null){
            throw new IllegalStateException("Undefined max rating configuration");
        }
        return Integer.parseInt(configuration.getValue());
    }

    public List<String> getForbiddenContentWords(){
        Configuration configuration = configurationRepository.findByKey(FORBIDDEN_CONTENT_WORDS);
        if(configuration == null){
            throw new IllegalStateException("Undefined max rating configuration");
        }

        if(configuration.getValue() == null){
            return Collections.emptyList();
        }
        return Arrays.asList(configuration.getValue().split(","));
    }

    @Override
    protected void initializeData() {
        if (enableDatanItializion) {

            repository.deleteAll();

            createConfiguration(MAX_RATING_KEY,String.valueOf(5));
            createConfiguration(MIN_RATING_KEY,String.valueOf(1));
            createConfiguration(FORBIDDEN_CONTENT_WORDS,"test,doughnut");

            initialized.set(true);
        }
    }
}
