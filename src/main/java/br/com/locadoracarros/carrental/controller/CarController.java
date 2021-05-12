package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

	@GetMapping
	public ResponseEntity<Car> getCar(){

		final Car car = new Car("Fiat", "Punto", "OMG-1234");
		return ResponseEntity.ok(car);


	}
}
