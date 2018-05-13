package com.english;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import java.util.ArrayList;
import java.util.List;


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

	private TextField firstNameTextField = new TextField();
	private TextField lastNameTextField = new TextField();
	private TextField countryTextField = new TextField();
	private TextField englishLevelTextField = new TextField();
	private TextField skypeTextField = new TextField();
	private ComboBox sexComboBox = new ComboBox();
	private TextField emailTextField = new TextField();
	private Label loginStatusLabel = new Label();
	private Button registerButton = new Button("Register me");
	private Button logInButton = new Button("Log me");
	private Button logOutButton = new Button("Log out me");
	private Button clearFilterButton = new Button("Clear filteres");

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

        VerticalLayout toolbarLayout = new VerticalLayout(filteringLayout, loginStatusLabel, registerButton, logInButton, logOutButton, clearFilterButton);
        toolbarLayout.setSpacing(true);

        mainGrid.setColumns("firstName", "lastName", "country", "englishLevel", "skype", "sex", "email");

        HorizontalLayout mainLayout = new HorizontalLayout(mainGrid, customerForm, registrationForm, logInForm);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainGrid.setSizeFull();
        mainLayout.setExpandRatio(mainGrid, 1);

        pageLayout.addComponents(toolbarLayout, mainLayout);

        updateList();

        pageLayout.setMargin(true);
        pageLayout.setSpacing(true);
        setContent(pageLayout);

        mainGrid.addSelectionListener(event->{
        	if(event.getSelected().isEmpty() || registrationForm.isVisible() || logInForm.isVisible()) {
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

	public void setVisibleLogInButton(boolean visible) {
		logInButton.setVisible(visible);
	}

	public void setVisibleLogOutButton(boolean visible) {
		logOutButton.setVisible(visible);
	}

	private void handleTextFieldsFiltering() {
        firstNameTextField.setInputPrompt("filter by first name");
        firstNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.FIRST_NAME)));
        });

        lastNameTextField.setInputPrompt("filter by last name");
        lastNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.LAST_NAME)));
        });

        countryTextField.setInputPrompt("filter by country");
        countryTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.COUNTRY)));
        });

        englishLevelTextField.setInputPrompt("filter by english level");
        englishLevelTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.ENGLISH_LEVEL)));
        });

        skypeTextField.setInputPrompt("filter by skype");
        skypeTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(e.getText(), CustomerService.FILTER_TYPE.SKYPE)));
        });

		sexComboBox.setInputPrompt("filter by sex");
		sexComboBox.addItem(true);
		sexComboBox.addItem(false);
		sexComboBox.setItemCaption(true, "Male");
		sexComboBox.setItemCaption(false, "Female");
		sexComboBox.addValueChangeListener(e->{
			mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findBy(getFilterSex(), CustomerService.FILTER_TYPE.SEX)));
		});

        emailTextField.setInputPrompt("filter by email");
        emailTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });

        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringLayout.addComponents(firstNameTextField,
				lastNameTextField,
				countryTextField,
				englishLevelTextField,
				skypeTextField,
				sexComboBox,
				emailTextField);
	}

	private String getFilterSex() {
		return null == sexComboBox.getValue() ? "" : GlobalFunctions.convertBooleanToString((boolean) sexComboBox.getValue());
	}

	private void setFormsToInvisible() {
		mainGrid.select(null);
		registrationForm.setVisible(false);
		logInForm.setVisible(false);
		customerForm.setVisible(false);
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
	}

	protected void clearFilteringTextFields() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		countryTextField.clear();
		englishLevelTextField.clear();
		skypeTextField.clear();
		sexComboBox.clear();
		emailTextField.clear();
		updateList();
	}

	public void updateList() {
		List<Customer> customers = new ArrayList<Customer>();

		List<Customer> customersFilteredByFirstName = customerService.findAll(firstNameTextField.getValue());
		List<Customer> customersFilteredByLastName = customerService.findAll(lastNameTextField.getValue());
		List<Customer> customersFilteredByCountry = customerService.findAll(countryTextField.getValue());
		List<Customer> customersFilteredByEnglishLevel = customerService.findAll(englishLevelTextField.getValue());
		List<Customer> customersFilteredBySkype = customerService.findAll(skypeTextField.getValue());
		List<Customer> customersFilteredBySex = customerService.findAll(getFilterSex());
		List<Customer> customersFilteredByEmail = customerService.findAll(emailTextField.getValue());

		customers.addAll(customersFilteredByFirstName);
		customers.addAll(customersFilteredByLastName);
		customers.addAll(customersFilteredByCountry);
		customers.addAll(customersFilteredByEnglishLevel);
		customers.addAll(customersFilteredBySkype);
		customers.addAll(customersFilteredBySex);
		customers.addAll(customersFilteredByEmail);

		mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
    }
}
