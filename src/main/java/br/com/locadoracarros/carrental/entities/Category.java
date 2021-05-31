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

	@ApiModelProperty(notes = "Preço por hora de uma categoria")
	private int pricePerHour;

	public Category() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
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
		else if (!carType.equalsIgnoreCase("Hatch")
				|| !carType.equalsIgnoreCase("Sedan")
				|| !carType.equalsIgnoreCase("SUV") || !carType.equalsIgnoreCase("Crossover")
		|| !carType.equalsIgnoreCase("Minivan") || !carType.equalsIgnoreCase("Picape")
		|| !carType.equalsIgnoreCase("Elétrico") || !carType.equalsIgnoreCase("Híbrido")
		|| !carType.equalsIgnoreCase("Conversível") || !carType.equalsIgnoreCase("Cupe")
				|| !carType.equalsIgnoreCase("Luxo")){

			throw new DomainException("That's not a valid car type");
		}
		this.carType = carType;
	}

	@Override
	public String toString() {
		return "Categoria{" +
				"carType='" + carType + '\'' +
				'}';
	}
}
