package com.attornatus.service;

import java.time.LocalDate;
import java.util.List;

import com.attornatus.entity.Person;

import jakarta.validation.Valid;

public interface PersonService {

	public Person create(@Valid final Person person);
	public Person update(final Long id, @Valid final Person person);
	public List<Person> getAll();
	public Person getById(final Long id);
	public List<Person> getByNameContaining(final String name);
	public List<Person> getByBirth(final LocalDate birth);
	public void remove(Long id);

}
