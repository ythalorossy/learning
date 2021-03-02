package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FLPersonResponse {

	private Long personId;
	private Long identId;
	private String surname;
	private String givenName;

	public String asLine() {
		return String.join(",",
				Arrays.asList(
						surname.toString(),
						givenName.toString()
				)
		);
	}
}
