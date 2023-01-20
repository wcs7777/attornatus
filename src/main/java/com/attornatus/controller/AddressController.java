package com.attornatus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.entity.Address;
import com.attornatus.service.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path ="/people/{resident:\\d+}/addresses")
public class AddressController {

	@Autowired
	private AddressService service;

	@PostMapping
	public Address create(
		@PathVariable("resident") Long resident,
		@Valid @RequestBody Address address
	) {
		return this.service.create(resident, address);
	}

	@PutMapping(path = "{id:\\d+}")
	public Address update(
		@PathVariable("resident") Long resident,
		@PathVariable("id") Long id,
		@Valid @RequestBody Address address
	) {
		return this.service.update(resident, id, address);
	}

	@DeleteMapping(path = "{id:\\d+}")
	public Map<String, String> remove(
		@PathVariable("resident") Long resident,
		@PathVariable("id") Long id
	) {
		this.service.remove(resident, id);
		return Map.of(
			"message",
			String.format("address {%d} of person {%d} removed", id, resident)
		);
	}

	@PutMapping(path = "{id:\\d+}/main")
	public Address setMain(
		@PathVariable("resident") Long resident,
		@PathVariable("id") Long id
	) {
		return this.service.setMain(resident, id);
	}

	@GetMapping(path = "/main")
	public Address getMain(@PathVariable("resident") Long resident) {
		return this.service.getMain(resident);
	}

	@GetMapping
	public List<Address> getAll(@PathVariable("resident") Long resident) {
		return this.service.getAllByResident(resident);
	}

	@GetMapping(path = "{id:\\d+}")
	public Address getById(
		@PathVariable("resident") Long resident,
		@PathVariable("id") Long id
	) {
		return this.service.getById(resident, id);
	}

}
