package me.otavio.notes.model;

import java.io.Serializable;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a basic note
 * @author Otavio
 *
 */
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private String content;
	
	@Getter
	@Setter
	private Instant createdAt;
	
	@Getter
	@Setter
	private Instant lastModified;
	
}
