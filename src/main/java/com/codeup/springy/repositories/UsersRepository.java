package com.codeup.springy.repositories;

import com.codeup.springy.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
//need to change so that it reflects the appropriate object, in this case User, NOT Post
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findByEmail(String email);
}
