package com.attornatus.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;

@DataJpaTest
@Sql(
	scripts = { "/com/attornatus/resources/test-populate.sql" },
	executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
public class AddressRepositoryTest {

	@Autowired
	private AddressRepository repository;

	@Test
	void testDeleteByResident() {
		this.repository.deleteByResident(Person.builder().id(1L).build());
		assertThat(
			this.repository
				.findByResident(Person.builder().id(1L).build())
		)
			.isEmpty();
	}

	@Test
	void testFindByIdAndResident() {
		assertThat(
			this.repository
				.findByIdAndResident(2L, Person.builder().id(1L).build())
				.isPresent()
		)
			.isTrue();
	}

	@Test
	void testFindByResident() {
		assertThat(
			this.repository
				.findByResident(Person.builder().id(1L).build())
		)
			.hasSize(2);
	}

	@Test
	void testFindByResidentAndIsMainTrue() {
		final Optional<Address> address = this.repository
			.findByResidentAndIsMainTrue(
				Person.builder().id(1L).build()
			);
		assertTrue(address.isPresent() && address.get().getId() == 2);
	}

	@Test
	void testResetAllIsMain() {
		this.repository.resetAllIsMain(
			Person.builder().id(1L).build()
		);
		assertThat(
			this.repository
				.findByResidentAndIsMainTrue(Person.builder().id(1L).build())
				.isEmpty()
		)
			.isTrue();
	}

}
