package kegelmeisterschaft.model.contact;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ContactModel {

    @NotBlank(message = "Bitte gib deinen Namen an.")
    private String name;

    @NotBlank(message = "Bitte gib deine gültige Email-Adresse an.")
    @Email(message = "Bitte gib deine gültige Email-Adresse an.")
    private String email;

    @NotBlank(message = "Bitte gib eine Nachricht an.")
    private String message;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}
