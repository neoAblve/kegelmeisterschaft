package kegelmeisterschaft.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.model.EventModel;

public final class EventUtil {

    public static ArrayList<EventModel> summarizeEvents(List<EventBean> events,
	    boolean withWeekHeader) {
	return summarizeEvents(events, withWeekHeader, null);
    }

    public static ArrayList<EventModel> summarizeEvents(List<EventBean> events,
	    boolean withWeekHeader, ClubBean club) {
	HashMap<EventModel, EventModel> dataMap = new HashMap<EventModel, EventModel>();
	for (EventBean event : events) {
	    EventModel newModel = new EventModel(event, club);
	    EventModel existingModel = dataMap.get(newModel);
	    if (existingModel == null)
		dataMap.put(newModel, newModel);
	    else
		existingModel.addRound(event.getRound());
	}

	ArrayList<EventModel> temp = new ArrayList<EventModel>(dataMap.values());
	Collections.sort(temp, EventModel.EVENT_MODEL_COMPARATOR);

	EventModel previous = null;
	Calendar cal = Calendar.getInstance();
	ArrayList<EventModel> data = new ArrayList<EventModel>();
	for (EventModel event : temp) {
	    int previousWeek = -1;
	    if (previous != null) {
		cal.setTime(previous.getDate());
		previousWeek = cal.get(Calendar.WEEK_OF_YEAR);
	    }

	    cal.setTime(event.getDate());
	    int week = cal.get(Calendar.WEEK_OF_YEAR);
	    if (withWeekHeader && week != previousWeek)
		data.add(new EventModel(week));
	    data.add(event);
	    previous = event;
	}
	return data;
    }
}
