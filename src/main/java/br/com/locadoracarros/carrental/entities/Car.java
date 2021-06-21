package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "car")
public class Car {

	// attributes
	@Id
	@NotNull
	@Column(columnDefinition = "int", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador único do carro", required = true)
	private int id;

	@ApiModelProperty(notes = "Marca do carro", required = true)
	private String brand;

	@ApiModelProperty(notes = "Modelo do carro", required = true)
	private String model;

	@ApiModelProperty(notes = "Placa do carro", required = true)
	private String licensePlate;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ApiModelProperty(notes = "Categoria do carro", required = true)
	private Category category;

	public Car(String brand, String model, String licensePlate, Category category) {
		this.setBrand(brand);
		this.setModel(model);
		this.setLicensePlate(licensePlate);
		this.setCategory(category);
	}

	// using DomainException to make validations
	public void setBrand(String brand) {
		if(brand.equals(null) || brand.isEmpty()){
			throw new DomainException("The car must have a brand.");
		}
		this.brand = brand;
	}

	public void setModel(String model) {
		if(model.equals(null) || model.isEmpty()){
			throw new DomainException("The car must have a model.");
		}
		this.model = model;
	}

	public void setLicensePlate(String licensePlate) {
		if(licensePlate.equals(null) || licensePlate.isEmpty()){
			throw new DomainException("The car must have a license plate.");
		}
		this.licensePlate = licensePlate;
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
