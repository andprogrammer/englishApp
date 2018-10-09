package com.english;

import com.vaadin.ui.HorizontalLayout;

public class EditPasswordForm extends CustomerForm {

    public EditPasswordForm(MyUI myUI) {
        super();
        this.myUI = myUI;

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
        englishLevelComboBox.setVisible(false);
    }

    protected boolean checkContracts() {
        return Contract.isNull(passwordTextField.getValue(), "password");
    }

    void setCustomerValue() {
        customer.setPassword(passwordTextField.getValue());
    }

    protected boolean saveButtonClick() {
        if (false == super.saveButtonClick()) return false;

        if(checkContracts()) {
            return false;
        }
        setCustomerValue();

        customerService.update(customer);
        myUI.updateList();
        setVisible(false);
        clearRegistrationFields();
        return true;
    }
}
