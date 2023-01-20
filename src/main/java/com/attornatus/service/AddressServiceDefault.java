package com.attornatus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;
import com.attornatus.repository.AddressRepository;
import com.attornatus.repository.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class AddressServiceDefault implements AddressService {

	@Autowired
	private AddressRepository repository;
	@Autowired
	private PersonRepository foreignKeyRepository;

	@Override
	@Transactional
	public Address create(final Long resident, @Valid final Address address) {
		final Person foreignKey = this.id2ForeignKey(resident);
		if (address.getIsMain()) {
			this.repository.resetAllIsMain(foreignKey);
		}
		return this.repository.save(
			address
				.toBuilder()
				.resident(foreignKey)
				.build()
		);
	}

	@Override
	@Transactional
	public Address update(
		final Long resident,
		final Long id,
		@Valid final Address address
	) {
		final Address found = this.getById(resident, id);
		final Person foreignKey = this.id2ForeignKey(resident);
		if (address.getIsMain()) {
			this.repository.resetAllIsMain(foreignKey);
		}
		return this.repository.save(
			address
				.toBuilder()
				.id(found.getId())
				.resident(found.getResident())
				.build()
		);
	}

	@Override
	@Transactional
	public void remove(Long resident, Long id) {
		this.repository.delete(this.getById(resident, id));
	}

	@Override
	public Address getById(final Long resident, final Long id) {
		return this.repository
			.findByIdAndResident(id, this.id2ForeignKey(resident))
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public List<Address> getAllByResident(final Long resident) {
		return this.repository.findByResident(this.id2ForeignKey(resident));
	}

	@Override
	@Transactional
	public Address setMain(final Long resident, final Long id) {
		final Address address = this.getById(resident, id);
		this.repository.resetAllIsMain(address.getResident());
		return this.repository.save(
			address
				.toBuilder()
				.isMain(true)
				.build()
		);
	}

	@Override
	public Address getMain(final Long resident) {
		return this.repository
			.findByResidentAndIsMainTrue(this.id2ForeignKey(resident))
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Person id2ForeignKey(final Long resident) {
		return this.foreignKeyRepository
			.findById(resident)
			.orElseThrow(EntityNotFoundException::new);
	}

}
