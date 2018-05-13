package com.english;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.TextField;


@SuppressWarnings("serial")
public class RegistrationForm extends CustomerForm {
	
	private TextField passwordTextField = new TextField();
	private TextField confirmPasswordTextField = new TextField();
	
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

		setTextFieldsPrompts();
		addComponents(passwordTextField, confirmPasswordTextField, buttonsHorizontalLayouts);
	}

	private void setTextFieldsPrompts() {
		passwordTextField.setInputPrompt("password");
		confirmPasswordTextField.setInputPrompt("confirm password");
	}

	private void initComponents() {
		clearRegistrationFields();
		
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		saveButton.addClickListener(e->saveButtonClick());
		closeButton.addClickListener(e->closeButtonClick());
	}
	
	private void saveButtonClick() {
		if(false == checkIfPasswordAndPasswordConfirmationAreTheSame()) {
			return;
		}
		if(checkContracts()) { 
			return; 
		}
		setCustomerValue();
		
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
		clearRegistrationFields();
	}
	
	void setCustomerValue() {
		//TODO birthday field
		customer.setFirstName(firstName.getValue());
		customer.setLastName(lastName.getValue());
		customer.setCountry(country.getValue());
		customer.setEnglishLevel((int) englishLevel.getValue());
		customer.setSkype(skype.getValue());
		customer.setSex(GlobalFunctions.convertBooleanToSex((boolean) sex.getValue()));
		customer.setEmail(email.getValue());
		customer.setDescription(description.getValue());
		customer.setPassword(passwordTextField.getValue());
	}
	
	protected boolean checkContracts() {
		return Contract.isNull(firstName.getValue(), "first name") ||
			   Contract.isNull(lastName.getValue(), "last name") ||
			   Contract.isNull(country, "country") ||
			   Contract.isNull(englishLevel.getValue(), "english level") ||
			   Contract.isNull(skype.getValue(), "skype") ||
			   Contract.isNull(sex.getValue(), "sex") ||
			   Contract.isNull(email.getValue(), "email") ||
			   //description could be empty
			   Contract.isNull(passwordTextField.getValue(), "password");
	}
	
	protected boolean checkIfPasswordAndPasswordConfirmationAreTheSame() {
		if(false == passwordTextField.getValue().equals(confirmPasswordTextField.getValue())) {
			Notification.show("Different passwords", "", Notification.Type.HUMANIZED_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void closeButtonClick() {
		setVisible(false);
	}
	
	protected void clearRegistrationFields() {
		super.clearRegistrationFields();
		passwordTextField.clear();
		confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
	}
}
