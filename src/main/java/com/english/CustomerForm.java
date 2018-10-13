package com.english;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {
	
	protected TextField nameTextField = new TextField();
	protected TextField skypeTextField = new TextField();
	protected TextField contactMeTextField = new TextField();
	protected ComboBox englishLevelComboBox = new ComboBox();
	protected PasswordField passwordTextField = new PasswordField();
	protected PasswordField confirmPasswordTextField = new PasswordField();
	protected Label nameLabel = new Label("name");
	protected Label skypeLabel = new Label("skype");
	protected Label contactLabel = new Label("contact");
	protected Label englishLevelLabel = new Label("english level");
	protected Label passwordLabel = new Label("password");
	protected Label confirmPasswordLabel = new Label("confirm password");
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
		addComponents(nameLabel, nameTextField, skypeLabel, skypeTextField, contactLabel, contactMeTextField, englishLevelLabel, englishLevelComboBox);
	}

	protected void initComponents() {
		clearInputFields();
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		saveButton.addClickListener(e->saveButtonClick());
		closeButton.addClickListener(f->closeButtonClick());
	}

	protected boolean saveButtonClick() {
		return checkIfPasswordAndPasswordConfirmationAreTheSame();
	}

	void setCustomerValueBasedOnInputPrompt() {
		customer.setName(nameTextField.getValue());
		customer.setSkype(skypeTextField.getValue());
		customer.setContactMe(contactMeTextField.getValue());
		customer.setEnglishLevel((int) englishLevelComboBox.getValue());
		customer.setPassword(passwordTextField.getValue());
	}

	protected void setLabelsInvisible() {
		nameLabel.setVisible(false);
		skypeLabel.setVisible(false);
		contactLabel.setVisible(false);
		englishLevelLabel.setVisible(false);
	}

	protected boolean checkContracts() {
		return Contract.isNull(nameTextField.getValue(), "name") ||
				Contract.isNull(skypeTextField.getValue(), "skype") ||
				Contract.isNull(contactMeTextField.getValue(), "contact me") ||
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
		contactMeTextField.setInputPrompt("contact [e.g. mail]");
		englishLevelComboBox.setInputPrompt("english level");
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		initializeGUIelements(customer);
		setVisible(true);
	}

	protected void initializeGUIelements(Customer customer) {
		nameTextField.setValue(customer.getName());
		skypeTextField.setValue(customer.getSkype());
		contactMeTextField.setValue(customer.getContactMe());
		englishLevelComboBox.setValue(customer.getEnglishLevel());
		String customerPassword = customer.getPassword();
		passwordTextField.setValue(customerPassword);
		confirmPasswordTextField.setValue(customerPassword);
	}
	
	protected void clearInputFields() {
		nameTextField.clear();
		skypeTextField.clear();
		contactMeTextField.clear();
		englishLevelComboBox.clear();
		passwordTextField.clear();
		confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
	}
}
