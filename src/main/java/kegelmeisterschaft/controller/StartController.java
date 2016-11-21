package kegelmeisterschaft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class StartController {

    @Autowired
    ControllerHelper helper;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView start() {
	ModelAndView mv = new ModelAndView("start");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/impressum")
    public ModelAndView impressum() {
	ModelAndView mv = new ModelAndView("imprint");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gaestebuch")
    public ModelAndView guest() {
	ModelAndView mv = new ModelAndView("guest");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/kinder")
    public ModelAndView kids(@PathVariable("year") String year) {
	ModelAndView mv = new ModelAndView(year + "/kids");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/tandem")
    public ModelAndView tandem(@PathVariable("year") String year) {
	ModelAndView mv = new ModelAndView(year + "/tandem");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/ausrichter")
    public ModelAndView ausrichter(@PathVariable("year") String year) {
	ModelAndView mv = new ModelAndView(year + "/ausrichter");
	helper.addCommonModels(mv);
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/news")
    public ModelAndView news() {
	ModelAndView mv = new ModelAndView("news");
	helper.addCommonModels(mv);
	return mv;
    }
}
