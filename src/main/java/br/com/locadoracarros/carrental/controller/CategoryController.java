package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Category;
import br.com.locadoracarros.carrental.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

	//category endpoint
	private final String endPoint = "/category";

	//logger for category
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	//autowired for CategoryService
	@Autowired
	CategoryService categoryService;


	//first GetMapping
	@ApiOperation(value = "Lista todas as categorias", notes = "Lista todas as categorias",
			response = Car.class, responseContainer = "Page")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Categorias listadas com sucesso")
	})
	@GetMapping
	public Page<Category> getAllCategories(
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
		//logger in GetMapping
		logger.info("[ GET ] => { " + endPoint + " } ");
		long start = System.currentTimeMillis();

		long end = System.currentTimeMillis();
		logger.debug("O tempo de execução foi de " + (end-start) + " ms.");

		return this.categoryService.getAll(page, size, sort, q, attribute);
	}


	//Operation GetMapping for one category based on ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém uma categoria", notes = "Obtém uma categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Existe uma categoria")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") int id){

		//log in GetMapping by id
		logger.info("[ GET ] => { " + endPoint + "/{id} }");
		long start = System.currentTimeMillis();

		try {
			Optional<Category> optionalCategory = this.categoryService.getCategory(id);

			ResponseEntity response;
			if (optionalCategory.isPresent()) {

				response = ResponseEntity.ok(optionalCategory.get());

			} else {

				response = ResponseEntity.noContent().build();

			}
			long end = System.currentTimeMillis();

			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	//Operation postMapping to add categories
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere uma categoria", notes = "Insere uma caregoria", response = Category.class)
	@ApiResponses(value ={
			@ApiResponse(code = 204, message = "Categoria inserida com sucesso")
	})
	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody Category category){

		//log in PostMapping
		logger.info("[ POST ] => { " + endPoint + " }");
		long start = System.currentTimeMillis();

		try {
			ResponseEntity response = ResponseEntity.created(new URI(endPoint)).body(this.categoryService.save(category));

			long end = System.currentTimeMillis();
			logger.debug("O tempo de execução foi de " + (end-start) + " ms.");

			return response;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(category);
		}
	}

	//Operation PutMapping to update a category
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza uma categoria", notes = "Atualiza uma categoria")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de uma categoria")
	})
	@PutMapping
	public ResponseEntity<Category> editCategory(@RequestBody Category category) {


		try {
			ResponseEntity response = ResponseEntity.ok(this.categoryService.updateCategory(category));


			return response;
		} catch (NotFoundException e) {

			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(category);
		}
	}

	//Operation Putmapping to update category by id
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza uma categoria por id", notes = "Atualiza uma categoria por id")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de uma categoria")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Category> editCategoty(@RequestBody Category category, @PathVariable int id) {

		try {
			if (category.getId() == 0)
				category.setId(id);
			ResponseEntity response = ResponseEntity.ok(this.categoryService.updateCategory(category));

			return response;
		} catch (NotFoundException e) {

			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(category);
		}
	}
}
