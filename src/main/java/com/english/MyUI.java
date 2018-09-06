package com.english;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	private final String LOGIN_STATUS = "NOT LOGGED";
	private CustomerForm customerForm = new CustomerForm();
	private RegistrationForm registrationForm = new RegistrationForm(this);
	private LogInForm logInForm = new LogInForm(this);
	private EditionForm editionForm = new EditionForm(this);

	private TextField firstNameTextField = new TextField();
	private TextField lastNameTextField = new TextField();
	private TextField countryTextField = new TextField();
	private ComboBox englishLevelComboBox = new ComboBox();
	private TextField skypeTextField = new TextField();
	private ComboBox genderComboBox = new ComboBox();
	private TextField emailTextField = new TextField();
	private Label loginStatusLabel = new Label();
	private Label filterByLabel = new Label("Filter by:");
	private Label descriptionLabel = new Label("Find a person you would like to speak in English");
	private Button registerButton = new Button("Register me");
	private Button logInButton = new Button("Log me");
	private Button logOutButton = new Button("Log out me");
	private Button clearFilterButton = new Button("Clear filteres");
	private Button editMeButton = new Button("Edit me");

	ThemeResource letsSpeakEnglishResource = new ThemeResource("images/LetsSpeakEng.png");
	Image letsSpeakEnglishImage = new Image("", letsSpeakEnglishResource);

	ThemeResource skypeResourse = new ThemeResource("images/Skype.png");
	Image skypeImage = new Image("", skypeResourse);

	private Grid mainGrid = new Grid();
	CssLayout filteringLayout = new CssLayout();

	private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initComponents();
        handleTextFieldsFiltering();
        handleButtons();

		setStyleName("backgroundimage");

        final VerticalLayout pageLayout = new VerticalLayout();

        VerticalLayout toolbarLayout = new VerticalLayout(letsSpeakEnglishImage, descriptionLabel, filterByLabel, filteringLayout, loginStatusLabel, registerButton, logInButton, logOutButton, clearFilterButton, editMeButton);
        toolbarLayout.setSpacing(true);

        mainGrid.setColumns("firstName", "lastName", "country", "englishLevel", "skype", "gender", "email");

        HorizontalLayout mainLayout = new HorizontalLayout(mainGrid, customerForm, registrationForm, logInForm, editionForm);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainGrid.setSizeFull();
        mainLayout.setExpandRatio(mainGrid, 1);

        pageLayout.addComponents(toolbarLayout, mainLayout, skypeImage);

        updateList();

        pageLayout.setMargin(true);
        pageLayout.setSpacing(true);
        setContent(pageLayout);

        mainGrid.addSelectionListener(event->{
        	if(event.getSelected().isEmpty() || registrationForm.isVisible() || logInForm.isVisible() || editionForm.isVisible()) {
        		customerForm.setVisible(false);
        	} else {
        		Customer customer = (Customer) event.getSelected().iterator().next();
        		customerForm.setCustomer(customer);
        	}
        });
    }

    private void initComponents() {
    	loginStatusLabel.setValue(LOGIN_STATUS);
        customerForm.setVisible(false);
        registrationForm.setVisible(false);
        logInForm.setVisible(false);
        editionForm.setVisible(false);
    }

	public void setLoginStatus(String status) {
		loginStatusLabel.setValue(status);
	}

	public String getLoginStatus() {
		return loginStatusLabel.getValue();
	}

	public void setVisibleReigsterButton(boolean visible) {
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

        firstNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.FIRST_NAME)));
        });

        lastNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.LAST_NAME)));
        });

        countryTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.COUNTRY)));
        });

		englishLevelComboBox.addItem(1);
		englishLevelComboBox.addItem(2);
		englishLevelComboBox.addItem(3);
		englishLevelComboBox.addItem(4);
		englishLevelComboBox.addItem(5);
		englishLevelComboBox.addItem(6);
		englishLevelComboBox.addValueChangeListener(e->{
			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(getFilterEnglishLevel(), CustomerService.FILTER_TYPE.ENGLISH_LEVEL)));
		});

        skypeTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.SKYPE)));
        });

		genderComboBox.addItem(true);
		genderComboBox.addItem(false);
		genderComboBox.setItemCaption(true, "Male");
		genderComboBox.setItemCaption(false, "Female");
		genderComboBox.addValueChangeListener(e->{
			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(getFilterGender(), CustomerService.FILTER_TYPE.GENDER)));
		});

        emailTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });

        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringLayout.addComponents(firstNameTextField,
				lastNameTextField,
				countryTextField,
				englishLevelComboBox,
				skypeTextField,
				genderComboBox,
				emailTextField);
	}

	private void setFiltersInputPrompt() {
		firstNameTextField.setInputPrompt("first name");
		lastNameTextField.setInputPrompt("last name");
		countryTextField.setInputPrompt("country");
		englishLevelComboBox.setInputPrompt("english level");
		skypeTextField.setInputPrompt("skype");
		genderComboBox.setInputPrompt("gender");
		emailTextField.setInputPrompt("email");
	}

	private String getFilterEnglishLevel() {
		return null == englishLevelComboBox.getValue() ? "" : Integer.toString((Integer) englishLevelComboBox.getValue());
	}

	private String getFilterGender() {
		return null == genderComboBox.getValue() ? "" : GlobalFunctions.convertBooleanToString((boolean) genderComboBox.getValue());
	}

	private void setFormsToInvisible() {
		mainGrid.select(null);
		registrationForm.setVisible(false);
		logInForm.setVisible(false);
		customerForm.setVisible(false);
		editionForm.setVisible(false);
	}

	private void handleRegisterButton() {
		registerButton.addClickListener(e->{
			if(registrationForm.isVisible()) setFormsToInvisible();
			else {
				setFormsToInvisible();
				registrationForm.setCustomer(new Customer()); //this is handled in saveButtonClick()
			}
        });
	}

	private void handleEditMeButton() {
    	editMeButton.addClickListener(e->{
			if(editionForm.isVisible()) setFormsToInvisible();
    		else {
				setFormsToInvisible();
				Optional<Customer> customer = DBHandler.getSingleCustomer((String) getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE));
				if (customer.isPresent()) {
					editionForm.setCustomer(customer.get());
				}
			}
		});
    	editMeButton.setVisible(false);
	}

	private void handleLogInButton() {
        logInButton.addClickListener(e->{
        	if(logInForm.isVisible()) setFormsToInvisible();
			else {
				setFormsToInvisible();
				logInForm.showLogInFormOnButtonClick();
			}
        });
	}

	private void handleLogOutButton() {
        logOutButton.addClickListener(e->{
        	mainGrid.select(null);
        	getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, null);
        	registerButton.setVisible(true);
        	logInButton.setVisible(true);
        	logOutButton.setVisible(false);
        	loginStatusLabel.setValue(LOGIN_STATUS);
        	editMeButton.setVisible(false);
        });
        logOutButton.setVisible(false);
	}

	private void handleClearFilterButton() {
		clearFilterButton.addClickListener(e->clearFilteringTextFields());
	}

	protected void handleButtons() {
		handleRegisterButton();
		handleLogInButton();
		handleLogOutButton();
		handleClearFilterButton();
		handleEditMeButton();
	}

	protected void clearFilteringTextFields() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		countryTextField.clear();
		englishLevelComboBox.clear();
		skypeTextField.clear();
		genderComboBox.clear();
		emailTextField.clear();
		updateList();
	}

	public void updateList() {
		List<Customer> customers = new ArrayList<Customer>();

		List<Customer> customersFilteredByFirstName = customerService.findAll(firstNameTextField.getValue());
		List<Customer> customersFilteredByLastName = customerService.findAll(lastNameTextField.getValue());
		List<Customer> customersFilteredByCountry = customerService.findAll(countryTextField.getValue());
		List<Customer> customersFilteredByEnglishLevel = customerService.findAll(getFilterEnglishLevel());
		List<Customer> customersFilteredBySkype = customerService.findAll(skypeTextField.getValue());
		List<Customer> customersFilteredByGender = customerService.findAll(getFilterGender());
		List<Customer> customersFilteredByEmail = customerService.findAll(emailTextField.getValue());

		customers.addAll(customersFilteredByFirstName);
		customers.addAll(customersFilteredByLastName);
		customers.addAll(customersFilteredByCountry);
		customers.addAll(customersFilteredByEnglishLevel);
		customers.addAll(customersFilteredBySkype);
		customers.addAll(customersFilteredByGender);
		customers.addAll(customersFilteredByEmail);

		mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}

	public void updateCustomers() {
    	customerService.updateCustomersFromDB();
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
    }
}
