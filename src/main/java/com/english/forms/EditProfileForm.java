package com.english.forms;

import com.english.db.DBHandler;
import com.english.utils.Contract;
import com.english.utils.SessionAttributes;
import com.english.view.MainUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class EditProfileForm extends CustomerForm {

    protected Button editPasswordButton = new Button("Edit Password");

    public EditProfileForm(MainUI mainUI) {
        super();
        this.mainUI = mainUI;

        initComponents();
        setSizeUndefined();

        HorizontalLayout buttonsHorizontalLayout = new HorizontalLayout(saveButton, closeButton);
        buttonsHorizontalLayout.setSpacing(true);

        VerticalLayout buttonVerticalLayout = new VerticalLayout();
        buttonVerticalLayout.addComponent(editPasswordButton);
        buttonVerticalLayout.addComponent(buttonsHorizontalLayout);
        buttonVerticalLayout.setSpacing(true);

        super.setTextFieldsPrompts();
        addComponents(buttonVerticalLayout);
    }

    protected void initComponents() {
        super.initComponents();
        editPasswordButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editPasswordButton.addClickListener(f -> editPasswordButtonClick());
        mainUI.editPasswordForm.setVisible(false);
    }

    private void editPasswordButtonClick() {
        setVisible(false);
        mainUI.editPasswordForm.setVisible(true);
        mainUI.editPasswordForm.setCustomer(customer);
    }

    private boolean nameSameAsLoggedIn(String name) {   //session label ALWAYS set to customer name!
        Object sessionAttribute = getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
        if (sessionAttribute != null) return sessionAttribute.toString().toLowerCase().equals(name.toLowerCase());
        return false;
    }

    protected boolean checkContracts() {
        return Contract.isNull(nameTextField.getValue(), "name") ||
                Contract.isNull(skypeTextField.getValue(), "skype") ||
                Contract.isNull(contactMeTextField.getValue(), "contact me") ||
                Contract.isNull(languageLevelComboBox.getValue(), "language level");
    }

    protected boolean saveButtonClick() {
        String name = this.nameTextField.getValue();
        String contactMe = this.contactMeTextField.getValue();

        if (DBHandler.checkIfNameAlreadyExists(name) && false == nameSameAsLoggedIn(name)) {
            Notification.show("Name already in use", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }

        if (DBHandler.checkIfContactMeAlreadyExists(contactMe) && false == customer.getContactMe().equals(contactMe)) {
            Notification.show("Contact already in use", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }

        if (checkContracts()) {
            return false;
        }
        setCustomerValueBasedOnInputPrompt();

        getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, name);
        mainUI.setLoginStatusLabel(name);

        customerService.update(customer);
        mainUI.updateCustomers();
        mainUI.updateMainGridCustomerList();
        setVisible(false);
        clearInputFields();
        return true;
    }
}
