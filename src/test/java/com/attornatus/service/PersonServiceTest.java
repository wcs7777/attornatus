package com.attornatus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.attornatus.entity.Person;
import com.attornatus.repository.AddressRepository;
import com.attornatus.repository.PersonRepository;

@SpringBootTest
public class PersonServiceTest {

	@InjectMocks
	private PersonServiceDefault service;
	@Mock
	private PersonRepository personRepository;
	@Mock
	private AddressRepository addressRepository;
	private LocalDate birth1 = LocalDate.of(2002, 12, 12);
	private LocalDate birth2 = LocalDate.of(1980, 7, 6);
	private Person person1 = Person
		.builder()
		.id(1L)
		.name("First Person")
		.birth(this.birth1)
		.build();
	private Person person2 = Person
		.builder()
		.id(2L)
		.name("Second Person")
		.birth(this.birth2)
		.build();

	@Test
	void testCreate() {
		when(this.personRepository.save(this.person1)).thenReturn(this.person1);
		assertThat(this.service.create(this.person1)).isEqualTo(this.person1);
	}

	@Test
	void testGetAll() {
		when(this.personRepository.findAll())
			.thenReturn(List.of(this.person1, this.person2));
		assertThat(this.service.getAll()).hasSize(2);
	}

	@Test
	void testGetByBirth() {
		when(this.personRepository.findByBirth(this.birth1))
			.thenReturn(List.of(this.person1));
		assertThat(this.service.getByBirth(this.birth1))
			.hasSize(1);
	}

	@Test
	void testGetById() {
		when(this.personRepository.findById(1L))
			.thenReturn(Optional.of(this.person1));
		assertThat(this.service.getById(1L))
			.isEqualTo(this.person1);
	}

	@Test
	void testGetByNameContaining() {
		when(this.personRepository.findByNameContainingIgnoreCase("person"))
			.thenReturn(List.of(this.person1, this.person2));
		assertThat(this.service.getByNameContaining("person"))
			.hasSize(2);
	}

	@Test
	void testRemove() {
		when(this.personRepository.findById(1L))
			.thenReturn(Optional.of(this.person1));
		doNothing().when(this.addressRepository).deleteByResident(this.person1);
		doNothing().when(this.personRepository).delete(this.person1);
		this.service.remove(1L);
		verify(this.addressRepository).deleteByResident(this.person1);
		verify(this.personRepository).delete(this.person1);
	}

	@Test
	void testUpdate() {
		final Person updated = this.person1
			.toBuilder()
			.name("First Person Edited")
			.build();
		when(this.personRepository.findById(1L))
			.thenReturn(Optional.of(this.person1));
		when(this.personRepository.save(updated))
			.thenReturn(updated);
		assertThat(this.service.update(1L, updated))
			.isEqualTo(updated);
	}

}
