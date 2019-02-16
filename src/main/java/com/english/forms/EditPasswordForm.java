package com.english.forms;

import com.english.Application;
import com.english.utils.Contract;
import com.vaadin.ui.HorizontalLayout;

public class EditPasswordForm extends CustomerForm {

    public EditPasswordForm(Application application) {
        super();
        this.application = application;

        super.initComponents();
        setSizeUndefined();

        HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(saveButton, closeButton);
        buttonsHorizontalLayouts.setSpacing(true);

        setLabelsInvisible();

        super.setTextFieldsPrompts();
        setTextFieldsInvisible();

        addComponents(passwordLabel, passwordTextField, confirmPasswordLabel, confirmPasswordTextField, buttonsHorizontalLayouts);
    }

    private void setTextFieldsInvisible() {
        nameTextField.setVisible(false);
        skypeTextField.setVisible(false);
        contactMeTextField.setVisible(false);
        languageLevelComboBox.setVisible(false);
    }

    protected boolean checkContracts() {
        return Contract.isNull(passwordTextField.getValue(), "password");
    }

    void setCustomerValueBasedOnInputPrompt() {
        customer.setPassword(passwordTextField.getValue());
    }

    protected boolean saveButtonClick() {
        if (false == super.saveButtonClick()) return false;

        if(checkContracts()) {
            return false;
        }
        setCustomerValueBasedOnInputPrompt();

        customerService.update(customer);
        application.updateMainGridCustomerList();
        setVisible(false);
        clearInputFields();
        return true;
    }
}
