package com.english;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;


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
	
	//private Button save = new Button("Save");
	//private Button delete = new Button("Delete");
	
	//private CustomerService customerService = CustomerService.getInstance();
	protected Customer customer;
	//private MyUI myUI;
	
	public CustomerForm() {
		//this.myUI = myUI;
		
		//save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		//save.setClickShortcut(KeyCode.ENTER);
		
		//save.addClickListener(e->save());
		//delete.addClickListener(e->delete());

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
		//HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(save, delete);
		//buttonsHorizontalLayouts.setSpacing(true);
		addComponents(firstNameTextField, lastNameTextField, countryTextField, englishLevelComboBox, skypeTextField, genderComboBox, emailTextField, descriptionTextArea);
	}

	private void setTextFieldsPrompts() {
		firstNameTextField.setInputPrompt("First name");
		lastNameTextField.setInputPrompt("Last name");
		countryTextField.setInputPrompt("Country");
		englishLevelComboBox.setInputPrompt("English level");
		skypeTextField.setInputPrompt("Skype");
		genderComboBox.setInputPrompt("Gender");
		emailTextField.setInputPrompt("Email");
		descriptionTextArea.setInputPrompt("Description");
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
	}
	
	/*private void save() {
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
	}*/
	
	/*private void delete() {
		customerService.delete(customer);
		myUI.updateList();
		setVisible(false);
	}*/
}
