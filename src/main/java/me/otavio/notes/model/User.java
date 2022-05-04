package me.otavio.notes.model;

import lombok.Data;

@Data public class User {
	private final String name;
	private final byte[] password;
	private final byte[] salt;
}
