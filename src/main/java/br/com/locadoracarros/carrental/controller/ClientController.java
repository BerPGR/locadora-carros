package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.service.ClientService;
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
@RequestMapping("/client")
public class ClientController {

	//endpoint for ClientController
	private final String endPoint = "/client";

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

		long start = System.currentTimeMillis();
		logger.info("[ GET ] => { " + endPoint + " }");
		long end = System.currentTimeMillis();

		logger.debug("O tempo de execução foi de " + (end-start) + " ms.");

		return this.clientService.getAll(page, size, sort, q, attribute);
	}

	//Operation GetMapping by ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém um cliente", notes = "Obtém um cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Existe uma cliente") })
	@GetMapping("/{id}")
	public ResponseEntity<Client> getClient(@PathVariable("id") int id){

		logger.info("[ GET ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();

		try{
			Optional<Client> optionalClient = this.clientService.getClient(id);

			ResponseEntity response;

			if (optionalClient.isPresent()){

				response = ResponseEntity.ok(optionalClient.get());
			}
			else{

				response = ResponseEntity.noContent().build();

			}
			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");

			return response;
		} catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//Operation PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere um cliente", notes = "Insere um cliente", response = Client.class)
	@ApiResponses({
		@ApiResponse(code = 204, message = "Inclusão com sucesso de um cliente.")
	})
	@PostMapping
	public ResponseEntity<Client> addClient(@RequestBody Client client){

		logger.info("[ POST ] => { " + endPoint + " }");
		long start = System.currentTimeMillis();
		try{
			ResponseEntity response = ResponseEntity.created(new URI(endPoint)).body(this.clientService.save(client));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");
			return response;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(client);
		}
	}

	//Operation PutMapping
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um cliente", notes = "Atualiza um cliente.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de um cliente.")
	})
	@PutMapping
	public ResponseEntity<Client> editClient(@RequestBody Client client){

		logger.info("[ PUT ] => { " + endPoint + " }");
		long start = System.currentTimeMillis();
		try{
			ResponseEntity response = ResponseEntity.ok(this.clientService.updateClient(client));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");
			return response;
		}
		catch(NotFoundException e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(client);
		}
	}

	//Operation PutMapping by ID
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza um cliente por id", notes = "Atualiza um cliente por id.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização de carro por id feita com sucesso.")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Client> editClient(@RequestBody Client client, @PathVariable int id){

		logger.info("[ PUT ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();
		try{
			if (client.getId() == 0) {
				client.setId(id);
			}
			ResponseEntity response = ResponseEntity.ok(this.clientService.updateClient(client));

			long end = System.currentTimeMillis();

			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");
			return response;
		}
		catch(NotFoundException e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(client);
		}
	}
}


