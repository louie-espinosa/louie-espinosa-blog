package com.codeup.springy.repositories;

import com.codeup.springy.data.Post;
import org.springframework.data.jpa.repository.JpaRepository;
//the parameters are the table object, and the data type of the primary key
public interface PostsRepository extends JpaRepository<Post, Long> {

}
