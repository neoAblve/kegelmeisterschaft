package kegelmeisterschaft.controller;

import kegelmeisterschaft.model.ConfigModelUtil;
import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class EventController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/termine")
    public ModelAndView showMenClubs(@PathVariable("year") String year) {
	ModelAndView mv = new ModelAndView("events");
	mv.addObject("year", year);
	mv.addObject("otherYears", ConfigModelUtil.getOtherYears(year));
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("data", resultService.provideEvents(year));
	return mv;
    }

}
