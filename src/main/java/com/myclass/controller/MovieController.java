package com.myclass.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.config.StaticCons;
import com.myclass.entity.Comment;
import com.myclass.entity.Movie;
import com.myclass.entity.Ratting;
import com.myclass.entity.UserDTO;
import com.myclass.repository.CommentRepository;
import com.myclass.repository.MovieRepository;
import com.myclass.repository.RattingRepository;
import com.myclass.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/movie")
public class MovieController {
	@Value("${UPLOAD_DIR}")
	private String UPLOAD_DIR;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	RattingRepository rattingRepo;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepo;
	
	@GetMapping("")
	public Object get(@RequestParam(required = false, defaultValue = "") String showtime) {
		List<Movie> movies;
		switch (showtime) {
		case "comingup":
			movies = formatM(movieRepository.getComingMovie());
			break;
		case "showing":
			movies = formatM(movieRepository.getShowingMovie());
			break;
		case "close":
			movies = formatM(movieRepository.getExpireMovie());
			break;
		default:
			movies = formatM(movieRepository.getAllMovie());
			break;
		}

		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Object getDetail(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = "MEMBER";
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			UserDTO currentUser = userRepository.findByUsername(currentUserName);
			if("1".equals(currentUser.getRoleId())) {
				role = "ADMIN";
			}
		} else {
			throw new AccessDeniedException("đăng nhâp đi bạn!");
		}
		
		Optional<Movie> movieOp = movieRepository.findById(id);
		if (movieOp.isPresent()) {
			Movie movie = movieOp.get();
			movie.setPoster(StaticCons.currentUrl() + movie.getPoster());
			movie.setImage(StaticCons.currentUrl() + movie.getImage());
			movie.setStar(rattingRepo.getMovieStar(id));
			movie.setUserRole(role);
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}

	@PostMapping()
	public Object post(@RequestParam("posterImg") MultipartFile posterImg,@RequestParam("imageFile") MultipartFile image
			,@ModelAttribute Movie movie) {
		Boolean p = movieRepository.existsByTitle(movie.getTitle());
		if (!p) {
			movie.setPoster(StaticCons.saveImage(posterImg));
			movie.setImage(StaticCons.saveImage(image));
			movie.setId(UUID.randomUUID().toString());
			Movie entity = movieRepository.save(movie);
			entity.setPoster(StaticCons.currentUrl() + entity.getPoster());
			entity.setImage(StaticCons.currentUrl() + entity.getImage());
			return new ResponseEntity<Movie>(entity, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Movie's Title exist", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @ModelAttribute Movie movie,
			@RequestParam(name = "posterImg",required = false) MultipartFile posterImg, 
			@RequestParam(name = "imageFile",required = false) MultipartFile image) {

		if (movieRepository.existsById(id)) {
			Movie m = movieRepository.findById(id).get();
			//for update partial property
			StaticCons.copyNonNullProperties(movie, m);
			if(posterImg != null && posterImg.getSize() > 0)
				m.setPoster(StaticCons.saveImage(posterImg));
			if(image != null && image.getSize() > 0)
				m.setImage(StaticCons.saveImage(image));
			Movie entity = movieRepository.save(m);
			return new ResponseEntity<Movie>(entity, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {

		if (movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/rate")
	public Object rateMovie(@RequestBody Map<String, String> allParams) {
		
		
		String movieId = allParams.get("movieId");
		String currentUserName;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		} else {
			throw new AccessDeniedException("đăng nhâp đi bạn!");
		}
		UserDTO u = userRepository.findByUsername(currentUserName);
		String userId = u.getId(); 
				//allParams.get("userId");
		int star = Integer.parseInt(allParams.get("star"));
		if (StringUtils.isEmpty(movieId) || StringUtils.isEmpty(userId)) {
			return new ResponseEntity<String>("thieu param", HttpStatus.BAD_REQUEST);
		}
		Ratting rate = new Ratting();
		try {
			rate = rattingRepo.checkExistsRate(userId, movieId);
		} catch (InvalidDataAccessApiUsageException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		if (rate != null) {
			return new ResponseEntity<String>("user da rate phim nay roi", HttpStatus.BAD_REQUEST);
		}
		try {
			rate = rattingRepo.save(new Ratting(movieId, userId, star));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(rate, HttpStatus.BAD_REQUEST);
	}

	public List<Movie> formatM(List<Movie> movies) {
		for (Movie m : movies) {
			if (m.getPoster() != null) {
				m.setPoster(StaticCons.currentUrl() + m.getPoster());
				m.setImage(StaticCons.currentUrl() + m.getImage());
				m.setStar(rattingRepo.getMovieStar(m.getId()));
			}
		}

		return movies;
	}
	
	@PostMapping("/comment/{idMovie}")
	public Object comment(@PathVariable String idMovie ,@RequestBody String comment) {
		String userID = "";
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		} else {
			throw new AccessDeniedException("đăng nhâp đi bạn!");
		}
		UserDTO currentUser = userRepository.findByUsername(currentUserName);
		userID = currentUser.getId();
		Comment Ocomment = commentRepo.save(new Comment(comment, idMovie, userID));
		
		return new ResponseEntity<Object>(Ocomment, HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/getComment/{idMovie}")
	public Object getListComment(@PathVariable String idMovie) {
		
		List<Comment> listComment = commentRepo.getAllByMovie(idMovie);
		
		return new ResponseEntity<Object>(listComment, HttpStatus.BAD_REQUEST);
		
	}
	
	@DeleteMapping("/deleteCmt/{id}")
	public Object deleteComment(@PathVariable Long id) {
		
		if(commentRepo.existsById(id)) {
			commentRepo.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
}
