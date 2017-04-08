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
        
        Button clearFilterTextButton = new Button(FontAwesome.TIMES);
        clearFilterTextButton.addClickListener(e->{
        	filteredText.clear();
        	updateList();
        });
        
        CssLayout filtering = new CssLayout();
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filteredText, clearFilterTextButton);
        
        Button addCustomerButton = new Button("Add new customer");
        addCustomerButton.addClickListener(e->{
        	grid.select(null);
        	customerForm.setCustomer(new Customer());
        });
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerButton);
        toolbar.setSpacing(true);
        
        grid.setColumns("firstName", "lastName", "email");
        
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
        /*final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);*/
    }

	public void updateList() {
		List<Customer> customers = customerService.findAll(filteredText.getValue());
		grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
