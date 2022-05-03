package me.otavio.notes.controller;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import me.otavio.notes.DTO.NoteDTO;
import me.otavio.notes.model.Note;
import me.otavio.notes.repository.NoteRepository;

@RestController
@RequestMapping("/api/v1")
public class NotesController {
	
	// @Autowired
	private NoteRepository repository;
	
	@Autowired
	public NotesController() {
		repository = NoteRepository.getInstance();
	}
	
	/**
	 * Converts a Note into a NoteDTO object.
	 * @param note The Note object to be converted
	 * @return A equivalent NoteDTO
	 */
	private NoteDTO convert(Note note) {
		NoteDTO data = new NoteDTO();
		data.setTitle(note.getTitle());
		data.setContent(note.getContent());
		data.setCreatedAt(note.getCreatedAt());
		data.setLastModified(note.getLastModified());
		return data;
	}
	
	/**
	 * Converts a NoteDTO into a Note object.
	 * Warning: This method doesn't set the Note's Id!
	 * @param data The NoteDTO instance to be converted
	 * @return A equivalent Note
	 */
	private Note convert(NoteDTO data) { //this doesn't set the note's Id!!
		Note note = new Note();
		note.setTitle(data.getTitle());
		note.setContent(data.getContent());
		note.setCreatedAt(data.getCreatedAt());
		note.setLastModified(data.getLastModified());
		return note;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<NoteDTO>> index() {
		List<NoteDTO> result = new ArrayList<NoteDTO>();
		
		for (Note note: repository.getAll())
			result.add(convert(note));
		return ResponseEntity.ok(result);
	}
	
//PathVairable, id -> url/{id} -> fetches id
//RequestParam, q -> url/destination?q={value} -> fetches value
//RequestBody -> fetches body request
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteDTO> getOne(@PathVariable(name="id") Integer id) {
		Optional<Note> result = repository.getById(id);
		if (result.isEmpty())
			return ResponseEntity.notFound().build();
		
		NoteDTO data = convert(result.get());
		return ResponseEntity.ok(data);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> add(@RequestBody NoteDTO item) {
		Note note = convert(item);
		Instant timestamp = Instant.now();
		note.setCreatedAt(timestamp);
		note.setLastModified(timestamp);
		repository.save(note);
		String path = String.format("/%d", note.getId());
		URI url = URI.create(path);
		return ResponseEntity.created(url).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable(name="id") int id, @RequestBody NoteDTO item) {
		Optional<Note> result = repository.getById(id);
		if (result.isEmpty())
			return ResponseEntity.notFound().build();
		
		Note note = convert(item);
		note.setId(id);
		note.setLastModified(Instant.now());
		repository.update(note);
		URI url = URI.create(String.format("/%d", id));
		return ResponseEntity.created(url).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable(name = "id") Integer id) {
		Optional<Note> result = repository.getById(id);
		if (result.isEmpty())
			return ResponseEntity.notFound().build();
		
		repository.delete(id);
		return ResponseEntity.noContent().build();
	}
}

