package com.myclass.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myclass.entity.Movie;
import com.myclass.repository.MovieRepository;
@RestController
@RequestMapping("api/movie")
public class MovieController {
	
	@Autowired
	MovieRepository movieRepository;
	
	@GetMapping("")
	public Object get() {
		List<Movie> movies = movieRepository.getAllMovie();
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	@PostMapping(value = "", headers = "Content-Type=multipart/form-data")
	public Object createMovie(@RequestParam("file") MultipartFile file, @RequestParam("Movie") String film) throws Exception {
		Movie movie = new ObjectMapper().readValue(film, Movie.class);
		Boolean p = movieRepository.existsByTitle(movie.getTitle());
		if(!p) {
			movie.setId(UUID.randomUUID().toString());
			try {
				movie.setImageData(compressBytes(file.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Movie entity = movieRepository.save(movie);
			return new ResponseEntity<Movie>(entity, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Movie's Title exist",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @RequestBody Movie movie) {
		if(movieRepository.existsById(id)) {
			movie.setId(id);
			Movie entity = movieRepository.save(movie);
			return new ResponseEntity<Movie>(entity, HttpStatus.OK);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		
		if(movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
	
	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

			return outputStream.toByteArray();
		}

		// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}

}
