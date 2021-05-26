package br.com.locadoracarros.carrental.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
public class LoggingController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/")
	public String index() {
		logger.trace("A TRACE Message");
		logger.debug("Running with Spring Boot v2.3.1.RELEASE, Spring v5.2.7.RELEASE");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("Running with Spring Boot v2.3.1.RELEASE, Spring v5.2.7.RELEASE");

		return "Howdy! Check out the Logs to see the output...";
	}
}
