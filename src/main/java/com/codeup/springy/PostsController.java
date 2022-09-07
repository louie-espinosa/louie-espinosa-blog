package com.codeup.springy;

import data.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {
    //...
    @GetMapping
    private List<Post> fetchAll() {
       // ...
        // In fetchAll(), let's make a list of Post objects and add 2-3 posts to that list with believable values.

        List<Post> blogs = new ArrayList<>();
        //List<Post> second = new ArrayList<>();
        //List<Post> third = new ArrayList<>();
        blogs.add(new Post(1L, "Dont arrest me", "Someone was arrest at Codeup today, and it was very upsetting. They should have been more professional and allowed him to walk down stairs"));
        blogs.add(new Post(2L, "Masks don't work", "Masks are made for highly sanitized operating rooms, but not for environments outside of those settings among people aren't surgical professionals"));
        blogs.add(new Post(3L, "Currency is NOT money", "Money must be a store of value over a long period of time, whereas currencies leak your purchasing power due to reckless monetary policy. One example of reckless policy is Quantitative easing, which is a fancy term for currency creation"));
        //Now, return that list to fulfil the return expectation of the method.
        return blogs;
    }
    //Using examples from above, create another public method in PostsController named getById()
    @GetMapping("{id}")
    public Post getById(@PathVariable Long id) {

        return new Post(1L, "Some Post", "Some content goes here.");

    }

    //    This method will return a single Post object
    //
    //    It will accept GET requests on api/posts/{id}
    //
    //    getById() has one parameter mapped by @PathVariable to the route's {id}.
    //        The parameter is of type Long and is named id.
    //
    //    Create and return a new Post object with all fields populated.

}



//rest of today restControllers part2. Proceed until back to javascript heading.