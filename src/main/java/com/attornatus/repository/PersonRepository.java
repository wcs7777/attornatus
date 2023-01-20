package com.attornatus.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.entity.Person;

public interface PersonRepository
	extends JpaRepository<Person, Long> {

	List<Person> findByNameContainingIgnoreCase(final String name);
	List<Person> findByBirth(final LocalDate birth);

}
