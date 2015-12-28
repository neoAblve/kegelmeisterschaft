package kegelmeisterschaft.controller;

import java.util.Comparator;

import kegelmeisterschaft.entities.ClubBean.ClubType;
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
public class ClubController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/clubs/herren")
    public ModelAndView showMenClubs(@PathVariable("year") String year,
	    @RequestParam(value = "column", defaultValue = "") final String column,
	    @RequestParam(value = "order", defaultValue = "desc") final String order) {
	return createModel(ClubType.MALE, "Herren", "/ksm/" + year + "/clubs/herren", column, order, year);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/clubs/damen")
    public ModelAndView showWomen(@PathVariable("year") String year,
	    @RequestParam(value = "column", defaultValue = "") final String column,
	    @RequestParam(value = "order", defaultValue = "desc") final String order) {
	return createModel(ClubType.FEMALE, "Damen", "/ksm/" + year + "/clubs/damen", column, order, year);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{year}/clubs/mixed")
    public ModelAndView showMixed(@PathVariable("year") String year,
	    @RequestParam(value = "column", defaultValue = "") final String column,
	    @RequestParam(value = "order", defaultValue = "desc") final String order) {
	return createModel(ClubType.MIXED, "Gemischte Clubs", "/ksm/" + year + "/clubs/mixed", column, order, year);
    }

    private ModelAndView createModel(ClubType type, String descr, String rootURL, String column, String order,
	    String year) {
	if (column == null)
	    column = "";
	Comparator<RoundResultModel> comp = RoundResultModel.ORDERS.get(column);
	if (comp == null)
	    column = "";

	boolean desc = true;
	if (order != null && StringUtils.equals(order, "asc"))
	    desc = false;

	String oldType = descr.toLowerCase();
	if (type == ClubType.MIXED)
	    oldType = type.name().toLowerCase();

	ModelAndView mv = new ModelAndView("clubOverview");
	mv.addObject("type", descr);
	mv.addObject("typeLower", oldType);
	mv.addObject("year", year);
	mv.addObject("otherYear", ConfigModelUtil.getOtherYear(year));
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("rootURL", rootURL);
	mv.addObject("column", column);
	mv.addObject("order", desc ? "asc" : "desc");
	mv.addObject("results", resultService.provideClubsResultsByTypeAndYear(type, comp, desc, year));
	return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/club/{id}")
    public ModelAndView showClubDetail(@PathVariable("id") int id) {
	ModelAndView mv = new ModelAndView("clubDetail");
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("model", resultService.provideClubDetails(id));
	return mv;
    }
}
