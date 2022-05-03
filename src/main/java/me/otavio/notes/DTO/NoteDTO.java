package me.otavio.notes.DTO;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data public class NoteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String content;
	private Instant lastModified;
	private Instant createdAt;
	
	public NoteDTO() { }
}
