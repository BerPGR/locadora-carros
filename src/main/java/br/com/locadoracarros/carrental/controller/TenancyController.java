package br.com.locadoracarros.carrental.controller;

import br.com.locadoracarros.carrental.service.TenancyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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





}
