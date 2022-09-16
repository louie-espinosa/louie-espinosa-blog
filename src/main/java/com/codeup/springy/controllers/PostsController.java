package com.codeup.springy.controllers;

import com.codeup.springy.data.Category;
import com.codeup.springy.data.Post;
import com.codeup.springy.controllers.misc.FieldHelper;
import com.codeup.springy.repositories.CategoriesRepositories;
import com.codeup.springy.repositories.PostsRepository;
import com.codeup.springy.data.User;
import com.codeup.springy.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {

   private PostsRepository postsRepository;

   private UsersRepository usersRepository;

   private CategoriesRepositories categoriesRepositories;



    @GetMapping("")
    public List<Post> fetchAll() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostById(@PathVariable long id) {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
        }
        return optionalPost;
    }


    @PostMapping("")
    private void createPost(@RequestBody Post incomingPost) {
        //System.out.println("Here is your post: " + incomingPost);
        //assign a fake author of the post
        User fakeAuthor = usersRepository.findById(1L).get();
        incomingPost.setAuthor(fakeAuthor);
        incomingPost.setCategories(new ArrayList<>());

        Category cat1 = categoriesRepositories.findById(1L).get();
        Category cat2 = categoriesRepositories.findById(2L).get();

        incomingPost.getCategories().add(cat1);
        incomingPost.getCategories().add(cat2);

        postsRepository.save(incomingPost);
    }
    //will have two pieces of info: request body, and path variable
    @PutMapping("{id}")
    private void updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {

        Optional<Post> originalPost = postsRepository.findById(id);
        if(originalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post " + id + " not found");
        }
        //in case id is not in the request body(i.e. updatedPost) set it with the path variable id
        updatedPost.setId(id);

        //copy any new field values FROM updatedPost TO originalPost
        BeanUtils.copyProperties(updatedPost, originalPost.get(), FieldHelper.getNullPropertyNames(updatedPost));

        postsRepository.save(originalPost.get());

        //find the post (blog in this case) to update in the post list
//        Post blog = fetchBlogById(id);
//        if(blog != null) {
//            //change the post according to the method intent
//            blog.setTitle(updatedPost.getTitle());
//            blog.setContent(updatedPost.getContent());
//            System.out.println("Your post at id: " + id + " has been updated.");
//        }
//        if(updatedPost.getTitle() != null) {
//            blog.setTitle(updatedPost.getTitle());
//
//        }
//        if(updatedPost.getContent() != null) {
//            blog.setContent(updatedPost.getContent());
//        }

    }
    //1. Set up the method signature much like updatePost() and createPost().
    @DeleteMapping("{id}")
    private void deletePostById(@PathVariable Long id) {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
        }
        postsRepository.deleteById(id);

    }

//    private Post findUserById(long id) {
//        for (Post blog: blogs) {
//            if(blog.getId() == id) {
//                return blog;
//            }
//        }
//        // didn't find it so do something
//        return null;
//    }

}
