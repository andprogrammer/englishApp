package com.english.forms;

import com.english.db.DBHandler;
import com.english.Application;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;


public class RegistrationForm extends CustomerForm {

	public RegistrationForm(Application application) {
		super();
		this.application = application;

		super.initComponents();
		setSizeUndefined();

		HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(saveButton, closeButton);
		buttonsHorizontalLayouts.setSpacing(true);

		super.setTextFieldsPrompts();
		addComponents(passwordLabel, passwordTextField, confirmPasswordLabel, confirmPasswordTextField, buttonsHorizontalLayouts);
	}

	protected boolean saveButtonClick() {
		if (false == super.saveButtonClick()) return false;

		if(DBHandler.checkIfNameAlreadyExists(nameTextField.getValue())) {
			Notification.show("This name is already in use", "Change name value", Notification.Type.HUMANIZED_MESSAGE);
			return false;
		}

		if(DBHandler.checkIfContactMeAlreadyExists(contactMeTextField.getValue())) {
			Notification.show("This contact is already in use", "Change contact value", Notification.Type.HUMANIZED_MESSAGE);
			return false;
		}

		if(checkContracts()) { 
			return false;
		}
		setCustomerValueBasedOnInputPrompt();
		
		customerService.save(customer);
		application.updateMainGridCustomerList();
		setVisible(false);
		clearInputFields();
		return true;
	}
}
