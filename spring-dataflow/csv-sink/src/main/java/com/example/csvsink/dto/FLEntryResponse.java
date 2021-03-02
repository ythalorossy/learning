package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FLEntryResponse {

	private Long entryId;
	private FLAddressResponse address;
	private FLPersonResponse person;
	private FLEmailResponse email;
	private FLBankResponse bank;
	private FLBankConnectionResponse bankConnection;

}
