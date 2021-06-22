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

	@ApiModelProperty(notes = "Sexo do cliente", required = true)
	private String gender;

	@Column(unique = true, nullable = false)
	@ApiModelProperty(notes = "CPF do cliente", required = true)
	private String cpf;

	@ApiModelProperty(notes = "Celular do cliente", required = true)
	private String number;

	@ApiModelProperty(notes = "CNH do cleinte", required = true)
	private String cnh;

	public Client(){}

	public Client(int id, String name, int age, String gender, String cpf, String number, String cnh) {
		this.setName(name);
		this.setAge(age);
		this.setGender(gender);
		this.setCpf(cpf);
		this.setNumber(number);
		this.setCnh(cnh);
	}

	public void setName(String name) {
		if(name.equals(null) || name.isEmpty()){
			throw new DomainException("Client must have a name");
		}
		this.name = name;
	}

	public void setAge(int age) {
		if(age < 18){
			throw new DomainException("Client must be over 18");
		}
		this.age = age;
	}

	public void setCpf(String cpf) {
		if (cpf.length() > 11 || cpf.length() < 11){
			throw new DomainException("CPF length must be 11.");
		}
		else if (cpf.equals(null) || cpf.isEmpty()){
			throw new DomainException("Client must have a CPF.");
		}
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", cnh=" + cnh +
				", gender='" + gender + '\'' +
				", cpf='" + cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
				+ cpf.substring(6, 9) + "-" + cpf.substring(9) + '\''
				+ '}';
	}

	public void setNumber(String number) {
		if(number.length() < 11 || number.isEmpty() || number.equals(null)){
			throw new DomainException("That's not a valid cellphone number.");
		}
		this.number = number;
	}

	public void setCnh(String cnh) {
		if(cnh.isEmpty() || cnh.equals(null)){
			throw new DomainException("Must be a valid CNH");
		}
		this.cnh = cnh;
	}
}
