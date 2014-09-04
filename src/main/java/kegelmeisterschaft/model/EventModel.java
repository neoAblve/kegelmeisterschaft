package kegelmeisterschaft.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

public class EventModel {

    private final static SimpleDateFormat EVENT_DATE_TIME_FORMAT = new SimpleDateFormat(
	    "dd.MM.yyyy '<br />'HH:mm 'Uhr'");
    private final static SimpleDateFormat EVENT_DATE_TIME_INLINE_FORMAT = new SimpleDateFormat(
	    "dd.MM.yyyy HH:mm 'Uhr'");
    private final static SimpleDateFormat EVENT_DAY_FORMAT = new SimpleDateFormat(
	    "dd.MM.yyyy, EEEEE");
    private final static SimpleDateFormat EVENT_TIME_FORMAT = new SimpleDateFormat(
	    "HH:mm 'Uhr'");

    public static Comparator<EventModel> EVENT_MODEL_COMPARATOR = new Comparator<EventModel>() {
	@Override
	public int compare(EventModel o1, EventModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getDate(), o2.getDate());
	    return builder.toComparison();
	}
    };

    private boolean newWeek = false;
    private int week;

    private Date date;
    private ClubBean club;
    private String round;
    private String location;
    private ClubBean checkerClub1;
    private ClubBean checkerClub2;

    public EventModel(int week) {
	newWeek = true;
	this.week = week;
    }

    public EventModel(EventBean event) {
	this(event, null);
    }

    public EventModel(EventBean event, ClubBean club) {
	date = event.getDate();
	this.club = event.getClub();
	round = String.valueOf(event.getRound());
	location = event.getLocation();
	if (club != null) {
	    checkerClub1 = event.getChecker1Club();
	    if (checkerClub1.equals(club))
		checkerClub1 = event.getChecker2Club();
	} else {
	    checkerClub1 = event.getChecker1Club();
	    checkerClub2 = event.getChecker2Club();
	}
    }

    public Date getDate() {
	return date;
    }

    public String getDateTime() {
	if (date == null)
	    return "";
	return EVENT_DATE_TIME_FORMAT.format(date);
    }

    public String getDateTimeInline() {
	if (date == null)
	    return "";
	return EVENT_DATE_TIME_INLINE_FORMAT.format(date);
    }

    public String getDateDay() {
	return EVENT_DAY_FORMAT.format(date);
    }

    public String getTime() {
	return EVENT_TIME_FORMAT.format(date);
    }

    public String getLocation() {
	return location;
    }

    public ClubBean getCheckerClub1() {
	return checkerClub1;
    }

    public ClubBean getCheckerClub2() {
	return checkerClub2;
    }

    public String getRound() {
	return round;
    }

    public ClubBean getClub() {
	return club;
    }

    public boolean isNewWeek() {
	return newWeek;
    }

    public int getWeek() {
	return week;
    }

    public void addRound(int round) {
	if (StringUtils.isNotBlank(this.round))
	    this.round += " + ";
	this.round += String.valueOf(round);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((club == null) ? 0 : club.hashCode());
	Calendar cal = Calendar.getInstance();
	if (date != null)
	    cal.setTime(date);
	int day1 = cal.get(Calendar.DAY_OF_YEAR);
	result = prime * result + ((date == null) ? 0 : day1);
	result = prime * result
		+ ((checkerClub1 == null) ? 0 : checkerClub1.hashCode());
	result = prime * result
		+ ((checkerClub2 == null) ? 0 : checkerClub2.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EventModel other = (EventModel) obj;
	if (club == null) {
	    if (other.club != null)
		return false;
	} else if (!club.equals(other.club))
	    return false;
	if (date == null) {
	    if (other.date != null)
		return false;
	} else {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int day1 = cal.get(Calendar.DAY_OF_YEAR);
	    cal.setTime(other.date);
	    int day2 = cal.get(Calendar.DAY_OF_YEAR);
	    if (day1 != day2)
		return false;
	}

	if (checkerClub1 == null) {
	    if (other.checkerClub1 != null)
		return false;
	} else if (!checkerClub1.equals(other.checkerClub1))
	    return false;
	if (checkerClub2 == null) {
	    if (other.checkerClub2 != null)
		return false;
	} else if (!checkerClub2.equals(other.checkerClub2))
	    return false;
	return true;
    }
}
