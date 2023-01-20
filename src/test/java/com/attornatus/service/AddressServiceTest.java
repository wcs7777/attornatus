package com.attornatus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;
import com.attornatus.repository.AddressRepository;
import com.attornatus.repository.PersonRepository;

@SpringBootTest
public class AddressServiceTest {

	@InjectMocks
	private AddressServiceDefault service;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private PersonRepository personRepository;
	private final Person resident = Person.builder().id(1L).build();
	private Address address1 = Address
			.builder()
			.id(1L)
			.cep("12345678")
			.city("City 1")
			.number("123")
			.street("Street 1")
			.isMain(false)
			.resident(this.resident)
			.build();
	private Address address2 = Address
			.builder()
			.id(2L)
			.cep("22345678")
			.city("City 2")
			.number("223")
			.street("Street 2")
			.isMain(true)
			.resident(this.resident)
			.build();

	@BeforeEach
	void setUp() {
		when(
			this.personRepository.findById(1L)
		)
			.thenReturn(Optional.of(this.resident));
		when(this.addressRepository.findByIdAndResident(1L, this.resident))
			.thenReturn(Optional.of(this.address1));
		doNothing()
			.when(this.addressRepository)
			.resetAllIsMain(this.resident);
	}

	@Test
	void testCreate() {
		when(
			this.addressRepository.save(any(Address.class))
		)
			.thenReturn(this.address1);
		assertThat(
			this.service.create(
				1L,
				Address
					.builder()
					.cep("12345678")
					.city("City 1")
					.number("123")
					.street("Street 1")
					.build()
			)
		)
			.isEqualTo(this.address1);
	}

	@Test
	void testGetAllByResident() {
		when(this.addressRepository.findByResident(this.resident))
			.thenReturn(List.of(this.address1, this.address2));
		assertThat(this.service.getAllByResident(1L))
			.hasSize(2);
	}

	@Test
	void testGetById() {
		when(this.addressRepository.findByIdAndResident(1L, this.resident))
			.thenReturn(Optional.of(this.address1));
		assertThat(this.service.getById(1L, 1L))
			.isEqualTo(this.address1);
	}

	@Test
	void testGetMain() {
		when(this.addressRepository.findByResidentAndIsMainTrue(this.resident))
			.thenReturn(Optional.of(this.address2));
		assertThat(this.service.getMain(1L)).isEqualTo(this.address2);
	}

	@Test
	void testRemove() {
		when(this.addressRepository.findByIdAndResident(1L, this.resident))
			.thenReturn(Optional.of(this.address1));
		this.service.remove(1L, 1L);
		verify(this.addressRepository).delete(this.address1);
	}

	@Test
	void testSetMain() {
		final Address updated = this.address1
			.toBuilder()
			.isMain(true)
			.build();
		when(this.addressRepository.save(updated))
			.thenReturn(updated);
		assertThat(this.service.setMain(1L, 1L).getIsMain())
			.isTrue();
	}

	@Test
	void testUpdate() {
		final Address updated = this.address1
			.toBuilder()
			.cep("44444444")
			.build();
		when(this.addressRepository.save(updated))
			.thenReturn(updated);
		assertThat(this.service.update(1L, 1L, updated))
			.isEqualTo(updated);
	}

	@Test
	void testId2ForeignKey() {
		assertThat(this.service.id2ForeignKey(1L))
			.isEqualTo(this.resident);
	}

}
