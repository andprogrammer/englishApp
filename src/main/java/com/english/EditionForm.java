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

    private boolean newEmailSameAsLogged(String email) {
        String sessionEmail = getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE).toString();
        return sessionEmail.equals(email);
    }

    protected void saveButtonClick() {
        super.saveButtonClick();
        String email = contactMe.getValue();
        if(DBHandler.checkIfEmailExist(email) && false == newEmailSameAsLogged(email) ) {
            Notification.show("Email already in use", "", Notification.Type.HUMANIZED_MESSAGE);
            return;
        }
        if(checkContracts()) {
            return;
        }
        setCustomerValue();

        customerService.update(customer);
        myUI.updateCustomers();
        myUI.updateList();
        setVisible(false);
        clearRegistrationFields();
    }
}
