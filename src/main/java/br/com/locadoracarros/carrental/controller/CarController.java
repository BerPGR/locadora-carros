package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

	private final String endPoint = "/car";

	@Autowired
	CarService carService;


	@ApiOperation(value = "Lista todos os carros", notes = "Lista todos os carros",
			response = Car.class, responseContainer = "Page")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Avaliações Listadas com sucesso"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public Page<Car> getAllCars(
	@RequestParam(
			value = "page",
			required = false,
			defaultValue = "0") int page,
	@RequestParam(
			value = "size",
			required = false,
			defaultValue = "15") int size,
	@RequestParam(
			defaultValue = "desc",
			value = "sort",
			required = false) String sort,
	@RequestParam(
			defaultValue = "",
			value = "q",
			required = false) String q,
	 @RequestParam(
	 		defaultValue = "model",
			value = "attribute",
			required = false) String attribute)
	{

		return this.carService.getAll(page, size, sort, q, attribute);
	}
}
