package com.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elastic.model.Source;

@Repository
public interface SourceRepository extends ElasticsearchRepository<Source, String> {

}
