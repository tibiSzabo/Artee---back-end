package com.petproject.artee;

import com.petproject.artee.model.Article;
import com.petproject.artee.model.Category;
import com.petproject.artee.model.Response;
import com.petproject.artee.repository.ArticleRepository;
import com.petproject.artee.repository.CategoryRepository;
import com.petproject.artee.service.DataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class BackendApi {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final DataManagementService dms;

    @Autowired
    public BackendApi(ArticleRepository articleRepository, CategoryRepository categoryRepository, DataManagementService dms) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.dms = dms;
    }

    @PostMapping("/api/article/add")
    public Response saveArticle(@RequestBody Article newArticle) {
        if (!dms.articleTitleAlreadyExists(newArticle)) {
            this.articleRepository.save(newArticle);
            System.out.println(newArticle.toString() + " saved to the database!");
            return new Response(true,"Article successfully saved to the database!");
        } else {
            return new Response(false,"Article with title '" + newArticle.getTitle() + "' already exists.");
        }
    }

    @PostMapping("/api/category/add")
    public Response saveCategory(@RequestBody Category newCategory) {
        if (!dms.categoryNameAlreadyExists(newCategory)) {
            this.categoryRepository.save(newCategory);
            System.out.println(newCategory.toString() + " saved to the database!");
            return new Response(true,"Category successfully saved to the database!");
        } else {
            return new Response(false,"Category with name '" + newCategory.getName() + "' already exists.");
        }
    }

    @GetMapping("/api/category/get-all")
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @GetMapping("/api/article/get-all")
    public List<Article> getAllArticles() {
        return this.articleRepository.findAll();
    }

    @PutMapping("/api/article/edit")
    public Response editArticle(@RequestBody Article editedArticle) {
        Article articleToEdit = null;
        Optional<Article> optionalArticle = articleRepository.findById(editedArticle.getId());
        if (!optionalArticle.isPresent()) {
            return new Response(false, "Article with ID: " + editedArticle.getId().toString() + ", title: '" + editedArticle.getTitle()
                    + "' was not found in the database.");
        } else {
            articleToEdit = optionalArticle.get();
            articleRepository.save(articleToEdit);
            return new Response(true, "Article with title '" + articleToEdit.getTitle() + "' updated in the database " +
                    "successfully!");
        }
    }

    @PutMapping("/api/category/edit")
    public Response editCategory(@RequestBody Category editedCategory) {
        Category categoryToEdit = null;
        Optional<Category> optionalCategory = categoryRepository.findById(editedCategory.getId());
        if (!optionalCategory.isPresent()) {
            return new Response(false, "Category with ID: " + editedCategory.getId().toString() + ", name: '"
                    + editedCategory.getName() + "' was not found in the database.");
        } else {
            categoryToEdit = optionalCategory.get();
            categoryRepository.save(categoryToEdit);
            return new Response(true, "Category with name '" + categoryToEdit.getName() + "' updated in the database " +
                    "successfully!");
        }
    }

    @DeleteMapping("/api/article/delete/{id}")
    public Response deleteArticle(@PathVariable Long id) {
        Optional<Article> articleToDelete = articleRepository.findById(id);
        if (!articleToDelete.isPresent()) {
            return new Response(false, "Article with ID: " + id.toString() + "' was not found in the database.");
        }
        articleRepository.deleteById(id);
        return new Response(true, "Article with ID: " + id.toString() + ", title: '" + articleToDelete.get().getTitle() + "' was deleted from the database.");
    }


}
