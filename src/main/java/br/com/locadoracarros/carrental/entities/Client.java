package br.com.locadoracarros.carrental.entities;

import br.com.locadoracarros.carrental.exceptions.DomainException;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@NotNull
	@Column(columnDefinition = "int", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador único de cliente", required = true)
	private int id;

	@ApiModelProperty(notes = "Nome do cliente", required = true)
	private String name;

	@ApiModelProperty(notes = "Idade do cliente", required = true)
	private int age;

	public Client() {
	}

	public Client(int id, String name, int age) {
		this.setName(name);
		this.setAge(age);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.equals(null) || name.isEmpty()){
			throw new DomainException("Client must have a name");
		}
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if(age < 18){
			throw new DomainException("Client must be over 18");
		}
		this.age = age;
	}

	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
