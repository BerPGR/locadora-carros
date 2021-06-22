package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Tenancy;
import br.com.locadoracarros.carrental.service.CarService;
import br.com.locadoracarros.carrental.service.ClientService;
import br.com.locadoracarros.carrental.service.TenancyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.json.JSONException;
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
@RequestMapping("/tenancy")
public class TenancyController {

	// endpoint for TenancyController
	final String endPoint = "/tenancy";

	// Logger for TenancyController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TenancyService tenancyService;

	@Autowired
	CarService carService;

	@Autowired
	ClientService clientService;

	// Operation GetMapping
	@ApiOperation(value = "Lista todas as locações.", notes = "Lista todas as locações.",
	response = Tenancy.class, responseContainer = "Page")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locações listadas com sucesso.")
	})
	@GetMapping
	public Page<Tenancy> getAllTenancies(
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
					defaultValue = "client",
					value = "attribute",
					required = false) String attribute)
	{
		long start = System.currentTimeMillis();
		logger.info("[ GET ] => { " + endPoint + " }");
		long end = System.currentTimeMillis();

		logger.debug("O tempo de execução foi de " + (end-start) + " ms");

		return this.tenancyService.getAll(page, size, sort, q, attribute);
	}

	//Operation GetMapping by ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém uma locação.", notes = "Obtém uma locação.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Existe uma locação.")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Tenancy>> getTenancy(@PathVariable("id") int id) throws JSONException {

		logger.info("[ GET ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();
		try{
			Optional<Tenancy> optionalTenancy = this.tenancyService.getTenancy(id);
			ResponseEntity response;

			if (optionalTenancy.isPresent()){
				response = ResponseEntity.ok(optionalTenancy.get());
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
			e.printStackTrace();

			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém uma locação randômica", notes = "Obtém uma locação randômica")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locação randômica gerada!")
	})
	@GetMapping("/random")
	public ResponseEntity<Tenancy> getRandomTenancy(){

		ResponseEntity response;
		logger.info("[ GET ] => { " + endPoint + "/random }");
		long start = System.currentTimeMillis();

		try{

			List<Tenancy> tenancyList = this.tenancyService.getAll();
			Random randomId = new Random();
			int nroRandom = randomId.nextInt(tenancyList.size());
			Optional<Tenancy> optionalTenancy = Optional.empty();

			if (tenancyList.size() > 0){
				optionalTenancy = Optional.of(tenancyList.get(nroRandom));
			}

			if (optionalTenancy.isPresent()){
				response = ResponseEntity.ok(optionalTenancy.get());
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

	//GetMapping taking client
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Obtém uma locação por cliente", notes = "Obtém uma locação por cliente")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locação por cliente obtida com sucesso.")
	})
	@GetMapping("/client/{id}")
	public ResponseEntity<List<Tenancy>> getTenancyByClient(@PathVariable("id") int id){

		ResponseEntity response;
		logger.info("[ GET ] => { " + endPoint + "/client }");
		long start = System.currentTimeMillis();

		try{
			List<Tenancy> clientTenancy = this.tenancyService.findByClient(id);

			if (clientTenancy.size() > 0){

				List<Tenancy> list = new ArrayList<>();


				response = ResponseEntity.ok(list);
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
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//GetMapping taking car
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Obtém uma locação por carro", notes = "Obtém uma locação por carro")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locação por carro obtida com sucesso.")
	})
	@GetMapping("/car/{id}")
	public ResponseEntity<Tenancy> getTenancyByCar(@PathVariable("id") int id){

		ResponseEntity response;
		logger.info("[ GET ] => { " + endPoint + "/car }");
		long start = System.currentTimeMillis();
		try{
			List<Tenancy> carTenancy = this.tenancyService.findByCar(id);

			if(carTenancy.size() > 0){

				response = ResponseEntity.ok(carTenancy);
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
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	// Operation PostMapping in TenancyController
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adiciona uma locação.", notes = "Adiciona uma locação.", response = Tenancy.class)
	@ApiResponses({
			@ApiResponse(code = 204, message = "Inclusão com sucesso de uma locação."),
			@ApiResponse(code = 422, message = "{\n" +
					"  \"message\": \"Validation Failed\",\n" +
					"  \"errors\": [\n" +
					"    {\n" +
					"      \"resource\": \"Issue\",\n" +
					"      \"field\": \"title\",\n" +
					"      \"code\": \"missing_field\"\n" +
					"    }\n" +
					"  ]\n" +
					"}")
	})
	@PostMapping
	public ResponseEntity<Tenancy> addTenancy(@RequestBody Tenancy tenancy){

		logger.info("[ POST ] => { " + endPoint +" }");
		long start = System.currentTimeMillis();
		try{
			ResponseEntity response = ResponseEntity.created(new URI(endPoint)).body(this.tenancyService.save(tenancy));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");
			return response;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(tenancy);
		}
	}

	//Operation PutMapping
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Edita uma locação.", notes = "Edita uma locação.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de uma locação.")
	})
	@PutMapping
	public ResponseEntity<Tenancy> editTenancy(@RequestBody Tenancy tenancy){

		logger.info("[ PUT ] : { " + endPoint + " }");
		long start = System.currentTimeMillis();
		try{
			ResponseEntity response = ResponseEntity.ok(this.tenancyService.updateTenancy(tenancy));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");

			return response;
		}
		catch(NotFoundException e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(tenancy);
		}
	}

	//Operation PutMapping by ID
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza uma locação por id.", notes = "Atualiza uma locação por id.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização de locação por id feita com sucesso.")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Tenancy> editTenancy(@RequestBody Tenancy tenancy, @PathVariable int id){

		logger.info("[ PUT ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();

		try{
			if (tenancy.getId() == 0){
				tenancy.setId(id);
			}

			ResponseEntity response = ResponseEntity.ok(this.tenancyService.updateTenancy(tenancy));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms");
			return response;
		}
		catch(NotFoundException e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(tenancy);
		}
	}

}
