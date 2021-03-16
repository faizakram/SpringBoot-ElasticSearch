package com.elastic.service;

import java.util.Map;

import com.elastic.dto.ProfileDocument;

public interface RestClientService {

	ProfileDocument createProfileDocument(ProfileDocument document);

	ProfileDocument findById(String id);

	String updateByIdMap(String id, Map<String, Object> obj);

}
