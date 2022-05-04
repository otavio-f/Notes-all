package me.otavio.notes;


import static me.otavio.notes.utils.SecurityUtils.genHash;
import static me.otavio.notes.utils.SecurityUtils.genSalt;
import static me.otavio.notes.utils.SecurityUtils.match;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


public class SecurityUtils {
	
	@Test
	void passwordMatches() {
		String password = "A very long password with special characters!";
		byte[] salt = genSalt();
		byte[] hash = genHash(password, salt);
		
		assertTrue(match(password, salt, hash));
	}
	
	@Test
	void wrongPasswordDoesntMatch() {
		String password = "A very long password with special characters!";
		byte[] salt = genSalt();
		byte[] hash = genHash(password, salt);
		String wrongPassword = "No!";
		
		assertFalse(match(wrongPassword, salt, hash));
	}
	
	@Test
	void nullArgumentOnHashGenerationThrows() {
		final String password = "Password";
		final byte[] salt = genSalt();
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				genHash(null, salt);
			}
		});
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				genHash(password, null);
			}
		});
	}

	@Test
	void nullArgumentOnHashVerificationThrows() {
		final String password = "Password";
		final byte[] salt = genSalt();
		final byte[] hash = genHash(password, salt);
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				match(null, hash, salt);
			}
		});
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				match(password, null, salt);
			}
		});
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				match(password, hash, null);
			}
		});
	}
}
