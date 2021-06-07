package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.entities.Tenancy;
import br.com.locadoracarros.carrental.service.TenancyService;
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
import java.util.Optional;

@RestController
@RequestMapping("/tenancy")
public class TenancyController {

	// endpoint for TenancyController
	final String endPoint = "/tenancy";

	// Logger for TenancyController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TenancyService tenancyService;

	//TODO put logger on requests

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
	public ResponseEntity<Tenancy> getTenancy(@PathVariable("id") int id){

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

	// Operation PostMapping in TenancyController
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adiciona uma locação.", notes = "Adiciona uma locação.", response = Tenancy.class)
	@ApiResponses({
			@ApiResponse(code = 204, message = "Inclusão com sucesso de uma locação.")
	})
	@PostMapping
	public ResponseEntity<Tenancy> addTenancy(@RequestBody Tenancy tenancy){

		try{
			ResponseEntity response = ResponseEntity.created(new URI(endPoint)).body(this.tenancyService.save(tenancy));
			return response;
		}
		catch(Exception e){
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

		try{
			ResponseEntity response = ResponseEntity.ok(this.tenancyService.updateTenancy(tenancy));
			return response;
		}
		catch(NotFoundException e){
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

		try{
			if (tenancy.getId() == 0){
				tenancy.setId(id);
			}

			ResponseEntity response = ResponseEntity.ok(this.tenancyService.updateTenancy(tenancy));
			return response;
		}
		catch(NotFoundException e){
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(tenancy);
		}
	}

	//TODO fix last 2 operations

	//GetMapping taking car
	//TODO operation get by car
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Obtém uma locação por carro", notes = "Obtém uma locação por carro")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locação por carro obtida com sucesso.")
	})
	@GetMapping
	public ResponseEntity<Tenancy> getTenancyByCar(@RequestBody Car car, Tenancy tenancy){
		try{
			Optional<Tenancy> optionalTenancy = this.tenancyService.getTenancy(car.getId());
			ResponseEntity response;

			if (optionalTenancy.isPresent()){
				response = ResponseEntity.ok(optionalTenancy.get());
			}
			else{
				response = ResponseEntity.noContent().build();
			}

			return response;
		}
		catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//GetMapping taking client
	//TODO operation get by client
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Obtém uma locação por cliente", notes = "Obtém uma locação por cliente")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locação por cliente obtida com sucesso.")
	})
	@GetMapping
	public ResponseEntity<Tenancy> getTenancyByClient(@RequestBody Client client, Tenancy tenancy){
		try{
			Optional<Tenancy> optionalTenancy = this.tenancyService.getTenancy(client.getId());
			ResponseEntity response;

			if (optionalTenancy.isPresent()){
				response = ResponseEntity.ok(optionalTenancy.get());
			}
			else{
				response = ResponseEntity.noContent().build();
			}

			return response;
		}
		catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}
}
