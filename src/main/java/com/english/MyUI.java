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

	private CustomerForm customerForm = new CustomerForm(this);
	private TextField filteredText = new TextField();
	private TextField filteredTextByFirstName = new TextField();
	private TextField filteredTextByLastName = new TextField();
	private TextField filteredTextByCountry = new TextField();
	private TextField filteredTextByEnglishLevel = new TextField();
	private TextField filteredTextBySkype = new TextField();
	private TextField filteredTextBySex = new TextField();
	private TextField filteredTextByEmail = new TextField();
	private CustomerService customerService = CustomerService.getInstance();
	private Grid grid = new Grid();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        filteredText.setInputPrompt("filtered by name");
        filteredText.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class,
        			customerService.findAll(e.getText())));
        });
        
        filteredTextByFirstName.setInputPrompt("filtered by first name");
        filteredTextByFirstName.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextByLastName.setInputPrompt("filtered by last name");
        filteredTextByLastName.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextByCountry.setInputPrompt("filtered by country");
        filteredTextByCountry.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextByEnglishLevel.setInputPrompt("filtered by english level");
        filteredTextByEnglishLevel.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextBySkype.setInputPrompt("filtered by skype");
        filteredTextBySkype.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextBySex.setInputPrompt("filtered by sex");
        filteredTextBySex.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        filteredTextByEmail.setInputPrompt("filtered by email");
        filteredTextByEmail.addTextChangeListener(e->{
        	grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
        });
        
        Button clearFilterTextButton = new Button(FontAwesome.TIMES);
        clearFilterTextButton.addClickListener(e->{
        	filteredText.clear();
        	updateList();
        });
        
        Button clearFilterFirstNameButton = new Button(FontAwesome.TIMES);
        clearFilterFirstNameButton.addClickListener(e->{
        	filteredTextByFirstName.clear();
        	updateList();
        });
        
        Button clearFilterLastNameButton = new Button(FontAwesome.TIMES);
        clearFilterLastNameButton.addClickListener(e->{
        	filteredTextByLastName.clear();
        	updateList();
        });
        
        Button clearFilterCountryButton = new Button(FontAwesome.TIMES);
        clearFilterCountryButton.addClickListener(e->{
        	filteredTextByCountry.clear();
        	updateList();
        });
        
        Button clearFilterEnglishLevelButton = new Button(FontAwesome.TIMES);
        clearFilterEnglishLevelButton.addClickListener(e->{
        	filteredTextByEnglishLevel.clear();
        	updateList();
        });
        
        Button clearFilterSkypeButton = new Button(FontAwesome.TIMES);
        clearFilterSkypeButton.addClickListener(e->{
        	filteredTextBySkype.clear();
        	updateList();
        });
        
        Button clearFilterSexButton = new Button(FontAwesome.TIMES);
        clearFilterSexButton.addClickListener(e->{
        	filteredTextBySex.clear();
        	updateList();
        });
        
        Button clearFilterEmailButton = new Button(FontAwesome.TIMES);
        clearFilterEmailButton.addClickListener(e->{
        	filteredTextByEmail.clear();
        	updateList();
        });
        
        CssLayout filtering = new CssLayout();
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filteredText, 
        						clearFilterTextButton, 
        						filteredTextByFirstName, 
        						clearFilterFirstNameButton, 
        						filteredTextByLastName, 
        						clearFilterLastNameButton,
        						filteredTextByCountry,
        						clearFilterCountryButton,
        						filteredTextByEnglishLevel,
        						clearFilterEnglishLevelButton,
        						filteredTextBySkype,
        						clearFilterSkypeButton,
        						filteredTextBySex,
        						clearFilterSexButton,
        						filteredTextByEmail,
        						clearFilterEmailButton);
        
        Button addCustomerButton = new Button("Add new customer");
        addCustomerButton.addClickListener(e->{
        	grid.select(null);
        	customerForm.setCustomer(new Customer());
        });
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerButton);
        toolbar.setSpacing(true);
        
        grid.setColumns("firstName", "lastName", "country", "englishLevel", "skype", "sex", "email");
        
        HorizontalLayout main = new HorizontalLayout(grid, customerForm);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);
        
        layout.addComponents(toolbar, main);
        
        updateList();
        
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        
        customerForm.setVisible(true);
        
        grid.addSelectionListener(event->{
        	if(event.getSelected().isEmpty()) {
        		customerForm.setVisible(false);
        	} else {
        		Customer customer = (Customer) event.getSelected().iterator().next();
        		customerForm.setCustomer(customer);
        	}
        });
    }

	public void updateList() {
		List<Customer> customers = customerService.findAll(filteredText.getValue());
		
		List<Customer> customersFilteredByFirstName = customerService.findAll(filteredTextByFirstName.getValue());
		List<Customer> customersFilteredByLastName = customerService.findAll(filteredTextByLastName.getValue());
		List<Customer> customersFilteredByCountry = customerService.findAll(filteredTextByCountry.getValue());
		List<Customer> customersFilteredByEnglishLevel = customerService.findAll(filteredTextByEnglishLevel.getValue());
		List<Customer> customersFilteredBySkype = customerService.findAll(filteredTextBySkype.getValue());
		List<Customer> customersFilteredBySex = customerService.findAll(filteredTextBySex.getValue());
		List<Customer> customersFilteredByEmail = customerService.findAll(filteredTextByEmail.getValue());
		
		customers.addAll(customersFilteredByFirstName);
		customers.addAll(customersFilteredByLastName);
		customers.addAll(customersFilteredByCountry);
		customers.addAll(customersFilteredByEnglishLevel);
		customers.addAll(customersFilteredBySkype);
		customers.addAll(customersFilteredBySex);
		customers.addAll(customersFilteredByEmail);
		grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
