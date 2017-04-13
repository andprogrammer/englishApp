package com.english;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public class RegistrationForm extends CustomerForm {
	
	private PasswordField passwordPasswordField = new PasswordField("password");
	private PasswordField passwordConfirmationPasswordField = new PasswordField("passwordConfirmation");
	
	private Button saveButton = new Button("Save me");
	private Button closeButton = new Button("Close me");
	
	private CustomerService customerService = CustomerService.getInstance();
	private MyUI myUI;
	
	
	public RegistrationForm(MyUI myUI) {
		this.myUI = myUI;
		
		initComponents();
		setSizeUndefined();
		HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(saveButton, closeButton);
		buttonsHorizontalLayouts.setSpacing(true);
		addComponents(passwordPasswordField, passwordConfirmationPasswordField, buttonsHorizontalLayouts);
	}

	private void initComponents() {
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		//saveButton.setClickShortcut(KeyCode.ENTER);	//Consider to uncomment
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		saveButton.addClickListener(e->saveButtonClick());
		closeButton.addClickListener(e->closeButtonClick());
	}
	
	private void saveButtonClick() {
		passwordConfirmationPasswordField.setValue("");//This field is not store in Customer and it is not refreshing value
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
	}
	
	private void closeButtonClick() {
		setVisible(false);
	}
}
