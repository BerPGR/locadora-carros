package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

	// Operation getMapping by ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém um carro", notes = "Obtém um carro")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Existe uma carro") })
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCar(@PathVariable("id") int id) {

		logger.info("[ GET ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();

		try {
			Optional<Car> optionalCar = this.carService.getCar(id);

			ResponseEntity response;
			if (optionalCar.isPresent()) {

				response = ResponseEntity.ok(optionalCar.get());
			} else {

				response = ResponseEntity.noContent().build();
			}

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//GetMapping taking a random car
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Pega um carro randomico", notes = "Pega um carro randomico")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Carro randômico gerado!")
	})
	@GetMapping("/random")
	public ResponseEntity<Car> getRandomCar(){

		ResponseEntity response;
		logger.info("[ GET ] => { " + endPoint + "/random }");
		long start = System.currentTimeMillis();

		try{

			List<Car> carList = this.carService.getAll();
			Random randomId = new Random();
			int nroRandom = randomId.nextInt(carList.size());
			Optional<Car> optionalCar = Optional.empty() ;

			if (carList.size() > 0) {
				optionalCar = Optional.of(carList.get(nroRandom));
			}

			if (optionalCar.isPresent()){
				response = ResponseEntity.ok(optionalCar.get());
			}
			else{
				response = ResponseEntity.noContent().build();
			}
			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");

			return response;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//GetMapping taking only the brands
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Faz uma listagem de marcas", notes = "Faz uma listagem de marcas")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Listagem feita com sucesso.")
	})
	@GetMapping("/brands")
	public ResponseEntity<List<String>> getListBrands(){

		ResponseEntity response;
		logger.info("[ GET ] => { " + endPoint + "/brands }");
		long start = System.currentTimeMillis();

		try{
			List<String> carBrandList = new ArrayList<>(); //this.carService.getCarBrands();
			if (carBrandList.size() > 0) {
				response = ResponseEntity.ok(carBrandList);
			} else  {
				response = ResponseEntity.noContent().build();
			}
		}
		catch(Exception e){

			return ResponseEntity.unprocessableEntity().build();
		}

		long end = System.currentTimeMillis();
		logger.debug("O tempo de execução foi de " + (end-start) + " ms");
		return response;

	}

	// Operation PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere um carro", notes = "Insere um carro", response = Car.class )
	@ApiResponses({
			@ApiResponse(code = 204, message = "Inclusão com sucesso de um carro")
	})
	@PostMapping
	public ResponseEntity<Car> addCar(@RequestBody Car car) {

		//REPLICATE IN ALL ENDPOINTS
		logger.info("[ POST ] => { " + endPoint +" }");
		long start = System.currentTimeMillis();

		try {
			ResponseEntity response = ResponseEntity.created(new URI(endPoint)).body(this.carService.save(car));

			long end = System.currentTimeMillis();
			long time = end - start;
			logger.debug("O tempo de execução foi de " + time + " ms");

			return response;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(car);
		}
	}

	//Operation PutMapping
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um carro", notes = "Atualiza um carro")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de um carro")
	})
	@PutMapping
	public ResponseEntity<Car> editCar(@RequestBody Car car) {

		logger.info("[ PUT ] : { " + endPoint + " }");

		long start = System.currentTimeMillis();

		try {
			ResponseEntity response = ResponseEntity.ok(this.carService.updateCar(car));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");

			return response;
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(car);
		}
	}

	//Operation PutMapping by ID
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um carro por id", notes = "Atualiza um carro por id")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de um carro")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Car> editCar(@RequestBody Car car, @PathVariable int id) {


		logger.info("[ PUT ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();

		try {
			if (car.getId() == 0) {
				car.setId(id);
			}
			ResponseEntity response = ResponseEntity.ok(this.carService.updateCar(car));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");

			return response;
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(car);
		}
	}

}
