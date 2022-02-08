package jp.co.warehouse.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/*
 * This class is used to encrypt the information.
 */
public class Hash {

	//This method is from raw sentence to encrypted sentence
	public String hashGenerator(String rawSentence) {

		//Original sentence
        String source = rawSentence;
        //Character set for switching to the byte array before hashing
        Charset charset = StandardCharsets.UTF_8;
        //Hash algorithm
        String algorithm = "SHA-512";
        //Modify the sentence by hash
        byte[] bytes = null;

		try {
			bytes = MessageDigest.getInstance(algorithm).digest(source.getBytes(charset));
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String result = DatatypeConverter.printHexBinary(bytes);
        //This print out will tell you know the result of the hash function.
//        System.out.println("result @ hashGenerator : " + result);
		return result;
	}
}