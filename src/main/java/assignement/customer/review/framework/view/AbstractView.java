package assignement.customer.review.framework.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by amazimpaka on 2018-03-03
 */
public class AbstractView  extends VerticalLayout implements View {

    private static final String INFO_TITLE = "Information";

    private static final String WARNING_TITLE = "Warning";

    private static final String ERROR_TITLE = "Error";

    private final boolean htmlContentAllowed = true;


    public void showInfoMessage(String message) {
        show(INFO_TITLE, message, Notification.Type.HUMANIZED_MESSAGE);
    }

    public void showTrayMessage(String message) {
        show(INFO_TITLE, message, Notification.Type.TRAY_NOTIFICATION);
    }

    public void showWarnMessage(String message) {
        show(WARNING_TITLE, message, Notification.Type.WARNING_MESSAGE);
    }

    public void showErrorMessage(String message) {
        show(ERROR_TITLE, message, Notification.Type.ERROR_MESSAGE);
    }


    private void show(String caption, String description, Notification.Type type) {
        new Notification(caption, description, type,htmlContentAllowed).show(Page.getCurrent());
    }


    protected static Button createButton(String caption, VaadinIcons icon, Button.ClickListener clickListener){
        final Button button = new Button();

        if(caption != null){
            button.setCaption(caption);
        }

        if(icon != null){
            button.setIcon(icon);
            button.setDescription(caption);
            button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        }

        if(clickListener != null){
            button.addClickListener(clickListener);
        }
        return button;
    }

}
