package kegelmeisterschaft.controller;

import kegelmeisterschaft.entities.PlayerBean.Gender;
import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class CheckerController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/aufschreiber/herren")
    public ModelAndView showMaleOverview(WebRequest webRequest) {
	ModelAndView mv = new ModelAndView("checkerOverview");
	mv.addObject("type", "Herren");
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("data",
		resultService.provideCheckerResultsByGender(Gender.MALE));
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/aufschreiber/damen")
    public ModelAndView showFemaleOverview(WebRequest webRequest) {
	ModelAndView mv = new ModelAndView("checkerOverview");
	mv.addObject("type", "Damen");
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("data",
		resultService.provideCheckerResultsByGender(Gender.FEMALE));
	return mv;
    }
}
