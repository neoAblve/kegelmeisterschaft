package kegelmeisterschaft.controller;

import kegelmeisterschaft.entities.PlayerBean.Gender;
import kegelmeisterschaft.model.ConfigModelUtil;
import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class CheckerController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/aufschreiber/herren")
    public ModelAndView showMaleOverview(WebRequest webRequest, @PathVariable("year") String year) {
	return getCheckerResults("Herren", Gender.MALE, year);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/aufschreiber/damen")
    public ModelAndView showFemaleOverview(WebRequest webRequest, @PathVariable("year") String year) {
	return getCheckerResults("Damen", Gender.FEMALE, year);
    }

    private ModelAndView getCheckerResults(String title, Gender gender, String year) {
	ModelAndView mv = new ModelAndView("checkerOverview");
	mv.addObject("type", title);
	mv.addObject("typeLower", title.toLowerCase());
	mv.addObject("year", year);
	mv.addObject("otherYears", ConfigModelUtil.getOtherYears(year));
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("data", resultService.provideCheckerResultsByGenderAndYear(gender, year));
	return mv;
    }
}
