package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tenancy")
public class Tenancy {

	@Id
	@NotNull
	@Column(columnDefinition = "int", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador único da locação")
	private int id;

	@ApiModelProperty(notes = "Carro da locação")
	private Car car;

	@ApiModelProperty(notes = "Cliente que vai alugar")
	private Client client;

	@ApiModelProperty(notes = "Dia da locação")
	private Date tenancyDate;

	public Tenancy() {
	}

	public Tenancy(Car car, Client client, Date tenancyDate, int id) {
		this.setId(id);
		this.setCar(car);
		this.setClient(client);
		this.setTenancyDate(tenancyDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getTenancyDate() {
		return tenancyDate;
	}

	public void setTenancyDate(Date tenancyDate) {
		if(tenancyDate.before(new Date())){
			throw new DomainException("Tenancy date must be after now!");
		}
		this.tenancyDate = tenancyDate;
	}
}
