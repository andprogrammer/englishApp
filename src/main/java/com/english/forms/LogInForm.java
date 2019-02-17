package com.english.forms;

import com.english.Application;
import com.english.db.DBHandler;
import com.english.model.Customer;
import com.english.utils.SessionAttributes;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Optional;


public class LogInForm extends FormLayout {

    private static final long serialVersionUID = 1L;

    private Label nameEmailLabel = new Label("name/email");
    private TextField logInTextField = new TextField();
    private Label passwordLabel = new Label("password");
    private PasswordField passwordTextField = new PasswordField();

    private Button logInButton = new Button("Log me");
    private Button closeButton = new Button("Close me");

    private Application application;


    public LogInForm(Application application) {
        this.application = application;

        initComponents();
        setSizeUndefined();
        addComponents(nameEmailLabel, logInTextField, passwordLabel, passwordTextField, logInButton, closeButton);
    }

    private void initComponents() {
        logInButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        logInButton.setClickShortcut(KeyCode.ENTER);
        closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        logInButton.addClickListener(e -> logInButtonClick());
        closeButton.addClickListener(e -> closeButtonClick());
    }

    protected void clearTextFields() {
        logInTextField.clear();
        passwordTextField.clear();
    }

    private boolean isLoginTextFieldAndPasswordTextFieldValid() {
        return logInTextField.isValid() && passwordTextField.isValid();
    }

    private boolean isLogInInputAndPasswordCorrect(String logInInput, String password) {
        return DBHandler.getSingleCustomerViaContactMe(logInInput, password).isPresent() || DBHandler.getSingleCustomerViaName(logInInput, password).isPresent();
    }

    protected void handleMainUIWhileLogInButtonClick() {
        application.setVisibleRegisterButton(false);
        application.setVisibleLogInButton(false);
        application.setVisibleLogOutButton(true);
        application.setVisibleEditMeButton(true);
    }

    protected void handleLogInFormWhileLogInButtonClickSuccessfulCase(String logInInput) {
        Optional<Customer> customer_optional = DBHandler.getSingleCustomerViaContactMe(logInInput);
        if (customer_optional.isPresent()) {
            handleSuccessfulLogIn(customer_optional);
        } else {
            customer_optional = DBHandler.getSingleCustomerViaName(logInInput);
            if (customer_optional.isPresent()) {
                handleSuccessfulLogIn(customer_optional);
            }
        }
    }

    private void handleSuccessfulLogIn(Optional<Customer> customer_optional) {
        getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, customer_optional.get().getName());
        application.setLoginStatusLabel(customer_optional.get().getName());
        handleMainUIWhileLogInButtonClick();
        clearTextFields();
        setVisible(false);
    }

    protected void handleLogInFormWhileLogInButtonClickFailureCase() {
        Notification.show("Incorrect customer login or password", Type.WARNING_MESSAGE);
        clearTextFields();
    }

    protected void logInButtonClick() {
        if (isLoginTextFieldAndPasswordTextFieldValid()) {
            String logInInput = logInTextField.getValue();
            String password = passwordTextField.getValue();

            if (isLogInInputAndPasswordCorrect(logInInput, password)) {
                handleLogInFormWhileLogInButtonClickSuccessfulCase(logInInput);
                return;
            }
        }
        handleLogInFormWhileLogInButtonClickFailureCase();
    }

    private void closeButtonClick() {
        setVisible(false);
        clearTextFields();
    }

    public void showLogInFormOnButtonClick() {
        setVisible(true);
    }
}
