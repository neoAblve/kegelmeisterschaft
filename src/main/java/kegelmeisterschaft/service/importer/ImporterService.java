package kegelmeisterschaft.service.importer;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import kegelmeisterschaft.dao.ClubHome;
import kegelmeisterschaft.dao.EventHome;
import kegelmeisterschaft.dao.PlayerHome;
import kegelmeisterschaft.dao.ResultHome;
import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ImporterService {

    @SuppressWarnings("serial")
    private static final Map<String, Importer> YEARS = new TreeMap<String, Importer>() {
	{
	    put("2014", new CSVImporter());
	    put("2015", new XLSXImporter());
	}
    };

    @Autowired
    private ClubHome clubHome;
    @Autowired
    private PlayerHome playerHome;
    @Autowired
    private EventHome eventHome;
    @Autowired
    private ResultHome resultHome;

    public void performImport() throws IOException, ParseException {
	System.out.println("Clear existing Data ...");
	resultHome.removeAll();
	eventHome.removeAll();
	clubHome.removeAll();
	playerHome.removeAll();

	resultHome.flush();
	eventHome.flush();
	clubHome.flush();
	playerHome.flush();
	System.out.println("Clearing done.");

	// reset BaseSequences
	resultHome.resetId();
	eventHome.resetId();
	clubHome.resetId();
	playerHome.resetId();

	resultHome.flush();
	eventHome.flush();
	clubHome.flush();
	playerHome.flush();

	for (String year : YEARS.keySet()) {
	    System.out.println(year);
	    importClubs(year);
	    importEvents(year);
	    importPlayer(year);
	    importResults(year);
	}
	System.out.println("Import done.");
    }

    private void importResults(String year) {
	System.out.println("Importing Results");
	List<ClubBean> clubs = clubHome.listByNamedQuery(ClubBean.FIND_BY_YEAR, year);
	for (ClubBean club : clubs) {
	    System.out.println("\tworking on Club: " + club.getName());
	    List<ResultBean> results = YEARS.get(year).getResultsByClub(club);
	    for (ResultBean result : results) {
		if (!StringUtils.equals("true", result.getReleased()))
		    continue;
		// System.out.println("\t\tworking on Result: " + result);
		boolean playerDependend = false;
		if (result.getSpecialType().equals("onlySingle")) {
		    result.setClubRelevant(false);
		    result.setSingleRelevant(true);
		    result.setChecker(false);
		} else if (result.getSpecialType().equals("checker")) {
		    result.setClubRelevant(false);
		    result.setSingleRelevant(false);
		    result.setChecker(true);
		} else {
		    result.setClubRelevant(true);
		    result.setChecker(false);
		    playerDependend = true;
		}

		// System.out.println(result.getFirstName() + " - " +
		// result.getLastName());
		result.setClub(club);
		PlayerBean player = playerHome.findExactlyOneByNamedQuery(PlayerBean.FIND_BY_NAME_AND_YEAR,
			result.getFirstName(), result.getLastName(), year);
		result.setPlayer(player);
		if (playerDependend) {
		    ClubBean singleClub = player.getSingleLeagueClub();
		    if (singleClub != null)
			result.setSingleRelevant(singleClub.getName().equals(club.getName()));
		}
		// System.out.println("\t\tCreate Result: " + result);
		result.setYear(year);
		resultHome.createOrUpdate(result);
	    }
	}
	System.out.println("results imported");
    }

    private void importEvents(String year) throws IOException, ParseException {
	HashMap<String, ClubBean> clubMap = new HashMap<String, ClubBean>();
	for (ClubBean club : clubHome.listByNamedQuery(ClubBean.FIND_BY_YEAR, year))
	    clubMap.put(club.getName(), club);

	List<EventBean> eventsToImport = YEARS.get(year).getEventsByYear(year);
	System.out.println("Importing " + eventsToImport.size() + " Events");
	for (EventBean event : eventsToImport) {
	    System.out.println("\tevent: " + event);
	    event.setClub(clubMap.get(event.getClubName()));
	    event.setChecker1Club(clubMap.get(event.getChecker1ClubImport()));
	    event.setChecker2Club(clubMap.get(event.getChecker2ClubImport()));
	    event.setYear(year);
	    eventHome.createOrUpdate(event);
	}
	System.out.println("events imported");
    }

    private static class PlayerKey {
	private String first;
	private String last;
	private String year;

	public PlayerKey(String first, String last, String year) {
	    this.first = first;
	    this.last = last;
	    this.year = year;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((first == null) ? 0 : first.hashCode());
	    result = prime * result + ((last == null) ? 0 : last.hashCode());
	    result = prime * result + ((year == null) ? 0 : year.hashCode());
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
	    PlayerKey other = (PlayerKey) obj;
	    if (first == null) {
		if (other.first != null)
		    return false;
	    } else if (!first.equals(other.first))
		return false;
	    if (last == null) {
		if (other.last != null)
		    return false;
	    } else if (!last.equals(other.last))
		return false;
	    if (year == null) {
		if (other.year != null)
		    return false;
	    } else if (!year.equals(other.year))
		return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "Player [" + first + ", " + last + ", " + year + "]";
	}

    }

    private void importPlayer(String year) throws IOException {
	HashMap<PlayerKey, PlayerBean> playerMap = new HashMap<PlayerKey, PlayerBean>();
	HashMap<PlayerKey, HashSet<ClubBean>> playerClubMap = new HashMap<PlayerKey, HashSet<ClubBean>>();

	System.out.println("Importing Player of clubs");
	for (ClubBean club : clubHome.listByNamedQuery(ClubBean.FIND_BY_YEAR, year)) {
	    System.out.println("\tworking on Club: " + club.getName());
	    List<PlayerBean> players = YEARS.get(year).getPlayerByClub(club);
	    for (PlayerBean player : players) {
		PlayerKey playerKey = new PlayerKey(player.getFirstName(), player.getLastName(), year);
		System.out.println("\t\tworking on " + playerKey);

		HashSet<ClubBean> playerClubs = playerClubMap.get(playerKey);
		if (playerClubs == null) {
		    playerClubs = new HashSet<ClubBean>();
		    playerClubMap.put(playerKey, playerClubs);
		}
		playerClubs.add(club);

		PlayerBean existing = playerMap.get(playerKey);
		if (existing == null) {
		    existing = player;
		    playerMap.put(playerKey, existing);
		}

		if (player.isSingleRelevant()) {
		    System.out.println("\t\t\tset single league club");
		    existing.setSingleLeagueClub(club);
		}
	    }
	}

	System.out.println("persist player");
	for (Entry<PlayerKey, PlayerBean> entry : playerMap.entrySet()) {
	    PlayerKey playerKey = entry.getKey();
	    System.out.println("\t" + playerKey);
	    PlayerBean player = entry.getValue();
	    player.setYear(year);
	    player = playerHome.createOrUpdate(player);

	    HashSet<ClubBean> clubs = playerClubMap.get(playerKey);
	    for (ClubBean club : clubs) {
		club = clubHome.findById(club.getId());
		club.addPlayer(player);
		player.getClubs().add(club);
		club = clubHome.createOrUpdate(club);
	    }
	}

	System.out.println("player imported");
    }

    private void importClubs(String year) throws IOException {
	System.out.println("Importing Clubs ...");
	List<ClubBean> clubsByYear = YEARS.get(year).getClubsByYear(year);
	for (ClubBean club : clubsByYear) {
	    System.out.println("\tClub: " + club.getName());
	    club.setYear(year);
	    clubHome.createOrUpdate(club);
	}
	System.out.println(clubsByYear.size() + " clubs imported");
    }
}
