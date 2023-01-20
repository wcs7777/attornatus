package com.attornatus.service;

import java.util.List;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;

import jakarta.validation.Valid;

public interface AddressService {

	public Address create(final Long resident, @Valid final Address address);
	public Address update(
		final Long resident,
		final Long id,
		@Valid final Address address
	);
	public void remove(final Long resident, final Long id);
	public Address getById(final Long resident, final Long id);
	public List<Address> getAllByResident(final Long resident);
	public Address setMain(final Long resident, final Long id);
	public Address getMain(final Long resident);
	public Person id2foreignKey(final Long resident);

}
