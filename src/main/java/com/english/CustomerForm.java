package com.english;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {
	
	protected TextField nameTextField = new TextField();
	protected TextField skypeTextField = new TextField();
	protected TextField contactMe = new TextField();
	protected ComboBox englishLevelComboBox = new ComboBox();
	protected TextField passwordTextField = new TextField();
	protected TextField confirmPasswordTextField = new TextField();
	protected Button saveButton = new Button("Save me");
	protected Button closeButton = new Button("Close me");

	protected Customer customer;
	protected CustomerService customerService = CustomerService.getInstance();
	protected MyUI myUI;

	
	public CustomerForm() {
		setTextFieldsPrompts();

		englishLevelComboBox.addItem(1);
		englishLevelComboBox.addItem(2);
		englishLevelComboBox.addItem(3);
		englishLevelComboBox.addItem(4);
		englishLevelComboBox.addItem(5);
		englishLevelComboBox.addItem(6);
		englishLevelComboBox.setNullSelectionAllowed(false);
		
		setSizeUndefined();
		addComponents(nameTextField, skypeTextField, contactMe, englishLevelComboBox);
	}

	protected void initComponents() {
		clearRegistrationFields();
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		saveButton.addClickListener(e->saveButtonClick());
		closeButton.addClickListener(f->closeButtonClick());
	}

	protected void saveButtonClick() {
		if (false == checkIfPasswordAndPasswordConfirmationAreTheSame()) {
			return;
		}
	}

	void setCustomerValue() {
		customer.setName(nameTextField.getValue());
		customer.setSkype(skypeTextField.getValue());
		customer.setContactMe(contactMe.getValue());
		customer.setEnglishLevel((int) englishLevelComboBox.getValue());
		customer.setPassword(passwordTextField.getValue());
	}

	protected boolean checkContracts() {
		return Contract.isNull(nameTextField.getValue(), "name") ||
				Contract.isNull(skypeTextField.getValue(), "skype") ||
				Contract.isNull(contactMe.getValue(), "contact me") ||
				Contract.isNull(englishLevelComboBox.getValue(), "english level") ||
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

	protected void setTextFieldsPrompts() {
		nameTextField.setInputPrompt("name");
		skypeTextField.setInputPrompt("skype");
		contactMe.setInputPrompt("contact [e.g. mail]");
		englishLevelComboBox.setInputPrompt("English level");
		passwordTextField.setInputPrompt("password");
		confirmPasswordTextField.setInputPrompt("confirm password");
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		initializeGUIelements(customer);
		setVisible(true);
	}

	protected void initializeGUIelements(Customer customer) {
		nameTextField.setValue(customer.getName());
		skypeTextField.setValue(customer.getSkype());
		contactMe.setValue(customer.getContactMe());
		englishLevelComboBox.setValue(customer.getEnglishLevel());
	}
	
	protected void clearRegistrationFields() {
		nameTextField.clear();
		skypeTextField.clear();
		contactMe.clear();
		englishLevelComboBox.clear();
		passwordTextField.clear();
		confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
	}
}
