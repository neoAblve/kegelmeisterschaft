package kegelmeisterschaft.controller;

import java.util.Calendar;

import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ControllerHelper {

    @Autowired
    private ResultService resultService;

    public void addCommonModels(ModelAndView mv) {
	mv.addObject("headTop", resultService.getNextHeadModel());
	mv.addObject("currentWeek", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
    }

}
