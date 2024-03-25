package com.example.mountain.domain.comment.repository;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByUser(User user);
}
