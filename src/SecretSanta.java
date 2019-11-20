import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

public class SecretSanta {
	public static void send(String from, String password, String to, String sub, String msg) {
		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setText(msg);
			// send message
			Transport.send(message);
			//System.out.println("message sent successfully via Eclipse to " + to);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static int getRandom(List<String> emails, String i) {

		int r = new Random().nextInt(emails.size());
		while (emails.get(r).equalsIgnoreCase(i)) {		
			r = new Random().nextInt(emails.size());
	
		}
		
		return r;

	}

	public static void main(String[] args) {

		String loginEmail = "xxxx";
		String loginPass = "xxxx";
		System.out.println("Drawing started...");
		System.out.println("=======================");
		String[] emails = { 
				"email1@gmail.com", 
				"email2@gmail.com", 
				"email3@gmail.com", 
				"email4@gmail.com" };


		Map<String, String> hm = new HashMap<>();

		int size = emails.length;
		List<String> draw = new ArrayList<>(Arrays.asList(emails));
		for (int i = 0; i < size; i++) {
			if (!hm.containsKey(emails[i])) {
				int j = getRandom(draw, emails[i]);
				hm.put(emails[i], draw.get(j));
				draw.remove(j);
				
			}
		}
		
		for(String s : hm.keySet()) {
			String receiver = hm.get(s);
			String body = "Hey!\n\nYou (" + s + ") will be the Secret Santa of " + receiver + ". Please keep this as a secret until gift exchange!\n\nxxoo\nNov.2019";
			
			SecretSanta.send(loginEmail,loginPass, s, "Secret Santa - 2019 !",body);

			System.out.println("Email successfully sent to " + s);
		}
		
		System.out.println("=======================");
		System.out.println("Drawing completed...");

		
		
	}
}