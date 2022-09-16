package com.codeup.springy.controllers;

import com.codeup.springy.data.Category;

import com.codeup.springy.repositories.CategoriesRepositories;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
    public class CategoriesController {

    //this is dependency injection; the controller is depending on the categories repository to make it accessible to our client side
    private CategoriesRepositories categoriesRepositories;


    @GetMapping("")
    private List<Category> fetchAllCategories(@RequestParam String categoryName) {
        return categoriesRepositories.findAll();
    }
    @GetMapping("/search")
    private Category fetchCategoryByCategoryName(@RequestParam String categoryName) {
        Category cat = categoriesRepositories.findByName(categoryName);
        if(cat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category " + categoryName + " not found");
        }
        return cat;
    }
}
