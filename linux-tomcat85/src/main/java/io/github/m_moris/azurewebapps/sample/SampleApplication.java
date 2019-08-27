package io.github.m_moris.azurewebapps.sample;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SampleApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SampleApplication.class);
	}

	@RequestMapping("/hello")
	Map<String, String> hello(@RequestParam(name = "name", required = false) String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Title", "Sample Application");
		map.put("Message", "Hello " + (name == null ? "" : name));
		map.put("Request", (new Date().toString()));
		return map;
	}
}