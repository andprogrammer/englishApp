package com.english;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;


public class LogInForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	
	private TextField loginTextField = new TextField("email");
	private TextField passwordTextField = new TextField("password");
	
	private Button logInButton = new Button("Log me");
	private Button closeButton = new Button("Close me");
	
	private MyUI myUI;
	
	
	public LogInForm(MyUI myUI) {
		this.myUI = myUI;
		
		initComponents();
		setSizeUndefined();
		addComponents(loginTextField, passwordTextField, logInButton, closeButton);
	}
	
	private void initComponents() {
		logInButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		logInButton.setClickShortcut(KeyCode.ENTER);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		logInButton.addClickListener(e->logInButtonClick());
		closeButton.addClickListener(e->closeButtonClick());
	}
	
	protected void clearTextFields() {
		loginTextField.clear();
		passwordTextField.clear();
	}
	
	protected boolean isLoginCorrect() {
		//TODO
		return true;
	}
	
	protected boolean isPasswordCorrect() {
		//TODO
		return true;
	}
	
	private boolean isLoginTextFieldAndPasswordTextFieldValid() {
		/* TODO
		 * if(loginTextField.isValid() == false || passwordTextField.isValid() == false) {
			return false;
		}*/
		return true;
	}
	
	private boolean isLoginAndPasswordCorrect(String login, String password) {
		//TODO Add database request
		return login.equals("test") && password.equals("test2");
	}
	
	protected void handleMainUIWhileLogInButtonClick(String login) {
		myUI.setVisibleReigsterButton(false);
		myUI.setVisibleLogInButton(false);
		myUI.setVisibleLogOutButton(true);
		myUI.setLoginStatus("Hello : " + login);
	}
	
	protected void handleLogInFormWhileLogInButtonClickSuccessfulCase(String login) {
		getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, login);
		handleMainUIWhileLogInButtonClick(login);
		clearTextFields();
		setVisible(false);
	}
	
	protected void handleLogInFormWhileLogInButtonClickFailureCase() {
		passwordTextField.focus();
		passwordTextField.clear();		
	}
	
	protected void logInButtonClick() {
		if (isLoginTextFieldAndPasswordTextFieldValid()) {		
			String login = loginTextField.getValue();
			String password = passwordTextField.getValue();
			
			if(isLoginAndPasswordCorrect(login, password)) {
				handleLogInFormWhileLogInButtonClickSuccessfulCase(login);
			}
		} else {
			handleLogInFormWhileLogInButtonClickFailureCase();
		}
	}
	
	private void closeButtonClick() {
		setVisible(false);
	}
	
	public boolean showLogInFormOnButtonClick() {
		loginTextField.focus();
		setVisible(true);
		return true;
	}
}
