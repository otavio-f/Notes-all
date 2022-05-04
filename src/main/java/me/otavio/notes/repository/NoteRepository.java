package me.otavio.notes.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.otavio.notes.model.Note;

/**
 * A singleton class to store Note
 * This is a placeholder for the real database
 * @author otavi
 *
 */
public class NoteRepository {
	
	private static NoteRepository instance = null;
	
	public static NoteRepository getInstance() {
		if (NoteRepository.instance == null)
			NoteRepository.instance = new NoteRepository();
		return NoteRepository.instance;
	}
	
	private List<Note> notes;
	
	private NoteRepository() {
		notes = new ArrayList<Note>();
	/*	Note note1 = new Note();
		note1.setTitle("OK");
		note1.setContent("VERY LONG CONTENT");
		note1.setCreatedAt(Instant.now());
		note1.setLastModified(Instant.now());
		this.save(note1);
		

		Note note2 = new Note();
		note2.setTitle("OK");
		note2.setContent("VERY LONG CONTENT");
		note2.setCreatedAt(Instant.now());
		note2.setLastModified(Instant.now());
		this.save(note2);
	*/
	}
	
	private int findNextId() {
		boolean found = false;
		int index = 1;
		do {
			for (Note note : notes) {
				found = (note.getId() == index);
				if (found) {
					index++;
					break;
				}
			}
		} while (found);
		
		// System.out.printf("Found next id: %d\n", index);
		return index;
	}
	
	public List<Note> getAll() {
		return new ArrayList<Note>(notes);
	}
	
	public Optional<Note> getById(int id) {
		for (Note note : notes)
			if (note.getId() == id)
				return Optional.of(note);
		return Optional.empty();
	}
	
	public void delete(int id) {
		notes.remove(id);
	}
	
	public void save(Note note) {
		note.setId(findNextId());
		notes.add(note);
	}
	
	public void update(Note note) {
		notes.remove(note.getId().intValue());
		notes.add(note.getId(), note);
	}
}
