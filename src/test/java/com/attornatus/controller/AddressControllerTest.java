package com.attornatus.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;
import com.attornatus.service.AddressService;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AddressService service;
	private Person resident = Person.builder().id(1L).build();
	private Address address1 = Address
		.builder()
		.id(1L)
		.cep("11111111")
		.city("City 1")
		.street("Street 1")
		.resident(this.resident)
		.number("111")
		.build();
	private Address address2 = Address
		.builder()
		.id(2L)
		.cep("22222222")
		.city("City 2")
		.street("Street 2")
		.number("222")
		.isMain(true)
		.resident(this.resident)
		.build();

	@Test
	void testCreate() throws Exception {
		when(
			this.service.create(
				1L,
				Address
					.builder()
					.cep(this.address1.getCep())
					.city(this.address1.getCity())
					.street(this.address1.getStreet())
					.number(this.address1.getNumber())
					.build()
			)
		)
			.thenReturn(this.address1);
		this.mockMvc.perform(
			post("/people/1/addresses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					String.format("""
						{
							"cep": "%s",
							"city": "%s",
							"street": "%s",
							"number": "%s"
						}
					""",
						this.address1.getCep(),
						this.address1.getCity(),
						this.address1.getStreet(),
						this.address1.getNumber()
					)
				)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.resident.name")
					.value(this.resident.getName())
			);
	}

	@Test
	void testGetAll() throws Exception {
		when(this.service.getAllByResident(1L))
			.thenReturn(List.of(this.address1, this.address2));
		this.mockMvc.perform(
			get("/people/1/addresses")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$..id", hasItems(1, 2)));
	}

	@Test
	void testGetById() throws Exception {
		when(this.service.getById(1L, 1L))
			.thenReturn(this.address1);
		this.mockMvc.perform(
			get("/people/1/addresses/1")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.city").value(this.address1.getCity())
			);
	}

	@Test
	void testGetMain() throws Exception {
		when(this.service.getMain(1L))
			.thenReturn(this.address2);
		this.mockMvc.perform(
			get("/people/1/addresses/main")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.isMain").value(true)
			);
	}

	@Test
	void testRemove() throws Exception {
		doNothing().when(this.service).remove(1L, 1L);
		this.mockMvc.perform(
			delete("/people/1/addresses/1")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$.message",
					containsStringIgnoringCase("removed")
				)
			);
		verify(this.service).remove(1L, 1L);
	}

	@Test
	void testSetMain() throws Exception {
		when(this.service.setMain(1L, 2L)).thenReturn(this.address2);
		this.mockMvc.perform(
			put("/people/1/addresses/2/main")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.isMain").value(true)
			);
	}

	@Test
	void testUpdate() throws Exception {
		final Address updated = Address
			.builder()
			.cep(this.address1.getCep())
			.city(this.address1.getCity())
			.street("Street 1 Edited")
			.number(this.address1.getNumber())
			.build();
		when(this.service.update(1L, 1L, updated))
			.thenReturn(updated);
		this.mockMvc.perform(
			put("/people/1/addresses/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					String.format("""
						{
							"cep": "%s",
							"city": "%s",
							"street": "%s",
							"number": "%s"
						}
					""",
						updated.getCep(),
						updated.getCity(),
						updated.getStreet(),
						updated.getNumber()
					)
				)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$.street",
					containsStringIgnoringCase("edited")
				)
			);
	}

}
