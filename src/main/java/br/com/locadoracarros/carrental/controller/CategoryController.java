package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Category;
import br.com.locadoracarros.carrental.service.CategoryService;
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
@RequestMapping(CategoryController.ENDPOINT)
public class CategoryController {

	//endpoint for CategoryController
	final static String ENDPOINT = "/category";

	//logger for CategoryController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	//autowired for CategoryService
	@Autowired
	CategoryService categoryService;

	//first GetMapping
	@ApiOperation(value = "Lista todas as categorias", notes = "Lista todas as categorias",
			response = Category.class, responseContainer = "Page")
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
					defaultValue = "carType",
					value = "attribute",
					required = false) String attribute) {
		Page<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT));
		long start = System.currentTimeMillis();

		response = this.categoryService.getAll(page, size, sort, q, attribute);
		logger.debug(LoggerUtils.calculateExecutionTime(start));

		return response;
	}

	//Operation GetMapping for one category based on ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Obtém uma categoria", notes = "Obtém uma categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Existe uma categoria")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") int id) {

		ResponseEntity<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT, "/{id}"));
		long start = System.currentTimeMillis();

		try {
			Optional<Category> optionalCategory = this.categoryService.getCategory(id);

			if (optionalCategory.isPresent()) {

				response = ResponseEntity.ok(optionalCategory.get());

			} else {

				response = ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().build();
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//GetMapping taking a random category
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Pega uma categoria randomica", notes = "Pega uma categoria randomica")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Categoria ranômica gerada!")
	})
	@GetMapping("/random")
	public ResponseEntity<Category> getRandomCategory() {

		ResponseEntity<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("GET", ENDPOINT, "/random"));
		long start = System.currentTimeMillis();

		try {
			List<Category> categoryList = this.categoryService.getAll();
			Random randomId = new Random();
			int nroRandom = randomId.nextInt(categoryList.size());
			Optional<Category> optionalCategory = Optional.empty();

			if (categoryList.size() > 0) {
				optionalCategory = Optional.of(categoryList.get(nroRandom));
			}

			if (optionalCategory.isPresent()) {
				response = ResponseEntity.ok(optionalCategory.get());
			} else {
				response = ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().build();
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation postMapping to add categories
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Insere uma categoria", notes = "Insere uma caregoria", response = Category.class)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Categoria inserida com sucesso")
	})
	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {

		ResponseEntity<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("POST", ENDPOINT));
		long start = System.currentTimeMillis();

		try {
			response = ResponseEntity.created(new URI(ENDPOINT)).body(this.categoryService.save(category));
		} catch (Exception e) {
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(category);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation PutMapping to update a category
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza uma categoria", notes = "Atualiza uma categoria")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de uma categoria")
	})
	@PutMapping
	public ResponseEntity<Category> editCategory(@RequestBody Category category) {

		ResponseEntity<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("PUT", ENDPOINT));
		long start = System.currentTimeMillis();

		try {
			response = ResponseEntity.ok(this.categoryService.updateCategory(category));
		} catch (NotFoundException e) {
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(category);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}

	//Operation Putmapping to update category by id
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Atualiza uma categoria por id", notes = "Atualiza uma categoria por id")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Atualização com sucesso de uma categoria")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Category> editCategory(@RequestBody Category category, @PathVariable int id) {

		ResponseEntity<Category> response;
		logger.info(LoggerUtils.notificationEndpointRequested("PUT", ENDPOINT, "/{id}"));
		long start = System.currentTimeMillis();

		try {
			if (category.getId() == 0) category.setId(id);

			response = ResponseEntity.ok(this.categoryService.updateCategory(category));
		} catch (NotFoundException e) {
			LoggerUtils.printStackTrace(e, true);
			response = ResponseEntity.unprocessableEntity().body(category);
		}

		logger.debug(LoggerUtils.calculateExecutionTime(start));
		return response;
	}
}