package com.petproject.egeszsegere.service;

import com.petproject.egeszsegere.model.Article;
import com.petproject.egeszsegere.model.Category;
import com.petproject.egeszsegere.repository.ArticleRepository;
import com.petproject.egeszsegere.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

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
