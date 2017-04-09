package com.english;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public class RegistrationForm extends CustomerForm {
	
	private PasswordField password = new PasswordField("password");
	private PasswordField passwordConfirmation = new PasswordField("passwordConfirmation");
	
	private Button save = new Button("Save me");
	private Button close = new Button("Close me");
	
	private CustomerService customerService = CustomerService.getInstance();
	//private Customer customer;
	private MyUI myUI;
	
	public RegistrationForm(MyUI myUI) {
		this.myUI = myUI;
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		//save.setClickShortcut(KeyCode.ENTER);	//Consider to uncomment
		close.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		save.addClickListener(e->save());
		close.addClickListener(e->close());
		
		setSizeUndefined();
		HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(save, close);
		buttonsHorizontalLayouts.setSpacing(true);
		addComponents(password, passwordConfirmation, buttonsHorizontalLayouts);
	}

	private void save() {
		passwordConfirmation.setValue("");//This field is not store in Customer and it is not refreshing value
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
	}
	
	private void close() {
		setVisible(false);
	}
}
