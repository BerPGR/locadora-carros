package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;

public class Car {

	// attributes
	private String brand;
	private String model;
	private String licensePlate;

	@ApiModelProperty(notes = "Categoria do carro", required = true)
	private Category category;


	// constructor

	public Car() {
	}

	public Car(String brand, String model, String licensePlate, Category category) {
		this.setBrand(brand);
		this.setModel(model);
		this.setLicensePlate(licensePlate);
		this.setCategory(category);
	}

	// getters and setters
	public String getBrand() {
		return brand;
	}

	// using DomainException to make validations
	public void setBrand(String brand) {
		if(brand.equals(null) || brand.equals("")){
			throw new DomainException("The car must have a brand.");
		}
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		if(model.equals(null) || model.equals("")){
			throw new DomainException("The car must have a model.");
		}
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		if(licensePlate.equals(null) || licensePlate.equals("")){
			throw new DomainException("The car must have a license plate.");
		}
		this.licensePlate = licensePlate;
	}

	@Override
	public String toString() {
		return "Car{" +
				"brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", licensePlate='" + licensePlate + '\'' +
				'}';
	}

	@OneToMany(targetEntity=Category.class, mappedBy="car", fetch=FetchType.EAGER)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", licensePlate='" + licensePlate + '\'' +
				", category=" + category +
				'}';
	}
}
