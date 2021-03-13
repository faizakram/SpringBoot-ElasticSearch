package com.elastic.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elastic.model.Article;
import com.elastic.model.Source;
import com.elastic.repository.ArticleRepository;
import com.elastic.repository.SourceRepository;

@RestController
public class ElasticController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private SourceRepository sourceRepository;


	@PostMapping("/add")
	public Article addArticle(@RequestBody Article article) {
		return articleRepository.save(article);
	}
	@PostMapping("/addSource")
	public Source addSource(@RequestBody Source source) {
		return sourceRepository.save(source);
	}

	@PutMapping("/update/{id}")
	public Article updateArticle(@PathVariable String id, @RequestBody Article article) {
		if (!articleRepository.findById(id).isPresent()) {
			throw new NullPointerException("Artile Not Found");
		}
		article.setId(id);
		return articleRepository.save(article);
	}

	@GetMapping("/find/{id}")
	public Article geteArticle(@PathVariable String id) {
		Optional<Article> article = articleRepository.findById(id);
		if (!article.isPresent()) {
			throw new NullPointerException("Artile Not Found");
		}
		return article.get();
	}


	@GetMapping("/pagination")
	public Page<Article> getAticles(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}

}
