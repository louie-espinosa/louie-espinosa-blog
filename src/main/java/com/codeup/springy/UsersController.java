package com.codeup.springy;


import data.User;
import data.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UsersController {
    private final List<User> users = new ArrayList<>(List.of(new User(1, "me", "me@pm.me", "12345", LocalDate.now(), UserRole.ADMIN, new ArrayList<>())));
    private long nextId = 2;

    @GetMapping("")
    public List<User> fetchUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable long id) {
        // search through the list of posts
        // and return the post that matches the given id
        User user = findUserById(id);
        if(user == null) {
            // what to do if we don't find it
            throw new RuntimeException("I don't know what I am doing");
        }
        // we found the post so just return it
        return user;
    }

    @GetMapping("/me")
    private User fetchMe() {
        return users.get(0);
    }

    @GetMapping("username/{username}")
    private User fetchUsername(@PathVariable String username) {
        User user = getByUsername(username);
        if(user == null) {
            throw new RuntimeException("Still dont know whats going on");
        }
        return user;
    }

    @GetMapping("email/{email}")
    private User getByEmail(@PathVariable String email) {
        User user = findUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("Surprise, I'm lost");
        }
        return user;
    }

private User getByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {//Need to work this area
                return user;
            }
        }
        return null;
}

    private User findUserById(long id) {
        for (User user: users) {
            if(user.getId() == id) {
                return user;
            }
        }
        // didn't find it so do something
        return null;
    }

    private User findUserByEmail(String email) {
        for (User user : users) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User newUser) {
//        System.out.println(newPost);
        // assign  nextId to the new post
        newUser.setId(nextId);
        //dont need this now, but just for kicks
        newUser.setCreatedAt(LocalDate.now());
        nextId++;

        users.add(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        // search through the list of posts
        // and delete the post that matches the given id
        User user = findUserById(id);
        if(user != null) {
            users.remove(user);
            return;
        }
        // what to do if we don't find it
        throw new RuntimeException("User not found");
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User updatedUser, @PathVariable long id) {
        // find the post to update in the posts list

        User user = findUserById(id);
        if(user == null) {
            System.out.println("User not found");
        } else {
            if(updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            return;
        }
        throw new RuntimeException("User not found");
    }
    @PutMapping("/{id}/updatePassword")
    private void updatePassword(@PathVariable long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min=3) @RequestParam String newPassword) {
        User user = findUserById(id);
        if(user == null) {
            throw  new RuntimeException("cannot find user " + id);
        }

        //compare old password with saved pw
        if(user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("this was same as last, give new please!");
        }
        //validate new password
        if(newPassword.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password length must be at least 3 characters");
        }
        user.setPassword(newPassword);
    }
}

