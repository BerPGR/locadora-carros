package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@NotNull
	@Column(columnDefinition = "int", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador único de categoria", required = true)
	private int id;

	@ApiModelProperty(notes = "Tipo da categoria")
	private String carType;

	@ApiModelProperty(notes = "Preço por dia de uma categoria")
	private int pricePerDay;

	public Category() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Category(String carType) {
		this.setCarType(carType);
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		if (carType.equals(null) || carType.isEmpty()){
			throw new DomainException("Car must have a type");
		}
		this.carType = carType;
	}

	@Override
	public String toString() {
		return carType + '\'' +
				", pricePerDay=" + pricePerDay;
	}
}
