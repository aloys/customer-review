package assignement.customer.review.framework.view.exception;

import assignement.customer.review.framework.view.notification.NotificationManager;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ApplicationErrorHandler extends DefaultErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationErrorHandler.class);

    private static final NotificationManager NOTIFICATION_MANAGER = new NotificationManager();


    private static final ApplicationErrorHandler INSTANCE = new ApplicationErrorHandler( );

    private ApplicationErrorHandler() {
    }

    public static final ApplicationErrorHandler getInstance(){
        return INSTANCE;
    }


    @Override
    public void error(ErrorEvent event) {

        final Throwable exception = event.getThrowable();

        if (logger.isDebugEnabled()) {
            logger.debug(exception.getMessage(), exception);
        }

        if(exception instanceof IllegalArgumentException) {
            NOTIFICATION_MANAGER.showWarnMessage(exception.getMessage());
        }else{
            NOTIFICATION_MANAGER.showErrorMessage(exception.getMessage());
        }


    }
}
