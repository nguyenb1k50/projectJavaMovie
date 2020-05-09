package com.myclass.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment")
public class Comment {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Column(name = "comment")
		private String comment;
		@Column(name = "movie_id")
		private String movieId;
		@Column(name = "user_id")
		private String userId;
		
		@Column(name = "CommentDate")
		private Date commentDate;
		
		@ManyToOne
		@JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name="fk_movie_cmt"),
		insertable = false, updatable = false)
		@JsonIgnore
		private Movie movie;
		
		@ManyToOne
		@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="fk_user_cmt"),
		insertable = false, updatable = false)
		@JsonIgnore
		private UserDTO user;
		
		@Transient
		private String userName;
		
		public Comment(String comment, String movieId, String userId, Date commentDate) {
			this.comment = comment;
			this.movieId = movieId;
			this.userId = userId;
			this.commentDate = commentDate;
		}
			
		public Comment() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getMovieId() {
			return movieId;
		}

		public void setMovieId(String movieId) {
			this.movieId = movieId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Movie getMovie() {
			return movie;
		}

		public void setMovie(Movie movie) {
			this.movie = movie;
		}

		public UserDTO getUser() {
			return user;
		}

		public void setUser(UserDTO user) {
			this.user = user;
		}

		public String getUserName() {
			this.userName = user.getUsername();
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Date getCommentDate() {
			return commentDate;
		}

		public void setCommentDate(Date commentDate) {
			this.commentDate = commentDate;
		}
		
		
}
