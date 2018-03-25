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
	private TextField sexTextField = new TextField();
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
        firstNameTextField.setInputPrompt("filtered by first name");
        firstNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        lastNameTextField.setInputPrompt("filtered by last name");
        lastNameTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        countryTextField.setInputPrompt("filtered by country");
        countryTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        englishLevelTextField.setInputPrompt("filtered by english level");
        englishLevelTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        skypeTextField.setInputPrompt("filtered by skype");
        skypeTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        sexTextField.setInputPrompt("filtered by sex");
        sexTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        emailTextField.setInputPrompt("filtered by email");
        emailTextField.addTextChangeListener(e->{
        	mainGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        Button clearFilterFirstNameButton = new Button(FontAwesome.TIMES);
        clearFilterFirstNameButton.addClickListener(e->{
        	firstNameTextField.clear();
        	updateList();
        });
        
        Button clearFilterLastNameButton = new Button(FontAwesome.TIMES);
        clearFilterLastNameButton.addClickListener(e->{
        	lastNameTextField.clear();
        	updateList();
        });
        
        Button clearFilterCountryButton = new Button(FontAwesome.TIMES);
        clearFilterCountryButton.addClickListener(e->{
        	countryTextField.clear();
        	updateList();
        });
        
        Button clearFilterEnglishLevelButton = new Button(FontAwesome.TIMES);
        clearFilterEnglishLevelButton.addClickListener(e->{
        	englishLevelTextField.clear();
        	updateList();
        });
        
        Button clearFilterSkypeButton = new Button(FontAwesome.TIMES);
        clearFilterSkypeButton.addClickListener(e->{
        	skypeTextField.clear();
        	updateList();
        });
        
        Button clearFilterSexButton = new Button(FontAwesome.TIMES);
        clearFilterSexButton.addClickListener(e->{
        	sexTextField.clear();
        	updateList();
        });
        
        Button clearFilterEmailButton = new Button(FontAwesome.TIMES);
        clearFilterEmailButton.addClickListener(e->{
        	emailTextField.clear();
        	updateList();
        });

        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringLayout.addComponents(firstNameTextField, 
        						clearFilterFirstNameButton, 
        						lastNameTextField, 
        						clearFilterLastNameButton,
        						countryTextField,
        						clearFilterCountryButton,
        						englishLevelTextField,
        						clearFilterEnglishLevelButton,
        						skypeTextField,
        						clearFilterSkypeButton,
        						sexTextField,
        						clearFilterSexButton,
        						emailTextField,
        						clearFilterEmailButton);
	}
	
	private void handleRegisterButton() {
		registerButton.addClickListener(e->{
        	mainGrid.select(null);
        	registrationForm.setCustomer(new Customer());
        });		
	}
	
	private void handleLogInButton() {
        logInButton.addClickListener(e->{
        	mainGrid.select(null);
        	logInForm.showLogInFormOnButtonClick();
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
		sexTextField.clear();
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
		List<Customer> customersFilteredBySex = customerService.findAll(sexTextField.getValue());
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
