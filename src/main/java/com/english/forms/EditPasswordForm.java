package com.english.forms;

import com.english.utils.Contract;
import com.english.view.MainUI;
import com.vaadin.ui.HorizontalLayout;

public class EditPasswordForm extends CustomerForm {

    public EditPasswordForm(MainUI mainUI) {
        super();
        this.mainUI = mainUI;

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

        if (checkContracts()) {
            return false;
        }
        setCustomerValueBasedOnInputPrompt();

        customerService.update(customer);
        mainUI.updateMainGridCustomerList();
        setVisible(false);
        clearInputFields();
        return true;
    }
}
