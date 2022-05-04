package me.otavio.notes.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SecurityUtils {
	
	/***
	 * Generates a 16 byte random salt
	 * @return
	 */
	public static byte[] genSalt() {
		SecureRandom randomness = new SecureRandom();
		// Is there any performance implications of creating everytime?
		byte[] salt = new byte[16];
		randomness.nextBytes(salt);
		return salt;
	}
	
	/***
	 * Concatenate two byte arrays
	 * @param a
	 * @param b
	 * @throws NullPointerException if any parameter is null
	 * @return the result of concatenation (a+b)
	 */
	private static byte[] concatBytes(byte[] a, byte[] b) {
		if (a==null||b==null)
			throw new NullPointerException();
		byte[] output = new byte[a.length + b.length];
		System.arraycopy(a, 0, output, 0, a.length);
		System.arraycopy(b, 0, output, a.length, b.length);
		// System.arraycopy(source, srcPos, destination, destPos, length);
		return output;
	}
	
	/***
	 * Generates a hash from a string and a salt.
	 * @param text An utf-8 string
	 * @param salt An array of random bytes
	 * @throws NullPointerException if any parameter is null
	 * @return the hash of the salted text, as an array of bytes, or null if an error occurred
	 */
	public static byte[] genHash(String text, byte[] salt) {
		if (text==null||salt==null)
			throw new NullPointerException();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
			byte[] saltedText = concatBytes(textBytes, salt);
			byte[] hash = digest.digest(saltedText);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			// It shouldn't get here because the algorithm is hard coded
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * Verify if the text is equal to the hash
	 * @param text The text
	 * @param salt The original random array of bytes used to hash
	 * @param hash The hash to be checked against
	 * @throws NullPointerException if any parameter is null
	 * @return
	 */
	public static boolean match(String text, byte[] salt, byte[] hash) {
		if (text==null || salt==null || hash==null)
			throw new NullPointerException();
		return Arrays.equals(hash, genHash(text, salt));
	}
}
