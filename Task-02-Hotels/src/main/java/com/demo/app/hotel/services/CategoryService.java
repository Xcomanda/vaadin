package com.demo.app.hotel.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.demo.app.hotel.entities.HotelCategory;

public class CategoryService {

	private static CategoryService instance;
	private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());
	private final HashMap<Long, HotelCategory> categories = new HashMap<>();
	private long nextId = 0;

	private CategoryService() {

	}

	public static CategoryService getInstance() {
		if (instance == null) {
			instance = new CategoryService();
			instance.ensureTestData();
		}
		return instance;
	}

	public synchronized List<HotelCategory> findAll() {
		ArrayList<HotelCategory> arrayList = new ArrayList<>();
		try {
			for (HotelCategory category : categories.values()) {
				arrayList.add(category.clone());
			}
		} catch (CloneNotSupportedException e) {
			LOGGER.log(Level.SEVERE, null, e);
		}
		Collections.sort(arrayList, new Comparator<HotelCategory>() {
			@Override
			public int compare(HotelCategory o1, HotelCategory o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});
		return arrayList;
	}

	public synchronized HotelCategory findById(long id) {
		HotelCategory category = categories.get(id);
		if (category != null) {
			try {
				return categories.get(id).clone();
			} catch (CloneNotSupportedException e) {
				LOGGER.log(Level.SEVERE, null, e);
				new RuntimeException(e);
			}
		}
		return null;
	}

	public synchronized void delete(HotelCategory category) {
		categories.remove(category.getId());
	}

	public synchronized void save(HotelCategory category) {
		if (category == null) {
			LOGGER.log(Level.SEVERE, "Category is null.");
			return;
		}
		if (category.getId() == null) {
			category.setId(++nextId);
		}
		try {
			category = (HotelCategory) category.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		categories.put(category.getId(), category);
	}

	private void ensureTestData() {
		if (findAll().isEmpty()) {
			categories.put(++nextId, new HotelCategory(nextId, "Hotel"));
			categories.put(++nextId, new HotelCategory(nextId, "Hostel"));
			categories.put(++nextId, new HotelCategory(nextId, "GuestHouse"));
			categories.put(++nextId, new HotelCategory(nextId, "Appartments"));
		}
	}

}