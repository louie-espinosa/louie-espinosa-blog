package com.codeup.springy.controllers;


import com.codeup.springy.data.User;
import com.codeup.springy.controllers.misc.FieldHelper;
import com.codeup.springy.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UsersController {

    private UsersRepository usersRepository;

//    public UsersController(UsersRepository usersRepository) {//this is IntelliJ built constructor, replaced by using lombok annotation
//        this.usersRepository = usersRepository;
//    }

    @GetMapping("")
    public List<User> fetchUsers() {
        //return users;
        return usersRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> fetchUserById(@PathVariable long id) {
        // search through the list of posts
        // and return the post that matches the given id
//        User user = findUserById(id);
//        if(user == null) {
//            // what to do if we don't find it
//            throw new RuntimeException("I don't know what I am doing");
//        }
//        // we found the post so just return it
//        return user;
        Optional<User> user = usersRepository.findById(id);
            if(user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found.");
            }
            return user;
    }
    @GetMapping("/me")
    private Optional<User> fetchMe() {
        return usersRepository.findById(1L);
    }

//    @GetMapping("username/{username}")
//    private User fetchUsername(@PathVariable String username) {
//        User user = getByUsername(username);
//        if(user == null) {
//            throw new RuntimeException("Still dont know whats going on");
//        }
//        return user;
//    }

//    @GetMapping("email/{email}")
//    private User getByEmail(@PathVariable String email) {
//        User user = findUserByEmail(email);
//        if(user == null) {
//            throw new RuntimeException("Surprise, I'm lost");
//        }
//        return user;
//    }
//
//private User getByUsername(String username) {
//        for (User user : users) {
//            if(user.getUsername().equals(username)) {//Need to work this area
//                return user;
//            }
//        }
//        return null;
//}

//    private User findUserById(long id) {
//        for (User user: users) {
//            if(user.getId() == id) {
//                return user;
//            }
//        }
//        // didn't find it so do something
//        return null;
//    }

//    private User findUserByEmail(String email) {
//        for (User user : users) {
//            if(user.getEmail().equals(email)) {
//                return user;
//            }
//        }
//        return null;
//    }

    @PostMapping("/create")
    public void createUser(@RequestBody User newUser) {
//        System.out.println(newPost);
        // assign  nextId to the new post
       newUser.setCreatedAt(LocalDate.now());
       usersRepository.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        // search through the list of posts
        // and delete the post that matches the given id
        usersRepository.deleteById(id);
//        User user = findUserById(id);
//        if(user != null) {
//            users.remove(user);
//            return;
//        }
        // what to do if we don't find it
        throw new RuntimeException("User not found");
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User updatedUser, @PathVariable long id) {

        Optional<User> userOptional = usersRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id " + id + " not found");
        }
        //Create variable for user optional so we dont have to worry about redeclaring optional
        User userOriginal = userOptional.get();

        //merge the chagned data in the updateduser with original user
        BeanUtils.copyProperties(updatedUser, userOriginal, FieldHelper.getNullPropertyNames(updatedUser));

        //originalUser now has the merged data (changes + original data)
        userOriginal.setId(id);
        usersRepository.save(updatedUser);
        // find the post to update in the posts list
//        User user = findUserById(id);
//        if(user == null) {
//            System.out.println("User not found");
//        } else {
//            if(updatedUser.getEmail() != null) {
//                user.setEmail(updatedUser.getEmail());
//            }
//            return;
//        }
        throw new RuntimeException("User not found");
    }
    @PutMapping("/{id}/updatePassword")
    private void updatePassword(@PathVariable long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min=3) @RequestParam String newPassword) {
        User user = usersRepository.findById(id).get();
//        if(user == null) {
//            throw  new RuntimeException("cannot find user " + id);
//        }

        //compare old password with saved pw
        if(!user.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "this was same pw as last, give new please!");
        }
        //validate new password
        if(newPassword.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password length must be at least 3 characters");
        }
        user.setPassword(newPassword);
        usersRepository.save(user);
    }
}

