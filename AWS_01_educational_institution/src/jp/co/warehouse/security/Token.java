package jp.co.warehouse.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*
 * Creating token.
 * It is for the confirmation function when the registered user E-mail is modified.
 */
public class Token {
	//Determine the size of token
	private int TOKEN_LENGTH = 16;

	public String tokenCreater() {
		byte token[] = new byte[TOKEN_LENGTH];
	    StringBuffer buf = new StringBuffer();
	    SecureRandom random = null;

	    try {
	      random = SecureRandom.getInstance("SHA1PRNG");
	      random.nextBytes(token);

	      for (int i = 0; i < token.length; i++) {
	        buf.append(String.format("%02x", token[i]));
	      }
	    }
	    catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    return buf.toString();
	}
}