package kegelmeisterschaft.controller;

import java.io.IOException;
import java.text.ParseException;

import kegelmeisterschaft.service.importer.ImporterService;
import kegelmeisterschaft.service.result.ResultService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ImportController {
    @Autowired
    private ImporterService importService;

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/import/{pwd}")
    public ModelAndView importData(@PathVariable("pwd") String pwd) throws IOException, ParseException {
	if (StringUtils.equals(pwd, "nine"))
	    importService.performImport();
	ModelAndView mv = new ModelAndView("start");
	mv.addObject("headTop", resultService.getNextHeadModel());
	return mv;
    }

}
