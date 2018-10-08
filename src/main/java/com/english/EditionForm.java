package com.english;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;


public class EditionForm extends CustomerForm {

    public EditionForm(MyUI myUI) {
        super();
        this.myUI = myUI;

        super.initComponents();
        setSizeUndefined();

        HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(saveButton, closeButton);
        buttonsHorizontalLayouts.setSpacing(true);

        super.setTextFieldsPrompts();
        addComponents(passwordTextField, confirmPasswordTextField, buttonsHorizontalLayouts);
    }

    private boolean nameSameAsLoggedIn(String name) {   //session label ALWAYS set to customer name!
        Object sessionAttribute = getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
        if(sessionAttribute != null) return sessionAttribute.toString().toLowerCase().equals(name.toLowerCase());
        return false;
    }

    protected boolean saveButtonClick() {
        if (false == super.saveButtonClick()) return false;
        String name = this.nameTextField.getValue();
        String contactMe = this.contactMeTextField.getValue();

        if(DBHandler.checkIfNameAlreadyExists(name) && false == nameSameAsLoggedIn(name)) {
            Notification.show("Name already in use", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }

        if(DBHandler.checkIfContactMeAlreadyExists(contactMe) && false == customer.getContactMe().equals(contactMe)) {
            Notification.show("Contact already in use", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }

        if(checkContracts()) {
            return false;
        }
        setCustomerValue();

        getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, name);
        myUI.setLoginStatusLabel(name);

        customerService.update(customer);
        myUI.updateCustomers();
        myUI.updateList();
        setVisible(false);
        clearRegistrationFields();
        return true;
    }
}
