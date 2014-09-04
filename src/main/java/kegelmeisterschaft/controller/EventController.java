package kegelmeisterschaft.controller;

import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class EventController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/termine")
    public ModelAndView showMenClubs() {
	ModelAndView mv = new ModelAndView("events");
	mv.addObject("data", resultService.provideEvents());
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

}
