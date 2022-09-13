package com.codeup.springy;

import data.Category;
import data.Post;
import data.User;
import data.UserRole;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {
    private List<Post> blogs = new ArrayList<>();

    private long nextId = 2;

    @GetMapping("")
    private List<Post> fetchAll() {
        return blogs;
    }

    @GetMapping("{id}")
    public Post getBlogById(@PathVariable Long id) {
    //search through the list of posts and return the post that matches the given id
        Post blog = findPostById(id);
        if (blog == null) {
            //what if we don't find it?
            throw new RuntimeException(("I dont know what I'm doing"));
        }
        return blog;
    }

    @PostMapping("")
    private void createPost(@RequestBody Post incomingPost) {
        //System.out.println("Here is your post: " + incomingPost);

        incomingPost.setId(nextId);

        //assign a fake author of the post
        User fakeAuthor = new User();
        fakeAuthor.setId(99);
        fakeAuthor.setUsername("Fake Author");
        fakeAuthor.setEmail("FA@stuff.com");
        incomingPost.setAuthor(fakeAuthor);
        //make some fake categories and put them into new post
        Category cat1 = new Category(1L, "horny", null);
        Category cat2 = new Category(1L, "bunny", null);
        incomingPost.setCategories(new ArrayList<>());
        incomingPost.getCategories().add(cat1);
        incomingPost.getCategories().add(cat2);

        nextId++;
        blogs.add(incomingPost);
    }
    //will have two pieces of info: request body, and path variable
    @PutMapping("{id}")
    private void updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        //find the post (blog in this case) to update in the post list
        Post blog = findPostById(id);
        if(blog != null) {
            //change the post according to the method intent
            blog.setTitle(updatedPost.getTitle());
            blog.setContent(updatedPost.getContent());
            System.out.println("Your post at id: " + id + " has been updated.");
        }
        if(updatedPost.getTitle() != null) {
            blog.setTitle(updatedPost.getTitle());

        }
        if(updatedPost.getContent() != null) {
            blog.setContent(updatedPost.getContent());
        }


    }
    //1. Set up the method signature much like updatePost() and createPost().
    @DeleteMapping("{id}")
    private void deletePostById(@PathVariable Long id) {
        Post blog = findPostById(id);
        if(blog != null) {
            //change the post according to the method intent
            blogs.remove(blog);
            System.out.println("Your post at id: " + id + " has been deleted.");

            }
        //what to do if we don't find it?
        throw new RuntimeException(("Blog not found"));
    }

    //making a function that finds a post or blog by id
    private Post findPostById(Long id) {
        for (Post blog : blogs) {
            if(blog.getId() == id) {
                blogs.remove(blog);
                System.out.println("Your post at id: " + id + " has been deleted.");
                return blog;
            }
        }
        return null;
    }

}
