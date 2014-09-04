package kegelmeisterschaft.model.result;

import java.text.SimpleDateFormat;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.StringUtils;

public class CheckerResultModel extends ResultModel {

    public final static SimpleDateFormat EVENT_FORMAT = new SimpleDateFormat(
	    "dd.MM.yyyy HH:mm 'Uhr'");

    private int rank;
    private PlayerBean checker;
    private EventBean event;
    private ClubBean clubToCheck;
    private ClubBean checkerClub;

    public CheckerResultModel(EventBean event, ResultBean result) {
	super(result.getScore(), result.getNinerCount());
	this.event = event;
	this.clubToCheck = event.getClub();
	this.checker = result.getPlayer();

	if (checker.getClubs().contains(event.getChecker1Club()))
	    checkerClub = event.getChecker1Club();
	else if (checker.getClubs().contains(event.getChecker2Club()))
	    checkerClub = event.getChecker2Club();
	else if (checker.getSingleLeagueClub() != null)
	    checkerClub = checker.getSingleLeagueClub();
	else
	    checkerClub = new ClubBean();

    }

    public PlayerBean getChecker() {
	return checker;
    }

    public EventBean getEvent() {
	return event;
    }

    public ClubBean getClubToCheck() {
	return clubToCheck;
    }

    public ClubBean getCheckerClub() {
	return checkerClub;
    }

    public String getDate() {
	if (event.getDate() == null)
	    return "";
	return EVENT_FORMAT.format(event.getDate());
    }

    public int getRank() {
	return rank;
    }

    public void setRank(int rank) {
	this.rank = rank;
    }

    public String getLocation() {
	if (StringUtils.contains(clubToCheck.getLocation(), "/"))
	    return clubToCheck.getLocation().split("/")[0];

	return clubToCheck.getLocation();
    }

}