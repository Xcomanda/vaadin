package com.demo.app.hotel.ui;

import javax.servlet.annotation.WebServlet;

import com.demo.app.hotel.services.CategoryService;
import com.demo.app.hotel.services.HotelService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MainUI extends UI {

	private VerticalLayout main;
	private HorizontalLayout menuLayout;
	private VerticalLayout formLayout;
	private MenuBar menu;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		CategoryService.getInstance();
		HotelService.getInstance();

		initComponents();
		initLayouts();

		menuLayout.addComponent(menu);
		main.addComponents(menuLayout, formLayout);
		setContent(main);
	}

	private void initLayouts() {
		main = new VerticalLayout();
		menuLayout = new HorizontalLayout();
		formLayout = new VerticalLayout();
	}

	private void initComponents() {
		menu = new MenuBar();
		menu.addItem("Hotels", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				setFormToMainLayout(new HotelForm());
			}
		});
		menu.addItem("Categories", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				setFormToMainLayout(new CategoryForm());
			}
		});
	}

	private void setFormToMainLayout(FormLayout form) {
		formLayout.removeAllComponents();
		form.setSizeFull();
		formLayout.addComponent(form);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
