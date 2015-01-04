package kegelmeisterschaft.service.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kegelmeisterschaft.model.contact.ContactModel;

import org.springframework.stereotype.Component;

@Component
public class MailerService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm.ss");

    public boolean sendInfoMail(ContactModel contact) {
	try {
	    // web.de
	    final String username = "mathis.schweitzer@web.de";
	    final String password = "p7zVKZJP";

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.web.de");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    props.put("mail.smtp.user", username);
	    props.put("mail.password", password);

	    javax.mail.Authenticator auth = new javax.mail.Authenticator() {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(username, password);
		}
	    };

	    Session session = Session.getDefaultInstance(props, auth);

	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(username));
	    msg.setReplyTo(new Address[] { new InternetAddress(contact.getEmail(), contact.getName()) });
	    msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fragen@ksm-balve.de", "Fragen KSM-Balve"));

	    String sentDate = sdf.format(new Date());
	    String subject = "Kontaktformular: Anfrage von " + contact.getName() + " am " + sentDate;
	    msg.setSubject(subject, "UTF-8");

	    String message = "Nachricht von " + contact.getName() + " (" + contact.getEmail() + ") abgeschickt um "
		    + sentDate + ":\n" + contact.getMessage();
	    msg.setContent(message, "text/plain; charset=UTF-8");
	    Transport.send(msg);
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }
}
