package com.english;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.data.fieldgroup.BeanFieldGroup;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {
	
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private TextField email = new TextField("Email");
	
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private CustomerService customerService = CustomerService.getInstance();
	private Customer customer;
	private MyUI myUI;
	
	public CustomerForm(MyUI myUI) {
		this.myUI = myUI;
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e->save());
		delete.addClickListener(e->delete());
		
		setSizeUndefined();
		HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(save, delete);
		buttonsHorizontalLayouts.setSpacing(true);
		addComponents(firstName, lastName, email, buttonsHorizontalLayouts);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		BeanFieldGroup.bindFieldsUnbuffered(customer, this);
		
		delete.setVisible(customer.isPersisted());
		setVisible(true);
		firstName.selectAll();
	}
	
	private void save() {
		customerService.save(customer);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		customerService.delete(customer);
		myUI.updateList();
		setVisible(false);
	}
}
