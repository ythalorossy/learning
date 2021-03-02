package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FLBankConnectionResponse {

	private Long bankConnectionId;
	private FLBankResponse bank;
	private String accountNumber;
	private String iban;
}
