package kegelmeisterschaft.controller;

import javax.validation.Valid;

import kegelmeisterschaft.model.contact.ContactModel;
import kegelmeisterschaft.service.mail.MailerService;
import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ContactController {

    @Autowired
    MailerService mailerService;

    @Autowired
    private ResultService resultService;

    @RequestMapping(value = "/kontakt/senden", method = RequestMethod.POST)
    public ModelAndView addContact(
	    @Valid @ModelAttribute("contact") ContactModel contact,
	    BindingResult result) {
	ModelAndView mv = new ModelAndView("contact", "contact", contact);
	mv.addObject("headTop", resultService.getNextHeadModel());
	if (result.hasErrors()) {
	    mv.addObject("status", false);
	    mv.addObject("resultMessage", "Bitte prüfe deine Eingabe.");
	} else {
	    boolean successfull = mailerService.sendInfoMail(contact);
	    if (successfull) {
		mv = new ModelAndView("contact", "contact", new ContactModel());
		mv.addObject(
			"resultMessage",
			"Deine Nachricht wurde verschickt. Wir versuchen so schnell wie möglich zu antworten. Vielen Dank für deine Anfrage!");
	    } else {
		mv.addObject("resultMessage",
			"Es kam zu einem technischem Fehler, bitte versuche es später erneut.");
	    }
	    mv.addObject("status", successfull);
	}
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/kontakt")
    public ModelAndView contact() {
	return new ModelAndView("contact", "contact", new ContactModel());
    }
}
