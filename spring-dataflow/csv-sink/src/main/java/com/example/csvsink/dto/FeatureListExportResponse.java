package com.example.csvsink.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FeatureListExportResponse {

	private String clientId;
	private List<FeatureListResponse> featureLists;
}
