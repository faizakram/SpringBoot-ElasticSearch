package com.elastic.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elastic.dto.Constant;
import com.elastic.dto.ProfileDocument;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestClientServiceImpl implements RestClientService {

	@Autowired
	private RestHighLevelClient client;
	private ObjectMapper objectMapper;

	@Override
	public ProfileDocument createProfileDocument(ProfileDocument document) {
		UUID uuid = UUID.randomUUID();
		document.setId(uuid.toString());
		objectMapper = new ObjectMapper();
		IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE, document.getId())
				.source(convertProfileDocumentToMap(document), XContentType.JSON);
		IndexResponse indexResponse = null;
		try {
			indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		indexResponse.getResult().name();
		return document;// createProfileDocument1(document);
	}

	public ProfileDocument createProfileDocument1(ProfileDocument document) {
		CreateIndexRequest request = new CreateIndexRequest(Constant.INDEX);
		UUID uuid = UUID.randomUUID();
		document.setId(uuid.toString());
		request.source(convertProfileDocumentToMap(document));
		try {
			client.indices().create(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	private Map<String, Object> convertProfileDocumentToMap(ProfileDocument profileDocument) {
		return objectMapper.convertValue(profileDocument, Map.class);
	}

	@Override
	public ProfileDocument findById(String id) {
		GetRequest getRequest = new GetRequest(Constant.INDEX, Constant.TYPE, id);
		objectMapper = new ObjectMapper();
		GetResponse getResponse = null;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> resultMap = getResponse.getSource();

		return convertMapToProfileDocument(resultMap);
	}

	public Map<String, Object> findByIdMap(String id) {
		GetRequest getRequest = new GetRequest(Constant.INDEX, Constant.TYPE, id);
		GetResponse getResponse = null;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			
		}
		return getResponse.getSource();
	}

	private ProfileDocument convertMapToProfileDocument(Map<String, Object> map) {
		return objectMapper.convertValue(map, ProfileDocument.class);
	}

	@Override
	public String updateByIdMap(String id, Map<String, Object> obj) {
		Map<String, Object> map = findByIdMap(id);
		obj.forEach((k, v) -> {
			if (map.containsKey(k)) {
				map.put(k, v);
			}
		});

		try {
			return updateProfile(map, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String updateProfile(Map<String, Object> obj, String id) throws Exception {
		// ProfileDocument resultDocument = findById(document.getId());
		UpdateRequest updateRequest = new UpdateRequest(Constant.INDEX, Constant.TYPE, id);
		updateRequest.doc(obj);
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();

	}

}
