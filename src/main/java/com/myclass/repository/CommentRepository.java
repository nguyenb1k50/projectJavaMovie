package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.myclass.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{
		
	@Query("SELECT c FROM Comment c WHERE c.movieId = :idMovie")
	List<Comment> getAllByMovie(@Param("idMovie") String idMovie);
}
