package com.english;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;


public class LogInForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	
	private TextField customerIdentifierTextField = new TextField();
	private TextField passwordTextField = new TextField();
	
	private Button logInButton = new Button("Log me");
	private Button closeButton = new Button("Close me");
	
	private MyUI myUI;
	

	public LogInForm(MyUI myUI) {
		this.myUI = myUI;
		
		initComponents();
		setSizeUndefined();
		setTextFieldsPrompt();
		addComponents(customerIdentifierTextField, passwordTextField, logInButton, closeButton);
	}

	private void setTextFieldsPrompt() {
		customerIdentifierTextField.setInputPrompt("name/email");   //TODO Handle name input
		passwordTextField.setInputPrompt("password");
	}

	private void initComponents() {
		logInButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		logInButton.setClickShortcut(KeyCode.ENTER);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		logInButton.addClickListener(e->logInButtonClick());
		closeButton.addClickListener(e->closeButtonClick());
	}
	
	protected void clearTextFields() {
		customerIdentifierTextField.clear();
		passwordTextField.clear();
	}

	private boolean isLoginTextFieldAndPasswordTextFieldValid() {
		return customerIdentifierTextField.isValid() && passwordTextField.isValid();
	}
	
	private boolean isLoginAndPasswordCorrect(String customerIdentifier, String password) {
		return DBHandler.getSingleCustomer(customerIdentifier, password).isPresent();
	}
	
	protected void handleMainUIWhileLogInButtonClick(String customerIdentifier) {
		myUI.setVisibleReigsterButton(false);
		myUI.setVisibleLogInButton(false);
		myUI.setVisibleLogOutButton(true);
		myUI.setVisibleEditMeButton(true);
		myUI.setLoginStatus("Hello : " + customerIdentifier);
	}
	
	protected void handleLogInFormWhileLogInButtonClickSuccessfulCase(String customerIdentifier) {
		getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, customerIdentifier);
		handleMainUIWhileLogInButtonClick(customerIdentifier);
		clearTextFields();
		setVisible(false);
	}
	
	protected void handleLogInFormWhileLogInButtonClickFailureCase() {
		Notification.show("Incorrect customer identifier or password", Type.WARNING_MESSAGE);
		clearTextFields();
	}
	
	protected void logInButtonClick() {
		if (isLoginTextFieldAndPasswordTextFieldValid()) {		
			String email = customerIdentifierTextField.getValue();
			String password = passwordTextField.getValue();
			
			if(isLoginAndPasswordCorrect(email, password)) {
				handleLogInFormWhileLogInButtonClickSuccessfulCase(email);
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
