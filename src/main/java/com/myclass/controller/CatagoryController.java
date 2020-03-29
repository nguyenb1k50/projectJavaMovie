package com.myclass.controller;
import java.util.List;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RestController;
import com.myclass.entity.Catagory;
import com.myclass.repository.CatagoryRepository;

@RestController
@RequestMapping("api/catagory")
public class CatagoryController {

	@Autowired
	CatagoryRepository catagoryRepository;
	
	@GetMapping("")
	public Object get() {
		List<Catagory> catagories = catagoryRepository.getAllCatagory();
		return new ResponseEntity<List<Catagory>>(catagories, HttpStatus.OK);
	}
	
	@PostMapping()
	public Object post(@RequestBody Catagory catagory) {
		Boolean p = catagoryRepository.existsByTitle(catagory.getTitle());
		if(!p) {
			catagory.setId(UUID.randomUUID().toString());
			Catagory entity = catagoryRepository.save(catagory);
			return new ResponseEntity<Catagory>(entity, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Catagory exist",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("Catagory/{id}")
	public Object post(@PathVariable String id, @RequestBody Catagory catagory) {
		
		if(catagoryRepository.existsById(id)) {
			catagory.setId(id);
			Catagory entity = catagoryRepository.save(catagory);
			return new ResponseEntity<Catagory>(entity, HttpStatus.OK);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		
		if(catagoryRepository.existsById(id)) {
			catagoryRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
}
