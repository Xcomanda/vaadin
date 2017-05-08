package com.demo.app.hotel.ui;

import java.util.List;

import com.demo.app.hotel.entities.Hotel;
import com.demo.app.hotel.entities.HotelCategory;
import com.demo.app.hotel.services.CategoryService;
import com.demo.app.hotel.services.HotelService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class HotelForm extends FormLayout {
	private HotelService hotelService = HotelService.getInstance();

	private TextField filterTextByName;
	private TextField filterTextByAddress;
	private Button clearFilterByNameBtn;
	private Button clearFilterByAdressBtn;
	private Grid<Hotel> gridHotel;
	private Button addHotelBtn;

	private HorizontalLayout toolbar;
	private HorizontalLayout filters;
	private CssLayout filterByName;
	private CssLayout filterByAddress;

	private EditHotelForm editHotelForm;

	public HotelForm() {
		initComponents();
		initLayouts();

		filterByName.addComponents(filterTextByName, clearFilterByNameBtn);
		filterByAddress.addComponents(filterTextByAddress, clearFilterByAdressBtn);
		filters.addComponents(filterByName, filterByAddress);
		toolbar.addComponents(filters, addHotelBtn);
		addComponents(toolbar, gridHotel, editHotelForm);
		updateList();
	}

	private void initLayouts() {
		filterByName = new CssLayout();
		filterByName.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filterByAddress = new CssLayout();
		filterByAddress.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		toolbar = new HorizontalLayout();
		filters = new HorizontalLayout();
	}

	private void initComponents() {
		filterTextByName = new TextField();
		filterTextByName.setPlaceholder("Filter by name");
		filterTextByName.addValueChangeListener(e -> updateList());
		filterTextByName.setValueChangeMode(ValueChangeMode.LAZY);

		filterTextByAddress = new TextField();
		filterTextByAddress.setPlaceholder("Filter by address");
		filterTextByAddress.addValueChangeListener(e -> updateList());
		filterTextByAddress.setValueChangeMode(ValueChangeMode.LAZY);

		clearFilterByNameBtn = new Button();
		clearFilterByNameBtn.setDescription("Clear filter by name");
		clearFilterByNameBtn.setIcon(VaadinIcons.CLOSE);
		clearFilterByNameBtn.addClickListener(e -> filterTextByName.clear());

		clearFilterByAdressBtn = new Button();
		clearFilterByAdressBtn.setDescription("Clear filter by address");
		clearFilterByAdressBtn.setIcon(VaadinIcons.CLOSE);
		clearFilterByAdressBtn.addClickListener(e -> filterTextByAddress.clear());

		gridHotel = new Grid<>(Hotel.class);
		gridHotel.setColumns("name", "address", "rating", "operatesDays");
		gridHotel.addColumn(hotel -> {
			HotelCategory category = CategoryService.getInstance().findById(hotel.getCategoryId());
			if (category != null) {
				return category.getName();
			}
			return "[not found]";
		}).setCaption("Category");
		gridHotel.addColumn(hotel -> "<a href='" + hotel.getUrl() + "' target='_blank'>link</a>", new HtmlRenderer())
				.setCaption("url");
		gridHotel.setSizeFull();
		gridHotel.asSingleSelect().addValueChangeListener(e -> {
			if (e.getValue() == null) {
				editHotelForm.setVisible(false);
			} else {
				editHotelForm.setHotel(e.getValue());
			}
		});

		addHotelBtn = new Button("Add new hotel");
		addHotelBtn.addClickListener(e -> {
			gridHotel.asSingleSelect().clear();
			editHotelForm.setHotel(new Hotel());
		});

		editHotelForm = new EditHotelForm(this);
		editHotelForm.setVisible(false);

	}

	public void updateList() {
		List<Hotel> hotels = hotelService.findAllbyNameAndAddress(filterTextByName.getValue(),
				filterTextByAddress.getValue());
		gridHotel.setItems(hotels);
	}
}
