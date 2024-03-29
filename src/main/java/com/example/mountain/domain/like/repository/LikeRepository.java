package com.example.mountain.domain.like.repository;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where l.user.userId = :userId and l.feed.id = :feedId")
    Like findUserId(@Param("userId")Long userId, @Param("feedId")Long feedId);
    @Modifying
    @Query("delete from Like l where l.feed.id = :feedId")
    void deleteFeed_Id(@Param("feedId")Long feedId);

}
