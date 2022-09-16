package com.codeup.springy.repositories;

import com.codeup.springy.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepositories extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
