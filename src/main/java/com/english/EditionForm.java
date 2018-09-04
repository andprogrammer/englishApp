package com.english;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class EditionForm extends CustomerForm {

    protected TextField passwordTextField = new TextField();
    protected TextField confirmPasswordTextField = new TextField();

    protected Button saveButton = new Button("Save me");
    private Button closeButton = new Button("Close me");

    protected CustomerService customerService = CustomerService.getInstance();
    protected MyUI myUI;

    public EditionForm(MyUI myUI) {
        super();

        this.myUI = myUI;

        initComponents();
        setSizeUndefined();

        HorizontalLayout buttonsHorizontalLayouts = new HorizontalLayout(saveButton, closeButton);
        buttonsHorizontalLayouts.setSpacing(true);

        setTextFieldsPrompts();
        addComponents(passwordTextField, confirmPasswordTextField, buttonsHorizontalLayouts);
    }

    private void setTextFieldsPrompts() {
//		super.setTextFieldsPrompts();
        passwordTextField.setInputPrompt("password");
        confirmPasswordTextField.setInputPrompt("confirm password");
    }

    private void initComponents() {
        clearRegistrationFields();

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        saveButton.addClickListener(e->saveButtonClick());
        closeButton.addClickListener(f->closeButtonClick());
    }

    private boolean newEmailSameAsLogged(String email) {
        String sessionEmail = getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE).toString();
        return sessionEmail.equals(email);
    }

    private void saveButtonClick() {
        if(false == checkIfPasswordAndPasswordConfirmationAreTheSame()) {
            return;
        }
        String email = emailTextField.getValue();
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

    void setCustomerValue() {
        //TODO birthday field
        customer.setFirstName(firstNameTextField.getValue());
        customer.setLastName(lastNameTextField.getValue());
        customer.setCountry(countryTextField.getValue());
        customer.setEnglishLevel((int) englishLevelComboBox.getValue());
        customer.setSkype(skypeTextField.getValue());
        customer.setGender(GlobalFunctions.convertBooleanToGender((boolean) genderComboBox.getValue()));
        customer.setEmail(emailTextField.getValue());
        customer.setDescription(descriptionTextArea.getValue());
        customer.setPassword(passwordTextField.getValue());
    }

    protected boolean checkContracts() {
        return Contract.isNull(firstNameTextField.getValue(), "first name") ||
                Contract.isNull(lastNameTextField.getValue(), "last name") ||
                Contract.isNull(countryTextField, "country") ||
                Contract.isNull(englishLevelComboBox.getValue(), "english level") ||
                Contract.isNull(skypeTextField.getValue(), "skype") ||
                Contract.isNull(genderComboBox.getValue(), "gender") ||
                Contract.isNull(emailTextField.getValue(), "email") ||
                //description could be empty
                Contract.isNull(passwordTextField.getValue(), "password");
    }

    protected boolean checkIfPasswordAndPasswordConfirmationAreTheSame() {
        if(false == passwordTextField.getValue().equals(confirmPasswordTextField.getValue())) {
            Notification.show("Different passwords", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }
        return true;
    }

    private void closeButtonClick() {
        setVisible(false);
    }

    protected void clearRegistrationFields() {
        super.clearRegistrationFields();
        passwordTextField.clear();
        confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
    }
}
