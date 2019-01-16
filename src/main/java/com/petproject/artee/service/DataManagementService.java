package com.petproject.artee.service;

import com.petproject.artee.model.Article;
import com.petproject.artee.model.Category;
import com.petproject.artee.repository.ArticleRepository;
import com.petproject.artee.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataManagementService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DataManagementService(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    public boolean categoryNameAlreadyExists(Category category) {
        return categoryRepository.findByName(category.getName()) != null;
    }

    public boolean articleTitleAlreadyExists(Article article) {
        return articleRepository.findByTitle(article.getTitle()) != null;
    }
}
