package com.english;

import com.english.db.DBHandler;
import com.english.forms.*;
import com.english.model.Customer;
import com.english.service.CustomerService;
import com.english.utils.SessionAttributes;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Optional;

import static com.english.Globals.*;


/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class Application extends UI {

    private final String supportEmail = new String("dekoderer@gmail.com");
    private static final long serialVersionUID = 1L;

    private final String STATUS_NOT_LOG_IN = "You are not logged in";
    private CustomerForm customerForm = new CustomerForm();
    private RegistrationForm registrationForm = new RegistrationForm(this);
    public EditPasswordForm editPasswordForm = new EditPasswordForm(this);
    private LogInForm logInForm = new LogInForm(this);
    private EditProfileForm editionForm = new EditProfileForm(this);

    private TextField nameTextField = new TextField();
    private TextField skypeTextField = new TextField();
    private TextField contactMeTextField = new TextField();
    private ComboBox languageComboBox = new ComboBox();
    private ComboBox languageLevelComboBox = new ComboBox();
    private Label loginStatusLabel = new Label();
    private Label descriptionLabel = new Label("Let's talk");
    private Label subtitleLabel = new Label("Improve your language skills for free");
    private Button searchButton = new Button("Search");
    private Button clearFilterButton = new Button("Clear filters");
    private Button registerButton = new Button("Register me");
    private Button logInButton = new Button("Log me");
    private Button logOutButton = new Button("Log out");
    private Button editMeButton = new Button("Edit me");

//	ThemeResource letsSpeakEnglishResource = new ThemeResource("images/LetsSpeakEng.png");
//	Image letsSpeakEnglishImage = new Image("", letsSpeakEnglishResource);

    ThemeResource skypeLogoResourse = new ThemeResource("images/SkypeLogo.png");
    Image skypeLogoImage = new Image("", skypeLogoResourse);

    private Grid mainGrid = new Grid();
    CssLayout filteringLayout = new CssLayout();
    CssLayout mainButtonsLayout = new CssLayout();
    CssLayout languageButtonsLayout = new CssLayout();

    private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initComponents();
        handleTextFieldsFiltering();
        handleMainButtonsLayout();
        handleButtons();

        setStyleName("backgroundimage");

        final VerticalLayout pageLayout = new VerticalLayout();

        setMainLabelsStyle();

        VerticalLayout toolbarLayout = new VerticalLayout();
        toolbarLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        toolbarLayout.addComponents(descriptionLabel, subtitleLabel, filteringLayout, mainButtonsLayout, skypeLogoImage, languageButtonsLayout, loginStatusLabel);
        toolbarLayout.setSpacing(true);

        mainGrid.setColumns("name", "skype", "contactMe", "languageLevel");

        HorizontalLayout mainLayout = new HorizontalLayout(mainGrid, customerForm, registrationForm, logInForm, editionForm, editPasswordForm);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainGrid.setSizeFull();
        mainLayout.setExpandRatio(mainGrid, 1);

        languageComboBox.setNullSelectionAllowed(false);
        languageComboBox.setValue(ENGLISH);

        PopupView contactPopupView = new PopupView(new Content() {
            @Override
            public Component getPopupComponent() {
                return new VerticalLayout() {
                    {
                        setSpacing(false);
                        addComponent(new Label(supportEmail));
                    }
                };
            }

            @Override
            public String getMinimizedValueAsHTML() {
                return "contact";
            }
        });

        pageLayout.addComponents(toolbarLayout, mainLayout, contactPopupView);
        pageLayout.setComponentAlignment(contactPopupView, Alignment.TOP_CENTER);

        updateMainGridCustomerList();

        pageLayout.setMargin(true);
        pageLayout.setSpacing(true);
        setContent(pageLayout);

        mainGrid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty() || registrationForm.isVisible() || logInForm.isVisible() || editionForm.isVisible() || editPasswordForm.isVisible()) {
                customerForm.setVisible(false);
            } else {
                Customer customer = (Customer) event.getSelected().iterator().next();
                customerForm.setCustomer(customer);
            }
        });
    }

    private void setMainLabelsStyle() {
        descriptionLabel.setStyleName("mainLabel");
        subtitleLabel.setStyleName("subtitleLabel");
        loginStatusLabel.setStyleName("loginStatusLabel");
    }

    private void initComponents() {
        setLoginStatusLabel("");
        setFormsInvisible();
    }

    public void setLoginStatusLabel(String status) {
        loginStatusLabel.setValue(status.isEmpty() ? STATUS_NOT_LOG_IN : "Hello : " + status);
    }

    public void setVisibleRegisterButton(boolean visible) {
        registerButton.setVisible(visible);
    }

    public void setVisibleEditMeButton(boolean visible) {
        editMeButton.setVisible(visible);
    }

    public void setVisibleLogInButton(boolean visible) {
        logInButton.setVisible(visible);
    }

    public void setVisibleLogOutButton(boolean visible) {
        logOutButton.setVisible(visible);
    }

    private void handleTextFieldsFiltering() {
        setFiltersInputPrompt();
        setLanguageLevelItems();
        setLanguageItems();

//        nameTextField.addTextChangeListener(e->{
//        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.NAME)));
//        });
//
//		skypeTextField.addTextChangeListener(e->{
//			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.SKYPE)));
//		});
//
//        contactMeTextField.addTextChangeListener(e->{
//        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.CONTACT_ME)));
//        });
//
//		languageLevelComboBox.addValueChangeListener(e->{
//			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(getLanguageLevel(), CustomerService.FILTER_TYPE.LANGUAGE_LEVEL)));
//		});

//        languageComboBox.addValueChangeListener(e->{
//			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(getLanguage(), CustomerService.FILTER_TYPE.LANGUAGE_LEVEL)));
//		});

        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringLayout.addComponents(nameTextField,
                skypeTextField,
                contactMeTextField,
                languageComboBox,
                languageLevelComboBox);
    }

    private void handleMainButtonsLayout() {
        mainButtonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        mainButtonsLayout.addComponents(searchButton, clearFilterButton, registerButton, logInButton, logOutButton, editMeButton);
    }

    private void setFiltersInputPrompt() {
        nameTextField.setInputPrompt("name");
        skypeTextField.setInputPrompt("skype");
        contactMeTextField.setInputPrompt("contact");
        languageLevelComboBox.setInputPrompt("language level");
        languageComboBox.setInputPrompt("language");
    }

    private void setLanguageLevelItems() {
        languageLevelComboBox.addItem(1);
        languageLevelComboBox.addItem(2);
        languageLevelComboBox.addItem(3);
        languageLevelComboBox.addItem(4);
        languageLevelComboBox.addItem(5);
        languageLevelComboBox.addItem(6);
    }

    private void setLanguageItems() {
        languageComboBox.addItem(ENGLISH);
        languageComboBox.addItem(SPANISH);
        languageComboBox.addItem(ITALIAN);
    }

    private String getLanguageLevel() {
        return null == languageLevelComboBox.getValue() ? "" : Integer.toString((Integer) languageLevelComboBox.getValue());
    }

    private String getLanguage() {
        return null == languageComboBox.getValue() ? "" : (String) languageComboBox.getValue();
    }

    private void setFormsInvisible() {
        mainGrid.select(null);
        registrationForm.setVisible(false);
        logInForm.setVisible(false);
        customerForm.setVisible(false);
        editionForm.setVisible(false);
        editPasswordForm.setVisible(false);
    }

    private void handleSearchButton() {
        searchButton.addClickListener(e -> updateMainGridCustomerList());
    }

    private void handleClearFilterButton() {
        clearFilterButton.addClickListener(e -> clearFilteringTextFields());
    }

    private void handleRegisterButton() {
        registerButton.addClickListener(e -> {
            if (registrationForm.isVisible()) setFormsInvisible();
            else {
                setFormsInvisible();
                registrationForm.setCustomer(new Customer()); //this is handled in saveButtonClick()
            }
        });
    }

    private void handleEditMeButton() {
        editMeButton.addClickListener(e -> {
            if (editionForm.isVisible()) setFormsInvisible();
            else {
                setFormsInvisible();

                Object sessionAttribute = getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
                if (sessionAttribute == null) return;

                String user_session_attribute = sessionAttribute.toString();
                Optional<Customer> customer = DBHandler.getSingleCustomerViaContactMe(user_session_attribute);
                if (customer.isPresent()) {
                    editionForm.setCustomer(customer.get());
                } else {
                    customer = DBHandler.getSingleCustomerViaName(user_session_attribute);
                    if (customer.isPresent()) editionForm.setCustomer(customer.get());
                }
            }
        });
        editMeButton.setVisible(false);
    }

    private void handleLogInButton() {
        logInButton.addClickListener(e -> {
            if (logInForm.isVisible()) setFormsInvisible();
            else {
                setFormsInvisible();
                logInForm.showLogInFormOnButtonClick();
            }
        });
    }

    private void handleLogOutButton() {
        logOutButton.addClickListener(e -> {
            setFormsInvisible();
            getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, null);
            registerButton.setVisible(true);
            logInButton.setVisible(true);
            logOutButton.setVisible(false);
            loginStatusLabel.setValue(STATUS_NOT_LOG_IN);
            editMeButton.setVisible(false);
        });
        logOutButton.setVisible(false);
    }

    protected void handleButtons() {
        handleSearchButton();
        handleClearFilterButton();
        handleRegisterButton();
        handleLogInButton();
        handleLogOutButton();
        handleEditMeButton();
    }

    protected void clearInputFields() {
        nameTextField.clear();
        skypeTextField.clear();
        contactMeTextField.clear();
        languageComboBox.setValue(ENGLISH);
        languageLevelComboBox.clear();
    }

    protected void clearFilteringTextFields() {
        clearInputFields();
        updateMainGridCustomerList();
    }

    public void updateMainGridCustomerList() {
        List<Customer> customers = customerService.getFilteredContacts(nameTextField.getValue(), skypeTextField.getValue(), contactMeTextField.getValue(), getLanguageLevel(), getLanguage());
        mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
    }

    public void updateCustomers() {
        customerService.updateCustomersFromDB();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Application.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

        private static final long serialVersionUID = 1L;
    }
}
