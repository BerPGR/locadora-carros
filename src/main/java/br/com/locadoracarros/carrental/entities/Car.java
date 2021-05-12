package br.com.locadoracarros.carrental.entities;

public class Car {

	// attributes
	private String brand;
	private String model;
	private String licensePlate;

	// constructor
	public Car(String brand, String model, String licensePlate) {
		this.brand = brand;
		this.model = model;
		this.licensePlate = licensePlate;
	}

	// getters and setters
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
}
