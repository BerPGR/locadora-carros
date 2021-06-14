package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@JsonFormat
@Table(name = "tenancy")
public class Tenancy {

	@Id
	@NotNull
	@Column(columnDefinition = "int", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador único da locação")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ApiModelProperty(notes = "Carro da locação")
	private Car car;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ApiModelProperty(notes = "Cliente que vai alugar")
	private Client client;

	@ApiModelProperty(notes = "Dia da locação")
	@JsonFormat(
			shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT-3")
	private Date firstDate;

	@JsonFormat(
			shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT-3")
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
		if (lastDate.before(firstDate)){
			throw new DomainException("Last date must be after first date.");
		}
		else if (lastDate.before(new Date())){
			throw new DomainException("Last date must be after now.");
		}
		this.lastDate = lastDate;
	}
}
