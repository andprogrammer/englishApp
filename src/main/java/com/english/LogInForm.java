package com.english;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;


public class LogInForm extends FormLayout {

	private TextField login = new TextField("Your email");
	private TextField password = new TextField("Your password");
	
	private Button logInButton = new Button("Log me");
	private Button closeButton = new Button("Close me");
	
	public LogInForm() {
		
		logInButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		//TODO
		//Add listener for login button
		closeButton.addClickListener(e->close());
		
		setSizeUndefined();
		addComponents(login, password, logInButton, closeButton);
	}
	
	protected boolean isLoginCorrect() {
		return true;
	}
	
	protected boolean isPasswordCorrect() {
		return true;
	}
	
	public boolean logIn() {
		
		setVisible(true);
		return true;
	}
	
	private void close() {
		setVisible(false);
	}
}
