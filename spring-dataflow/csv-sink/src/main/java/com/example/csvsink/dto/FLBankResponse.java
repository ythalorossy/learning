package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FLBankResponse {

	private Long bankId;
	private String blz;
	private String bic;
}
