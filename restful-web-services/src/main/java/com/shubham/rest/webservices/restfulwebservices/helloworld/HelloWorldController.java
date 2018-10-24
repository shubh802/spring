package com.shubham.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	//GET
	//URI -/hello-world
	//method - "Hello World"
//	@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
				
	}
	
	//hello-world-bean
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
				
	}
	
	@GetMapping(path="/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
	}

	// In spring we can get the locale from LocaleContextHolder
	@GetMapping(path="/hello-world-internalionalized")
	public String helloWorldInternationalized() {
	
		return messageSource.getMessage("good.morning.message", null,
				LocaleContextHolder.getLocale());
	}
}
