package com.attornatus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@Sql(
	scripts = { "/com/attornatus/resources/test-populate.sql" },
	executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository repository;

	@Test
	void testFindByBirth() {
		assertThat(
			this.repository.findByBirth(LocalDate.of(2002, 12, 12))
		)
			.hasSize(1);
	}

	@Test
	void testFindByNameContainingIgnoreCase() {
		assertThat(
			this.repository.findByNameContainingIgnoreCase("person")
		)
			.hasSize(2);
	}

}
