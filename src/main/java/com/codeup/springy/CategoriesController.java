package com.codeup.springy;

import data.Category;
import data.Post;
import data.User;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

    @RestController
    @RequestMapping(value = "/api/categories", headers = "Accept=application/json")
    public class CategoriesController {

        @GetMapping("")
        private Category getPostsByCategories(@RequestParam String categoryName) {
            Category fakeCat1 = new Category(1L, categoryName, null);
            ArrayList<Post> posts = new ArrayList<>();
            //make new post, then add to arraylist, then fakeCat1.setPosts, ACP
            return fakeCat1;
        }
    }

        //Within that category is a list of Post objects which will have the related Category
        //Some Post objects could have more than one Category nested within them. How could we use this in our client-side to provide a more unique experience?









