package com.myclass.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.entity.Movie;
import com.myclass.repository.MovieRepository;

@RestController
@RequestMapping("api/movie")
public class MovieController {
	@Value("${UPLOAD_DIR}")
	private String UPLOAD_DIR;

	@Autowired
	MovieRepository movieRepository;

	@GetMapping("")
	public Object get() {
		List<Movie> movies = movieRepository.getAllMovie();
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

	@PostMapping()
	public Object post(@RequestParam("posterImg") MultipartFile posterImg, @ModelAttribute Movie movie) {
		Boolean p = movieRepository.existsByTitle(movie.getTitle());
		if (!p) {
			try {
				// Get the file and save it somewhere
				byte[] bytes = posterImg.getBytes();
				// File.separator for difference OS platforms
				Path path = Paths.get(System.getProperty("user.dir") + File.separator + UPLOAD_DIR + File.separator
						+ posterImg.getOriginalFilename());
				if(Files.write(path, bytes) != null) {
					movie.setPoster("upload/"+posterImg.getOriginalFilename());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			movie.setId(UUID.randomUUID().toString());
			Movie entity = movieRepository.save(movie);
			return new ResponseEntity<Movie>(entity, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Movie's Title exist", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @RequestBody Movie movie) {

		if (movieRepository.existsById(id)) {
			movie.setId(id);
			Movie entity = movieRepository.save(movie);
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

}
