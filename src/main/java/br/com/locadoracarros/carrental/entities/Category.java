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
@NoArgsConstructor
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
	private double pricePerDay;

	public Category(int id, String carType, double pricePerDay){
		this.setId(id);
		this.setCarType(carType);
		this.setPricePerDay(pricePerDay);
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
