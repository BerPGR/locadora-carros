package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Category;
import br.com.locadoracarros.carrental.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		try {
			Optional<Category> optionalCategory = this.categoryService.getCategory(id);

			ResponseEntity response;
			if (optionalCategory.isPresent()) {

				response = ResponseEntity.ok(optionalCategory.get());

			} else {

				response = ResponseEntity.noContent().build();

			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().build();
		}
	}
}
