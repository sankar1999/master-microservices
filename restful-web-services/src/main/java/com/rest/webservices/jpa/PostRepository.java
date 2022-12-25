package com.rest.webservices.jpa;

import com.rest.webservices.user.Post;
import com.rest.webservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
