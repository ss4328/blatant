package com.exec.repository;

import com.exec.model.Post;
import com.exec.model.Topic;
import com.exec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic subreddit);

    List<Post> findByUser(User user);
}