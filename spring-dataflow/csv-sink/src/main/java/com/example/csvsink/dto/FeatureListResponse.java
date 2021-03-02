package com.example.csvsink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureListResponse {

	private String clientId;
	private FeatureCodeResponse featureCode;
	private FLEntryResponse entry;
	private LocalDateTime activationDate;
	private LocalDateTime expirationDate;
}
