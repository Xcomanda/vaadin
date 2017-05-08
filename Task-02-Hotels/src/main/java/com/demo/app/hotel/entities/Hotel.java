package com.demo.app.hotel.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Hotel implements Serializable, Cloneable {

	private Long id;
	private String name = "";
	private String address = "";
	private Integer rating;
	private Long operatesDays = 0L;
	private Long categoryId;
	private String url;
	private String description = "";

	public Hotel() {

	}

	public Hotel(Long id, String name, String address, Integer rating, Long operatesDays, Long categoryId, String url) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.operatesDays = operatesDays;
		this.categoryId = categoryId;
		this.url = url;
	}

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public String toString() {
		return name + " " + rating + "stars " + address;
	}

	@Override
	public Hotel clone() throws CloneNotSupportedException {
		return (Hotel) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getOperatesDays() {
		return operatesDays;
	}

	public void setOperatesDays(Long operatesDays) {
		this.operatesDays = operatesDays;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}