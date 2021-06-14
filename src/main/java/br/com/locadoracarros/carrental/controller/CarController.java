package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

	//endpoint for CarController
	private final String endPoint = "/car";

	//Logger for CarController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CarService carService;


	//Operation getMapping
	@ApiOperation(value = "Lista todos os carros", notes = "Lista todos os carros",
			response = Car.class, responseContainer = "Page")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Carros listados com sucesso")
	})
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
		logger.info("[ GET ] => { " + endPoint + " }");
		long end = System.currentTimeMillis();

		logger.debug("O tempo de execução foi de " + (end-start) + " ms");

		return this.carService.getAll(page, size, sort, q, attribute);
	}
}
