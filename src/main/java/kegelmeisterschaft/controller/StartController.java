package kegelmeisterschaft.controller;

import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class StartController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView start() {
	ModelAndView mv = new ModelAndView("start");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/impressum")
    public ModelAndView impressum() {
	ModelAndView mv = new ModelAndView("imprint");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gaestebuch")
    public ModelAndView guest() {
	ModelAndView mv = new ModelAndView("guest");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/kinder")
    public ModelAndView kids() {
	ModelAndView mv = new ModelAndView("kids");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tandem")
    public ModelAndView tandem() {
	ModelAndView mv = new ModelAndView("tandem");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/news")
    public ModelAndView news() {
	ModelAndView mv = new ModelAndView("news");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }
}
