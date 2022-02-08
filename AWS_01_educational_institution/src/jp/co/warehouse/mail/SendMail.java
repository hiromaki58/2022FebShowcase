package jp.co.warehouse.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * This class controls the mail sending function.
 */
public class SendMail {
	public void sendMail(String mailAddress, String subject, String content) {

		final String to = mailAddress;
	    final String from = "hiromaki01aws@gmail.com";
	    // Account ID or user name
	    final String username = "hiromaki01aws";
	    
	    // Password to login the E-mail account
	    final String localPassword = "bptonwxubfoiccve";
	    
	    // Set the mail server
	    String host = "smtp.gmail.com";
	    Properties props = new Properties();
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.ssl.enable", "true");
	    props.put("mail.smtp.auth", "true");

	    //Try to authorize the session with E-mail server
	    Session session = Session.getInstance(props,
	    new javax.mail.Authenticator() {
	       @Override
		protected PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(username, localPassword);
	       }
	    });

	    //Set up the E-mail message
	    try {
	    	  Message msg = new MimeMessage(session);
	    	  //set from
	    	  msg.setFrom(new InternetAddress(from));
	    	  //set to
	    	  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	    	  msg.setSubject(subject);
	    	  msg.setText(content);
	    	  Transport.send(msg);
	    } catch (AddressException e) {
	    	throw new RuntimeException(e);
	    } catch (MessagingException e) {
	    	throw new RuntimeException(e);
	    }
	}
}