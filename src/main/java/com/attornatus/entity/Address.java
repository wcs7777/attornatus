package com.attornatus.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"resident", "number"},
			name = "unique_resident_number_address"
		)
	}
)
public class Address {

	@Id
	@SequenceGenerator(
		name = "address_sequence",
		sequenceName = "address_sequence",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "address_sequence"
	)
	private Long id;

	@NotBlank(message = "Please, inform the street!")
	@Column(nullable = false)
	private String street;

	@NotBlank(message = "Please, inform the cep!")
	@Column(length = 8, nullable = false)
	private String cep;

	@NotBlank(message = "Please, inform the number!")
	@Column(length = 4, nullable = false)
	private String number;

	@NotBlank(message = "Please, inform the city!")
	@Column(nullable = false)
	private String city;

	@Builder.Default
	private Boolean isMain = false;

	@ManyToOne(
		cascade = {
			CascadeType.MERGE
		},
		fetch = FetchType.LAZY,
		optional = false
	)
	@JoinColumn(
		name = "resident",
		referencedColumnName = "id",
		foreignKey = @ForeignKey(name = "fk_address_person")
	)
	private Person resident;
}
