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
	private Date firstDate;

	@ApiModelProperty(notes = "Dia da entrega")
	private Date lastDate;

	public Tenancy() {
	}

	public Tenancy(Car car, Client client, Date firstDate, Date lastDate, int id) {
		this.setId(id);
		this.setCar(car);
		this.setClient(client);
		this.setFirstDate(firstDate);
		this.setLastDate(lastDate);
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

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		if(firstDate.before(new Date())){
			throw new DomainException("Tenancy date must be after now!");
		}
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
}
