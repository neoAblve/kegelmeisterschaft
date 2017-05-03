package kegelmeisterschaft.controller;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kegelmeisterschaft.service.importer.ImporterService;

@Controller
@RequestMapping
public class ImportController {
    private static final String IMPORT_PASSWORD = "your_import_password";

    @Autowired
    private ImporterService importService;

    @Autowired
    ControllerHelper helper;

    @RequestMapping(method = RequestMethod.GET, value = "/import/{pwd}")
    public ModelAndView importData(@PathVariable("pwd") String pwd) throws IOException, ParseException {
	if (StringUtils.equals(pwd, IMPORT_PASSWORD))
	    importService.performImport();
	ModelAndView mv = new ModelAndView("start");
	helper.addCommonModels(mv);
	return mv;
    }

}
