package com.attornatus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.entity.Person;
import com.attornatus.repository.AddressRepository;
import com.attornatus.repository.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PersonServiceDefault implements PersonService {

	@Autowired
	private PersonRepository repository;
	@Autowired
	private AddressRepository addressesRepository;

	@Override
	public Person create(final Person person) {
		return this.repository.save(person);
	}

	@Override
	public Person update(final Long id, final Person person) {
		final Optional<Person> found = this.repository.findById(id);
		if (found.isEmpty()) {
			throw new EntityNotFoundException();
		}
		person.setId(id);
		return this.repository.save(person.toBuilder().id(id).build());
	}

	@Override
	@Transactional
	public void remove(Long id) {
		final Person person = this.getById(id);
		this.addressesRepository.deleteByResident(person);
		this.repository.delete(person);
	}

	@Override
	public List<Person> getAll() {
		return this.repository.findAll();
	}

	@Override
	public Person getById(final Long id) {
		return this.repository
			.findById(id)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public List<Person> getByNameContaining(final String name) {
		return this.repository
			.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<Person> getByBirth(final LocalDate birth) {
		return this.repository.findByBirth(birth);
	}

}
