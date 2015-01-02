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
	    Properties props = System.getProperties();
	    // Gmail
	    final String username = "mathis.schweitzer";
	    final String password = "p7zVKZJP1";
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    // web.de
	    // final String username = "mathis.schweitzer@web.de";
	    // final String password = "p7zVKZJP";
	    // props.put("mail.smtp.host", "smtp.web.de");
	    // props.put("mail.smtp.port", "587");
	    // props.put("mail.transport.protocol", "smtp");
	    // props.put("mail.smtp.auth", "true");
	    // props.put("mail.smtp.starttls.enable", "true");

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
	    String subject = "Anfrage von " + contact.getName() + " am " + sentDate;
	    msg.setSubject(subject, "UTF-8");

	    String message = "Nachricht von " + contact.getName() + " (" + contact.getEmail() + ") abgeschickt um "
		    + sentDate + ": " + contact.getMessage();
	    msg.setContent(message, "text/plain; charset=UTF-8");
	    System.out.println(subject);
	    System.out.println(message);
	    Transport.send(msg);
	    System.out.println("Sent message successfully....");
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static void main(String[] args) {
	MailerService service = new MailerService();
	ContactModel model = new ContactModel();
	model.setEmail("mathis.schweitzer@gmx.de");
	model.setMessage("das ist eine Testnachricht");
	model.setName("Birte213123");
	service.sendInfoMail(model);
    }

}
