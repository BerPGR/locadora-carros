package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
