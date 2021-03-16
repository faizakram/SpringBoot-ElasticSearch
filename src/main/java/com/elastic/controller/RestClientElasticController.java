package com.elastic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elastic.dto.ProfileDocument;
import com.elastic.service.RestClientService;

@RestController
public class RestClientElasticController {

	@Autowired
	private RestClientService restClientService;

	@PostMapping("addProfile")
	public ResponseEntity<ProfileDocument> createProfile(@RequestBody ProfileDocument document) throws Exception {
		return new ResponseEntity<ProfileDocument>(restClientService.createProfileDocument(document),
				HttpStatus.CREATED);
	}

	@GetMapping("getProfile/{id}")
	public ProfileDocument findById(@PathVariable String id) throws Exception {
		return restClientService.findById(id);
	}
	
	@PutMapping("updateBy/{id}")
	public String updateByIdMap(@PathVariable String id, @RequestBody Map<String, Object> obj) throws Exception {
		return restClientService.updateByIdMap(id, obj);
	}
	
}
