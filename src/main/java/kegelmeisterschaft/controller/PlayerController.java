package kegelmeisterschaft.controller;

import java.util.Comparator;

import kegelmeisterschaft.entities.PlayerBean.Gender;
import kegelmeisterschaft.model.ConfigModelUtil;
import kegelmeisterschaft.model.result.RoundResultModel;
import kegelmeisterschaft.service.result.ResultService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class PlayerController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/einzel/herren")
    public ModelAndView showMaleOverview(@PathVariable("year") String year,
	    @RequestParam(value = "column", defaultValue = "") final String column,
	    @RequestParam(value = "order", defaultValue = "desc") final String order) {
	return createModel(Gender.MALE, "Herren", "/ksm/" + year + "/einzel/herren", column, order, year);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/einzel/damen")
    public ModelAndView showFemaleOverview(@PathVariable("year") String year,
	    @RequestParam(value = "column", defaultValue = "") final String column,
	    @RequestParam(value = "order", defaultValue = "desc") final String order) {
	return createModel(Gender.FEMALE, "Damen", "/ksm/" + year + "/einzel/damen", column, order, year);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/kegler/{id}")
    public ModelAndView getPlayerDetail(@PathVariable("id") int id) {
	ModelAndView mv = new ModelAndView("playerDetail");
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("model", resultService.providePlayerDetails(id));
	return mv;
    }

    private ModelAndView createModel(Gender gender, String descr, String rootURL, String column, String order,
	    String year) {
	if (column == null)
	    column = "";
	Comparator<RoundResultModel> comp = RoundResultModel.ORDERS.get(column);
	if (comp == null)
	    column = "";

	boolean desc = true;
	if (order != null && StringUtils.equals(order, "asc"))
	    desc = false;

	ModelAndView mv = new ModelAndView("playerOverview");
	mv.addObject("type", descr);
	mv.addObject("typeLower", descr.toLowerCase());
	mv.addObject("year", year);
	mv.addObject("otherYear", ConfigModelUtil.getOtherYear(year));
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("rootURL", rootURL);
	mv.addObject("column", column);
	mv.addObject("order", desc ? "asc" : "desc");
	mv.addObject("data", resultService.providePlayerResultsByGenderAndYear(gender, comp, desc, year));
	return mv;
    }

}
