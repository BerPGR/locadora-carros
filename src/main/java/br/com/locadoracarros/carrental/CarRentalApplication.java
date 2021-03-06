package br.com.locadoracarros.carrental;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;


@SpringBootApplication
public class CarRentalApplication {

	/* When we debug the program, a message shows
	 on the terminal, saying: Failed to determine a suitable driver class*/
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SpringApplication.run(CarRentalApplication.class, args);
	}


}
