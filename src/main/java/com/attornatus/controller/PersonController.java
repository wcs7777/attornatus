package com.attornatus.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.entity.Person;
import com.attornatus.service.PersonService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/people")
public class PersonController {

	@Autowired
	private PersonService service;

	@PostMapping
	public Person create(@Valid @RequestBody Person person) {
		return this.service.create(person);
	}

	@PutMapping(path = "{id:[\\d+]}")
	public Person update(
		@PathVariable("id") Long id,
		@Valid @RequestBody Person person
	) {
		return this.service.update(id, person);
	}

	@DeleteMapping(path = "{id:[\\d+]}")
	public Map<String, String> remove(@PathVariable("id") Long id) {
		this.service.remove(id);
		return Map.of(
			"message",
			String.format("person {%d} removed", id)
		);
	}

	@GetMapping
	public List<Person> getAll() {
		return this.service.getAll();
	}

	@GetMapping(path = "{id:[\\d+]}")
	public Person getById(@PathVariable("id") Long id) {
		return this.service.getById(id);
	}

	@GetMapping(path = "{name:[a-zA-Z]+}")
	public List<Person> getByNameContaining(@PathVariable("name") String name) {
		return this.service.getByNameContaining(name);
	}

	@GetMapping(path = "{birth:\\d\\d\\d\\d-\\d\\d-\\d\\d}")
	public List<Person> getByBirth(@PathVariable("birth") String birth) {
		return this.service.getByBirth(LocalDate.parse(birth));
	}

}
