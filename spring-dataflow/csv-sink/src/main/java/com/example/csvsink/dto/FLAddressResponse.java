package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FLAddressResponse {
	private Long addressId;
	private Long isoNumber;
	private String street;
	private String houseNumber;
	private String zipCode;
	private String city;

	public String asLine() {
		return String.join(",",
				Arrays.asList(
						addressId.toString(),
						isoNumber.toString(),
						street,
						houseNumber,
						zipCode,
						city
				)
		);
	}
}
