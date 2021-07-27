package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.service.ClientService;
import br.com.locadoracarros.carrental.util.LoggerUtils;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(ClientController.ENDPOINT)
public class ClientController {

	//endpoint for ClientController
	final static String ENDPOINT = "/client";

	//created logger for ClientController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ClientService clientService;

	//Operation GetMapping
	@ApiOperation(value = "Lista todos os clientes", notes = "Lista todos os clientes",
			response = Client.class, responseContainer = "Page")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Clientes listados com sucesso")
	})
	@GetMapping
	public Page<Client> getAllClients(
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
					defaultValue = "name",
					value = "attribute",
					required = false) String attribute)
	{

		Page<Client> response;
		long start = System.currentTimeMillis();
		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT));

		response = this.clientService.getAll(page, size, sort, q, attribute);
		logger.debug(LoggerUtils.calculateExecutionTime(start));

		return response;
	}

	//Operation GetMapping by ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém um cliente", notes = "Obtém um cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Existe uma cliente") })
	@GetMapping("/{id}")
	public ResponseEntity<Client> getClient(@PathVariable("id") int id){

		ResponseEntity<Client> response;

		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT, "/{id}"));
		long start = System.currentTimeMillis();

		try{
			Optional<Client> optionalClient = this.clientService.getClient(id);

			if (optionalClient.isPresent()){

				response = ResponseEntity.ok(optionalClient.get());
			}
			else{

				response = ResponseEntity.noContent().build();
			}
		} catch(Exception e){
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().build();
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//GetMapping taking a random category
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Pega um cliente randomico", notes = "Pega um cliente randomico")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cliente randomico gerado!")
	})
	@GetMapping("/random")
	public ResponseEntity<Client> getRandomClient(){

		ResponseEntity<Client> response;
		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT, "/random"));
		long start = System.currentTimeMillis();

		try{

			List<Client> clientList = this.clientService.getAll();
			Random randomId = new Random();
			int nroRandom = randomId.nextInt(clientList.size());
			Optional<Client> optionalClient = Optional.empty();

			if (clientList.size() > 0){
				optionalClient = Optional.of(clientList.get(nroRandom));
			}

			if (optionalClient.isPresent()){
				response = ResponseEntity.ok(optionalClient.get());
			}
			else{
				response = ResponseEntity.noContent().build();
			}
		}
		catch(Exception e){
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().build();
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere um cliente", notes = "Insere um cliente", response = Client.class)
	@ApiResponses({
		@ApiResponse(code = 204, message = "Inclusão com sucesso de um cliente.")
	})
	@PostMapping
	public ResponseEntity<Client> addClient(@RequestBody Client client){

		ResponseEntity<Client> response;

		logger.info(LoggerUtils.notificationEndpointRequested("POST", ENDPOINT));
		long start = System.currentTimeMillis();

		try{
			response = ResponseEntity.created(new URI(ENDPOINT)).body(this.clientService.save(client));
		}
		catch(Exception e){
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(client);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation PutMapping
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um cliente", notes = "Atualiza um cliente.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de um cliente.")
	})
	@PutMapping
	public ResponseEntity<Client> editClient(@RequestBody Client client){

		ResponseEntity<Client> response;

		logger.info(LoggerUtils.notificationEndpointRequested("PUT", ENDPOINT));
		long start = System.currentTimeMillis();

		try{
			response = ResponseEntity.ok(this.clientService.updateClient(client));
		}
		catch(NotFoundException e){
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(client);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation PutMapping by ID
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um cliente por id", notes = "Atualiza um cliente por id.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização de carro por id feita com sucesso.")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Client> editClient(@RequestBody Client client, @PathVariable int id){

		ResponseEntity<Client> response;

		logger.info(LoggerUtils.notificationEndpointRequested("PUT", ENDPOINT, "/{id}"));
		long start = System.currentTimeMillis();

		try{
			if (client.getId() == 0) {
				client.setId(id);
			}
			response = ResponseEntity.ok(this.clientService.updateClient(client));;
		}
		catch(NotFoundException e){
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(client);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}
}