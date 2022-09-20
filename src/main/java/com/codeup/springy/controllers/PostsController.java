package com.codeup.springy.controllers;

import com.codeup.springy.data.Category;
import com.codeup.springy.data.Post;
import com.codeup.springy.controllers.misc.FieldHelper;
import com.codeup.springy.repositories.CategoriesRepositories;
import com.codeup.springy.repositories.PostsRepository;
import com.codeup.springy.data.User;
import com.codeup.springy.repositories.UsersRepository;
import com.codeup.springy.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//IMPORTANT NOTE: The NoArgsConstructor prohibited me from connecting to the front end. Deleted and everything worked!
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private final EmailService emailService;
   private PostsRepository postsRepository;
   private UsersRepository usersRepository;
   private CategoriesRepositories categoriesRepositories;

    @GetMapping("")
    public List<Post> fetchPosts() {
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
    public void createPost(@RequestBody Post incomingPost, OAuth2Authentication auth) {
        //System.out.println("Here is your post: " + incomingPost);
        //assign a fake author of the post
        //User fakeAuthor = usersRepository.findById(1L).get();//dont need these two lines anymore because its static, lets insert the actual user
        //incomingPost.setAuthor(fakeAuthor);
        String username = auth.getName();

        User author = usersRepository.findByUsername(username);
        incomingPost.setAuthor(author);

        incomingPost.setCategories(new ArrayList<>());

        Category cat1 = categoriesRepositories.findById(1L).get();
        Category cat2 = categoriesRepositories.findById(2L).get();

        incomingPost.getCategories().add(cat1);
        incomingPost.getCategories().add(cat2);

        postsRepository.save(incomingPost);

        emailService.prepareAndSend(incomingPost, "THE VERY FIRST POST", "If this works for you, celebrate good times, c'mon!");
    }
    //will have two pieces of info: request body, and path variable
    @PutMapping("{id}")
    public void updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {

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
    public void deletePostById(@PathVariable Long id) {
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
