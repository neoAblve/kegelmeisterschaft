package kegelmeisterschaft.service.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import kegelmeisterschaft.dao.ClubHome;
import kegelmeisterschaft.dao.EventHome;
import kegelmeisterschaft.dao.PlayerHome;
import kegelmeisterschaft.dao.ResultHome;
import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.ClubBean.ClubType;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.PlayerBean.Gender;
import kegelmeisterschaft.entities.ResultBean;
import kegelmeisterschaft.model.EventModel;
import kegelmeisterschaft.model.common.HeadTopModel;
import kegelmeisterschaft.model.result.CheckerResultModel;
import kegelmeisterschaft.model.result.ClubDetailResultModel;
import kegelmeisterschaft.model.result.ClubResultModel;
import kegelmeisterschaft.model.result.PlayerDetailModel;
import kegelmeisterschaft.model.result.PlayerResultModel;
import kegelmeisterschaft.model.result.ResultModel;
import kegelmeisterschaft.model.result.RoundResultModel;
import kegelmeisterschaft.util.EventUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ResultService {

    private static final String CURRENT_YEAR = "2015";

    @Autowired
    private ClubHome clubHome;
    @Autowired
    private PlayerHome playerHome;
    @Autowired
    private EventHome eventHome;
    @Autowired
    private ResultHome resultHome;

    public List<ClubResultModel> provideClubsResultsByTypeAndYear(ClubType type, Comparator<RoundResultModel> comp,
	    boolean desc, String year) {
	List<ClubResultModel> results = new ArrayList<ClubResultModel>();
	for (ClubBean club : clubHome.listByNamedQuery(ClubBean.FIND_BY_TYPE_AND_YEAR, type, year)) {
	    if (club.getName().equals("Nur Einzelwertung"))
		continue;
	    results.add(new ClubResultModel(club, resultHome.listByNamedQuery(ResultBean.FIND_BY_CLUB, club)));
	}

	if (comp != null)
	    Collections.sort(results, comp);
	else
	    Collections.sort(results, ClubResultModel.CLUB_ROUND_TOTAL_COMPARATOR);

	if (desc)
	    Collections.reverse(results);
	return results;
    }

    public ClubDetailResultModel provideClubDetails(int id) {
	ClubBean club = clubHome.findById(id);
	if (club == null)
	    return null;
	Map<PlayerBean, List<ResultBean>> playerResults = new HashMap<PlayerBean, List<ResultBean>>();
	for (PlayerBean player : playerHome.listByNamedQuery(PlayerBean.FIND_BY_CLUB_AND_YEAR, club.getName(),
		club.getYear()))
	    playerResults.put(player, resultHome.listByNamedQuery(ResultBean.FIND_BY_PLAYER_AND_CLUB, player, club));

	return new ClubDetailResultModel(club, resultHome.listByNamedQuery(ResultBean.FIND_BY_CLUB, club),
		playerResults, eventHome.listByNamedQuery(EventBean.FIND_BY_CLUB, club), eventHome.listByNamedQuery(
			EventBean.FIND_BY_CHECKER_CLUB, club, club));
    }

    public PlayerDetailModel providePlayerDetails(int id) {
	PlayerBean player = playerHome.findById(id);
	if (player == null)
	    return null;

	HashMap<ClubBean, List<EventBean>> events = new HashMap<ClubBean, List<EventBean>>();
	List<ResultBean> results = resultHome.listByNamedQuery(ResultBean.FIND_BY_PLAYER_ALL, player);
	for (ResultBean result : results) {
	    ClubBean club = result.getClub();
	    List<EventBean> clubEvents = events.get(club);
	    if (clubEvents == null)
		events.put(club, eventHome.listByNamedQuery(EventBean.FIND_BY_CLUB, club));
	}

	return new PlayerDetailModel(player, results, events);
    }

    public List<PlayerResultModel> providePlayerResultsByGenderAndYear(Gender gender,
	    Comparator<RoundResultModel> comp, boolean desc, String year) {
	List<PlayerResultModel> results = new ArrayList<PlayerResultModel>();
	List<PlayerBean> players = playerHome.listByNamedQuery(PlayerBean.FIND_BY_GENDER_AND_YEAR, gender.toString(),
		year);
	for (PlayerBean player : players) {
	    if (player.getSingleLeagueClub() == null)
		continue;
	    List<ResultBean> playerResults = resultHome.listByNamedQuery(ResultBean.FIND_BY_PLAYER, player);
	    if (playerResults.size() == 0)
		playerResults = new ArrayList<ResultBean>();
	    results.add(new PlayerResultModel(player, playerResults));
	}

	if (comp != null)
	    Collections.sort(results, comp);
	else
	    Collections.sort(results, PlayerResultModel.PLAYER_ROUND_TOTAL_COMPARATOR);

	if (desc)
	    Collections.reverse(results);
	return results;
    }

    public List<CheckerResultModel> provideCheckerResultsByGenderAndYear(Gender gender, String year) {
	Map<PlayerBean, Set<ResultBean>> checkerResultMap = new HashMap<PlayerBean, Set<ResultBean>>();
	for (ResultBean result : resultHome.listByNamedQuery(ResultBean.FIND_CHECKER_RESULTS_BY_GENDER_AND_YEAR,
		gender.toString(), year)) {
	    Set<ResultBean> checkerResult = checkerResultMap.get(result.getPlayer());
	    if (checkerResult == null) {
		checkerResult = new TreeSet<ResultBean>(ResultBean.SCORE_COMPARATOR);
		checkerResultMap.put(result.getPlayer(), checkerResult);
	    }
	    checkerResult.add(result);
	}

	List<CheckerResultModel> results = new ArrayList<CheckerResultModel>();
	for (Entry<PlayerBean, Set<ResultBean>> entry : checkerResultMap.entrySet()) {
	    ResultBean result = entry.getValue().iterator().next();
	    EventBean event = eventHome.findExactlyOneByNamedQuery(EventBean.FIND_BY_CLUB_AND_ROUND, result.getClub(),
		    result.getRound());
	    results.add(new CheckerResultModel(event, result));
	}
	Collections.sort(results, ResultModel.RESULT_COMPARATOR);
	for (int i = 0; i < results.size(); i++)
	    results.get(i).setRank(i + 1);
	return results;
    }

    public ArrayList<EventModel> provideEvents(String year) {
	return EventUtil.summarizeEvents(eventHome.listByNamedQuery(EventBean.FIND_BY_YEAR, year), true);
    }

    private HashMap<Integer, HeadTopModel> headModelCache = new HashMap<Integer, HeadTopModel>();

    public HeadTopModel getNextHeadModel() {
	Integer next = (int) (new Date().getTime() % 7);
	HeadTopModel model = headModelCache.get(next);
	if (model != null)
	    return model;

	model = getNextHeadModel(next);
	headModelCache.put(next, model);
	return model;
    }

    private HeadTopModel getNextHeadModel(Integer type) {
	HeadTopModel model = new HeadTopModel(type);
	switch (type) {
	case 1:
	    model.fillWithClubs(provideClubsResultsByTypeAndYear(ClubType.FEMALE,
		    RoundResultModel.ROUND_TOTAL_COMPARATOR, true, CURRENT_YEAR));
	    break;
	case 2:
	    model.fillWithClubs(provideClubsResultsByTypeAndYear(ClubType.MIXED,
		    RoundResultModel.ROUND_TOTAL_COMPARATOR, true, CURRENT_YEAR));
	    break;
	case 3:
	    model.fillWithPlayer(providePlayerResultsByGenderAndYear(Gender.MALE,
		    RoundResultModel.ROUND_TOTAL_COMPARATOR, true, CURRENT_YEAR));
	    break;
	case 4:
	    model.fillWithPlayer(providePlayerResultsByGenderAndYear(Gender.FEMALE,
		    RoundResultModel.ROUND_TOTAL_COMPARATOR, true, CURRENT_YEAR));
	    break;
	case 5:
	    model.fillWithChecker(provideCheckerResultsByGenderAndYear(Gender.MALE, CURRENT_YEAR));
	    break;
	case 6:
	    model.fillWithChecker(provideCheckerResultsByGenderAndYear(Gender.FEMALE, CURRENT_YEAR));
	    break;
	default:
	    model.fillWithClubs(provideClubsResultsByTypeAndYear(ClubType.MALE,
		    RoundResultModel.ROUND_TOTAL_COMPARATOR, true, CURRENT_YEAR));
	    break;
	}
	return model;
    }

}
