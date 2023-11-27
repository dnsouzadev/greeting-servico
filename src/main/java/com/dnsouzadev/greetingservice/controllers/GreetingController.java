package com.dnsouzadev.greetingservice.controllers;

import com.dnsouzadev.greetingservice.configuration.GreetingConfiguration;
import com.dnsouzadev.greetingservice.model.Greeting;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "%s, %s!";
	private final AtomicLong counter = new AtomicLong();

	private GreetingConfiguration configuration;

	public GreetingController(GreetingConfiguration configuration) {
		this.configuration = configuration;
	}

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue = "") String name) {

		if (name.isEmpty()) name = configuration.getDefaultValue();

		return new Greeting(counter.incrementAndGet(), String.format(template, configuration.getGreeting(), name));
	}
}
