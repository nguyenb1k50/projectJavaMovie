package com.myclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Ratting;
@Repository
public interface RattingRepository extends JpaRepository<Ratting, Long>{
	@Query("SELECT r FROM Ratting r WHERE r.userId = :userId AND r.movieId = :movieId")
	Ratting checkExistsRate(@Param("userId") String userId,@Param("movieId") String movieId);

	@Query("SELECT coalesce(sum(r.star+0.0)/count(r.id),0.0) FROM Ratting r WHERE r.movieId = :movieId")
	float getMovieStar(@Param("movieId") String movieId);
}
