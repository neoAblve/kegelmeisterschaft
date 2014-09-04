package kegelmeisterschaft.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.ClubBean.ClubType;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;
import kegelmeisterschaft.model.EventModel;
import kegelmeisterschaft.util.EventUtil;

public class ClubDetailResultModel extends ClubResultModel {

    private List<EventModel> events = new ArrayList<EventModel>();
    private List<EventModel> checkerEvents = new ArrayList<EventModel>();
    private List<PlayerResultModel> results = new ArrayList<PlayerResultModel>();

    private boolean hasNotClubRelevant = false;

    private String type = "";

    public ClubDetailResultModel(ClubBean club, List<ResultBean> clubResults,
	    Map<PlayerBean, List<ResultBean>> playerResults,
	    List<EventBean> events, List<EventBean> checkerEvents) {
	super(club, clubResults);

	if (ClubType.MALE == club.getType())
	    type = "Herrenclub";
	else if (ClubType.FEMALE == club.getType())
	    type = "Damenclub";
	else
	    type = "gemischter Club";

	for (Entry<PlayerBean, List<ResultBean>> entry : playerResults
		.entrySet()) {
	    // Marker ob der Club, nicht Club-relevante Ergebnisse enthält
	    if (!hasNotClubRelevant) {
		for (ResultBean result : entry.getValue()) {
		    if (!result.isClubRelevant()) {
			hasNotClubRelevant = true;
			break;
		    }
		}
	    }
	    PlayerBean player = entry.getKey();

	    results.add(new PlayerResultModel(player, entry.getValue(),
		    topResults, club.equals(player.getSingleLeagueClub())));
	}

	Collections.sort(results, RoundResultModel.ROUND_TOTAL_COMPARATOR);
	Collections.reverse(results);

	for (EventBean event : events)
	    this.events.add(new EventModel(event));
	Collections.sort(this.events, EventModel.EVENT_MODEL_COMPARATOR);

	this.checkerEvents = EventUtil.summarizeEvents(checkerEvents, false,
		club);
    }

    public List<PlayerResultModel> getPlayerResults() {
	return results;
    }

    public List<EventModel> getEvents() {
	return events;
    }

    public boolean isHasNotClubRelevant() {
	return hasNotClubRelevant;
    }

    public void setHasNotClubRelevant(boolean hasNotClubRelevant) {
	this.hasNotClubRelevant = hasNotClubRelevant;
    }

    public List<EventModel> getCheckerEvents() {
	return checkerEvents;
    }

    public void setCheckerEvents(List<EventModel> checkerEvents) {
	this.checkerEvents = checkerEvents;
    }

    public String getType() {
	return type;
    }

}
