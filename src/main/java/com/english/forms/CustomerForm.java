package com.english.forms;

import com.english.Application;
import com.english.model.Customer;
import com.english.service.CustomerService;
import com.english.utils.Contract;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import static com.english.Globals.*;


@SuppressWarnings("serial")
public class CustomerForm extends FormLayout {

    protected TextField nameTextField = new TextField();
    protected TextField skypeTextField = new TextField();
    protected TextField contactMeTextField = new TextField();
    protected ComboBox languageComboBox = new ComboBox();
    protected ComboBox languageLevelComboBox = new ComboBox();
    protected PasswordField passwordTextField = new PasswordField();
    protected PasswordField confirmPasswordTextField = new PasswordField();
    protected Label nameLabel = new Label("name");
    protected Label skypeLabel = new Label("skype");
    protected Label contactLabel = new Label("contact");
    protected Label languageLabel = new Label("language");
    protected Label languageLevelLabel = new Label("language level");
    protected Label passwordLabel = new Label("password");
    protected Label confirmPasswordLabel = new Label("confirm password");
    protected Button saveButton = new Button("Save me");
    protected Button closeButton = new Button("Close me");

    protected Customer customer;
    protected CustomerService customerService = CustomerService.getInstance();
    protected Application application;


    public CustomerForm() {
        setTextFieldsPrompts();

        languageComboBox.addItem(ENGLISH);
        languageComboBox.addItem(SPANISH);
        languageComboBox.addItem(ITALIAN);
        languageComboBox.setNullSelectionAllowed(false);

        languageLevelComboBox.addItem(1);
        languageLevelComboBox.addItem(2);
        languageLevelComboBox.addItem(3);
        languageLevelComboBox.addItem(4);
        languageLevelComboBox.addItem(5);
        languageLevelComboBox.addItem(6);
        languageLevelComboBox.setNullSelectionAllowed(false);

        setSizeUndefined();
        addComponents(nameLabel, nameTextField, skypeLabel, skypeTextField, contactLabel, contactMeTextField, languageLabel, languageComboBox, languageLevelLabel, languageLevelComboBox);
    }

    protected void initComponents() {
        clearInputFields();
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addClickListener(e -> saveButtonClick());
        closeButton.addClickListener(f -> closeButtonClick());
    }

    protected boolean saveButtonClick() {
        return checkIfPasswordAndPasswordConfirmationAreTheSame();
    }

    void setCustomerValueBasedOnInputPrompt() {
        customer.setName(nameTextField.getValue());
        customer.setSkype(skypeTextField.getValue());
        customer.setContactMe(contactMeTextField.getValue());
        customer.setLanguage((String) languageComboBox.getValue());
        customer.setLanguageLevel((int) languageLevelComboBox.getValue());
        customer.setPassword(passwordTextField.getValue());
    }

    protected void setLabelsInvisible() {
        nameLabel.setVisible(false);
        skypeLabel.setVisible(false);
        contactLabel.setVisible(false);
        languageLabel.setVisible(false);
        languageLevelLabel.setVisible(false);
    }

    protected boolean checkContracts() {
        return Contract.isNull(nameTextField.getValue(), "name") ||
                Contract.isNull(skypeTextField.getValue(), "skype") ||
                Contract.isNull(contactMeTextField.getValue(), "contact me") ||
                Contract.isNull(languageComboBox.getValue(), "language") ||
                Contract.isNull(languageLevelComboBox.getValue(), "language level") ||
                Contract.isNull(passwordTextField.getValue(), "password");
    }

    protected boolean checkIfPasswordAndPasswordConfirmationAreTheSame() {
        if (!confirmPasswordTextField.getValue().equals(passwordTextField.getValue())) {
            Notification.show("Different passwords", "", Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }
        return true;
    }

    private void closeButtonClick() {
        setVisible(false);
    }

    protected void setTextFieldsPrompts() {
        nameTextField.setInputPrompt("name");
        skypeTextField.setInputPrompt("skype");
        contactMeTextField.setInputPrompt("contact [e.g. mail]");
        languageComboBox.setInputPrompt("language");
        languageLevelComboBox.setInputPrompt("language level");
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        initializeGUIelements(customer);
        setVisible(true);
    }

    protected void initializeGUIelements(Customer customer) {
        nameTextField.setValue(customer.getName());
        skypeTextField.setValue(customer.getSkype());
        contactMeTextField.setValue(customer.getContactMe());
        languageComboBox.setValue(customer.getLanguage());
        languageLevelComboBox.setValue(customer.getLanguageLevel());
        String customerPassword = customer.getPassword();
        passwordTextField.setValue(customerPassword);
        confirmPasswordTextField.setValue(customerPassword);
    }

    protected void clearInputFields() {
        nameTextField.clear();
        skypeTextField.clear();
        contactMeTextField.clear();
        languageComboBox.clear();
        languageLevelComboBox.clear();
        passwordTextField.clear();
        confirmPasswordTextField.clear();//This field is not store in Customer and it is not refreshing value
    }
}
