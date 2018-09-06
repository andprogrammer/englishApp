package com.english;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {
	
	protected TextField firstNameTextField = new TextField();
	protected TextField lastNameTextField = new TextField();
	protected TextField countryTextField = new TextField();
	protected ComboBox englishLevelComboBox = new ComboBox();
	protected TextField skypeTextField = new TextField();
	protected ComboBox genderComboBox = new ComboBox();
	protected TextField emailTextField = new TextField();
	protected TextArea descriptionTextArea = new TextArea();
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

		genderComboBox.addItem(true);
		genderComboBox.addItem(false);
		genderComboBox.setItemCaption(true, "Male");
		genderComboBox.setItemCaption(false, "Female");
		genderComboBox.setNullSelectionAllowed(false);

		descriptionTextArea.setRows(10);
		descriptionTextArea.setSizeFull();
		
		setSizeUndefined();
		addComponents(firstNameTextField, lastNameTextField, countryTextField, englishLevelComboBox, skypeTextField, genderComboBox, emailTextField, descriptionTextArea);
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
		//TODO birthday field
		customer.setFirstName(firstNameTextField.getValue());
		customer.setLastName(lastNameTextField.getValue());
		customer.setCountry(countryTextField.getValue());
		customer.setEnglishLevel((int) englishLevelComboBox.getValue());
		customer.setSkype(skypeTextField.getValue());
		customer.setGender(GlobalFunctions.convertBooleanToGender((boolean) genderComboBox.getValue()));
		customer.setEmail(emailTextField.getValue());
		customer.setDescription(descriptionTextArea.getValue());
		customer.setPassword(passwordTextField.getValue());
	}

	protected boolean checkContracts() {
		return Contract.isNull(firstNameTextField.getValue(), "first name") ||
				Contract.isNull(lastNameTextField.getValue(), "last name") ||
				Contract.isNull(countryTextField, "country") ||
				Contract.isNull(englishLevelComboBox.getValue(), "english level") ||
				Contract.isNull(skypeTextField.getValue(), "skype") ||
				Contract.isNull(genderComboBox.getValue(), "gender") ||
				Contract.isNull(emailTextField.getValue(), "email") ||
				//description could be empty
				Contract.isNull(passwordTextField.getValue(), "password") ||
				Contract.incorrectEmail(emailTextField.getValue());
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
		firstNameTextField.setInputPrompt("First name");
		lastNameTextField.setInputPrompt("Last name");
		countryTextField.setInputPrompt("Country");
		englishLevelComboBox.setInputPrompt("English level");
		skypeTextField.setInputPrompt("Skype");
		genderComboBox.setInputPrompt("Gender");
		emailTextField.setInputPrompt("Email");
		descriptionTextArea.setInputPrompt("Description");
		passwordTextField.setInputPrompt("password");
		confirmPasswordTextField.setInputPrompt("confirm password");
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		initializeGUIelements(customer);
		setVisible(true);
	}

	protected void initializeGUIelements(Customer customer) {
		firstNameTextField.setValue(customer.getFirstName());
		lastNameTextField.setValue(customer.getLastName());
		countryTextField.setValue(customer.getCountry());
		englishLevelComboBox.setValue(customer.getEnglishLevel());
		skypeTextField.setValue(customer.getSkype());
		genderComboBox.setValue(GlobalFunctions.convertGenderToBoolean(customer.getGender()));
		emailTextField.setValue(customer.getEmail());
		descriptionTextArea.setValue(customer.getDescription());
	}
	
	protected void clearRegistrationFields() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		countryTextField.clear();
		englishLevelComboBox.clear();
		skypeTextField.clear();
		genderComboBox.clear();
		emailTextField.clear();
		descriptionTextArea.clear();
		passwordTextField.clear();
		confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
	}
}
