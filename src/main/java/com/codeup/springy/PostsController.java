package com.codeup.springy;

import data.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {
    List<Post> blogs = new ArrayList<>();

    @GetMapping
    private List<Post> fetchAll() {
        // In fetchAll(), let's make a list of Post objects and add 2-3 posts to that list with believable values.
        blogs.add(new Post(1L, "Dont arrest me", "Someone was arrest at Codeup today, and it was very upsetting. They should have been more professional and allowed him to walk down stairs"));
        blogs.add(new Post(2L, "Masks don't work", "Masks are made for highly sanitized operating rooms, but not for environments outside of those settings among people aren't surgical professionals"));
        blogs.add(new Post(3L, "Currency is NOT money", "Money must be a store of value over a long period of time, whereas currencies leak your purchasing power due to reckless monetary policy. One example of reckless policy is Quantitative easing, which is a fancy term for currency creation"));
        //Now, return that list to fulfil the return expectation of the method.
        return blogs;
    }
    //Using examples from above, create another public method in PostsController named getById()
    @GetMapping("{id}")
    public Post getById(@PathVariable Long id) {
    //search through the list of posts and return the post that matches the given id
        for (Post blog : blogs) {
            if(blog.getId() == id) {
                return blog;
            }
        }
        //what if we don't find it?
        throw new RuntimeException(("I dont know what I'm doing"));

    }

    @PostMapping
    private void createPost(@RequestBody Post incomingPost) {
        System.out.println("Here is your post: " + incomingPost);
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



    //making a function for a post or blog by id
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
