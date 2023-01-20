package com.attornatus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.attornatus.entity.Address;
import com.attornatus.entity.Person;

public interface AddressRepository
	extends JpaRepository<Address, Long> {

	Optional<Address> findByIdAndResident(final Long id, final Person resident);
	List<Address> findByResident(final Person resident);
	@Modifying
	@Query(
		value = """
			update
				Address a
			set
				a.isMain = false
			where
				a.resident = :resident
		"""
	)
	void resetAllIsMain(final Person resident);
	Optional<Address> findByResidentAndIsMainTrue(final Person resident);
	void deleteByResident(final Person resident);

}
