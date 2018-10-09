package com.english;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;


public class RegistrationForm extends CustomerForm {

	public RegistrationForm(MyUI myUI) {
		super();
		this.myUI = myUI;

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
		setCustomerValue();
		
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
		clearInputFields();
		return true;
	}
}
