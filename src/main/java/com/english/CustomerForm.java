package com.english;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.data.fieldgroup.BeanFieldGroup;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {
	
	protected TextField firstName = new TextField();
	protected TextField lastName = new TextField();
	protected TextField country = new TextField();
	protected ComboBox englishLevel = new ComboBox();
	protected TextField skype = new TextField();
	protected ComboBox sex = new ComboBox();
	protected TextField email = new TextField();
	protected TextArea description = new TextArea();
	
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

		englishLevel.addItem(1);
		englishLevel.addItem(2);
		englishLevel.addItem(3);
		englishLevel.addItem(4);
		englishLevel.addItem(5);
		englishLevel.addItem(6);
		englishLevel.setNullSelectionAllowed(false);
		
		sex.addItem(true);
		sex.addItem(false);
		sex.setItemCaption(true, "Male");
		sex.setItemCaption(false, "Female");
		sex.setNullSelectionAllowed(false);

		description.setRows(10);
		description.setSizeFull();
		
		setSizeUndefined();
		//HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(save, delete);
		//buttonsHorizontalLayouts.setSpacing(true);
		addComponents(firstName, lastName, country, englishLevel, skype, sex, email, description);
	}

	private void setTextFieldsPrompts() {
		firstName.setInputPrompt("First name");
		lastName.setInputPrompt("Last name");
		country.setInputPrompt("Country");
		englishLevel.setInputPrompt("English level");
		skype.setInputPrompt("Skype");
		sex.setInputPrompt("Sex");
		email.setInputPrompt("Email");
		description.setInputPrompt("Description");
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		initializeGUIelements(customer);
		setVisible(true);
	}

	protected void initializeGUIelements(Customer customer) {
		firstName.setValue(customer.getFirstName());
		lastName.setValue(customer.getLastName());
		country.setValue(customer.getCountry());
		englishLevel.setValue(customer.getEnglishLevel());
		skype.setValue(customer.getSkype());
		sex.setValue(GlobalFunctions.convertSexToBoolean(customer.getSex()));
		email.setValue(customer.getEmail());
		description.setValue(customer.getDescription());
	}
	
	protected void clearRegistrationFields() {
		firstName.clear();
		lastName.clear();
		country.clear();
		englishLevel.clear();
		skype.clear();
		sex.clear();
		email.clear();
		description.clear();
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
