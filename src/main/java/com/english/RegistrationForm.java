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
		addComponents(passwordTextField, confirmPasswordTextField, buttonsHorizontalLayouts);
	}

	protected void saveButtonClick() {
		super.saveButtonClick();

		if(DBHandler.checkIfEmailExist(contactMe.getValue())) {
			Notification.show("Email already in use", "", Notification.Type.HUMANIZED_MESSAGE);
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
}
