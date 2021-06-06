package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Tenancy;
import br.com.locadoracarros.carrental.service.TenancyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenancy")
public class TenancyController {

	// endpoint for TenancyController
	private final String endPoint = "/tenancy";

	// Logger for TenancyController
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TenancyService tenancyService;

	// Operation GetMapping
	@ApiOperation(value = "Lista todas as locações", notes = "Lista todas as locações",
	response = Tenancy.class, responseContainer = "Page")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Locações listadas com sucesso")
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

		return this.tenancyService.getAll(page, size, sort, q, attribute);
	}


}
