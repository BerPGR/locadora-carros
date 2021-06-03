package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;

public class Car {

	// attributes
	private String brand;
	private String model;
	private String licensePlate;




	// constructor

	public Car() {
	}

	public Car(String brand, String model, String licensePlate) {
		this.setBrand(brand);
		this.setModel(model);
		this.setLicensePlate(licensePlate);

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

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", licensePlate='" + licensePlate + '\'' +
				'}';
	}
}
