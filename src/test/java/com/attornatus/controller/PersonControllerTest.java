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
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attornatus.entity.Person;
import com.attornatus.service.PersonService;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonService service;
	private Person person1 = Person
		.builder()
		.id(1L)
		.name("First Person")
		.birth(LocalDate.of(2000, 6, 15))
		.build();
	private Person person2 = Person
		.builder()
		.id(2L)
		.name("Second Person")
		.birth(LocalDate.of(1982, 8, 25))
		.build();

	@Test
	void testCreate() throws Exception {
		when(
			this.service.create(
				Person
					.builder()
					.name(this.person1.getName())
					.birth(this.person1.getBirth())
					.build()
			)
		)
			.thenReturn(this.person1);
		this.mockMvc.perform(
			post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					String.format("""
						{
							"name": "%s",
							"birth": "%s"
						}
					""",
						this.person1.getName(),
						this.person1.getBirth()
					)
				)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.birth")
					.value(this.person1.getBirth().toString())
			);
	}

	@Test
	void testGetAll() throws Exception {
		when(this.service.getAll())
			.thenReturn(List.of(this.person1, this.person2));
		this.mockMvc.perform(
			get("/people")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$..name",
					hasItems(
						this.person1.getName(),
						this.person2.getName()
					)
				)
			);
	}

	@Test
	void testGetByBirth() throws Exception {
		when(this.service.getByBirth(this.person1.getBirth()))
			.thenReturn(List.of(this.person1));
		this.mockMvc.perform(
				get(String.format("/people/%s", this.person1.getBirth()))
					.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$..name",
					hasItems(this.person1.getName())
				)
			);
	}

	@Test
	void testGetById() throws Exception {
		when(this.service.getById(1L))
			.thenReturn(this.person1);
		this.mockMvc.perform(
			get("/people/1")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.name")
					.value(this.person1.getName())
			);
	}

	@Test
	void testGetByNameContaining() throws Exception {
		when(this.service.getByNameContaining("person"))
			.thenReturn(List.of(this.person1, this.person2));
		this.mockMvc.perform(
				get("/people/person")
					.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$..birth",
					hasItems(
						this.person1.getBirth().toString(),
						this.person2.getBirth().toString()
					)
				)
			);
	}

	@Test
	void testRemove() throws Exception {
		doNothing().when(this.service).remove(1L);
		this.mockMvc.perform(
			delete("/people/1")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$.message",
					containsStringIgnoringCase("removed")
				)
			);
		verify(this.service).remove(1L);
	}

	@Test
	void testUpdate() throws Exception {
		final Person updated = Person
			.builder()
			.name("First Person Edited")
			.birth(this.person1.getBirth())
			.build();
		when(this.service.update(1L, updated))
			.thenReturn(updated);
		this.mockMvc.perform(
			put("/people/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					String.format("""
						{
							"name": "%s",
							"birth": "%s"
						}
					""",
						updated.getName(),
						updated.getBirth()
					)
				)
		)
			.andExpect(status().isOk())
			.andExpect(
				jsonPath(
					"$.name",
					containsStringIgnoringCase("edited")
				)
			);
	}

}
