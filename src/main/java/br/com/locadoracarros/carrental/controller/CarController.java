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
			@ApiResponse(code = 204, message = "Avaliações Listadas com sucesso")
	}
			)
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
		long start = System.currentTimeMillis();
		logger.info("[ GET ] => {/car}");
		long end = System.currentTimeMillis();

		logger.debug("O tempo de execução foi de " + (end-start) + " ms");

		return this.carService.getAll(page, size, sort, q, attribute);
	}
}
