package kegelmeisterschaft.service.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kegelmeisterschaft.model.contact.ContactModel;

import org.springframework.stereotype.Component;

@Component
public class MailerService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm.ss");
    private static final String USERNAME = "your username";
    private static final String PASSWORD = "your password";
    private static final String SMTP_HOSTNAME = "your smtp server";
    private static final String IMAP_HOSTNAME = "your imap server";

    public boolean sendInfoMail(ContactModel contact) {
	try {
	    doImap();

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", SMTP_HOSTNAME);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.user", USERNAME);
	    props.put("mail.password", PASSWORD);

	    javax.mail.Authenticator auth = new javax.mail.Authenticator() {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(USERNAME, PASSWORD);
		}
	    };

	    Session session = Session.getInstance(props, auth);
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(USERNAME));
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

    private void doImap() {
	try {
	    Properties props = new Properties();
	    props.setProperty("mail.imap.port", "993");
	    props.setProperty("mail.imap.ssl.enable", "true");
	    Session session = Session.getDefaultInstance(props, null);
	    Store store = session.getStore("imaps");
	    store.connect(IMAP_HOSTNAME, USERNAME, PASSWORD);

	    Folder inbox = store.getFolder("INBOX");
	    inbox.open(Folder.READ_ONLY);

	    inbox.close(false);
	    store.close();
	} catch (NoSuchProviderException e) {
	    e.printStackTrace();
	} catch (MessagingException e) {
	    e.printStackTrace();
	}
    }
}
