package kegelmeisterschaft.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class PlayerDetailModel {

    private PlayerBean player;

    private List<PlayerDetailsResultModel> single = new ArrayList<PlayerDetailsResultModel>();

    private List<PlayerDetailsResultModel> checker = new ArrayList<PlayerDetailsResultModel>();

    private HashMap<ClubBean, List<PlayerDetailsResultModel>> clubs = new HashMap<ClubBean, List<PlayerDetailsResultModel>>();

    public PlayerDetailModel(PlayerBean player, List<ResultBean> playerResults,
	    HashMap<ClubBean, List<EventBean>> events) {
	this.player = player;

	for (ResultBean result : playerResults) {
	    ClubBean club = result.getClub();
	    if (player.getSingleLeagueClub() != null
		    && player.getSingleLeagueClub().equals(club)
		    && !result.isChecker())
		single.add(new PlayerDetailsResultModel(getEventByClubAndRound(
			events, club, result.getRound()), result));
	    else if (!result.isChecker()) {
		List<PlayerDetailsResultModel> clubResults = clubs.get(club);
		if (clubResults == null) {
		    clubResults = new ArrayList<PlayerDetailsResultModel>();
		    clubs.put(club, clubResults);
		}
		clubResults
			.add(new PlayerDetailsResultModel(
				getEventByClubAndRound(events, club,
					result.getRound()), result));
	    } else
		checker.add(new PlayerDetailsResultModel(
			getEventByClubAndRound(events, club, result.getRound()),
			result));

	    Collections.sort(checker,
		    new Comparator<PlayerDetailsResultModel>() {
			@Override
			public int compare(PlayerDetailsResultModel o1,
				PlayerDetailsResultModel o2) {
			    CompareToBuilder builder = new CompareToBuilder();
			    builder.append(o1.getResult().getScore(), o2
				    .getResult().getScore());
			    builder.append(o1.getResult().getNinerCount(), o2
				    .getResult().getNinerCount());
			    return builder.toComparison() * -1;
			}
		    });
	}
    }

    private EventBean getEventByClubAndRound(
	    HashMap<ClubBean, List<EventBean>> events, ClubBean club, int round) {
	List<EventBean> clubEvents = events.get(club);
	for (EventBean event : clubEvents) {
	    if (event.getRound() == round)
		return event;
	}
	return new EventBean();
    }

    public PlayerBean getPlayer() {
	return player;
    }

    public List<PlayerDetailsResultModel> getSingle() {
	return single;
    }

    public List<PlayerDetailsResultModel> getChecker() {
	return checker;
    }

    public HashMap<ClubBean, List<PlayerDetailsResultModel>> getClubs() {
	return clubs;
    }

    public boolean isHasSingle() {
	return single.size() > 0;
    }

    public boolean isHasOther() {
	return clubs.size() > 0;
    }

    public boolean isHasChecker() {
	return checker.size() > 0;
    }
}
