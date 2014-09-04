package kegelmeisterschaft.service.importer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Component
@Transactional
public class ImporterService {

    @Autowired
    private ClubHome clubHome;
    @Autowired
    private PlayerHome playerHome;
    @Autowired
    private EventHome eventHome;
    @Autowired
    private ResultHome resultHome;

    public void performImport() throws IOException {
	System.out.println("Clear existing Data ...");
	resultHome.removeAll();
	eventHome.removeAll();
	clubHome.removeAll();
	playerHome.removeAll();
	System.out.println("Clearing done.");

	importClubs();
	importEvents();
	importPlayer();
	importResults();
	System.out.println("Import done.");
    }

    private void importResults() {
	System.out.println("Importing Results");
	List<ClubBean> clubs = clubHome.listAll();
	for (ClubBean club : clubs) {
	    System.out.println("\tworking on Club: " + club.getName());
	    List<ResultBean> results = getResultsToImportByClub(club);
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

		result.setClub(club);
		PlayerBean player = playerHome.findExactlyOneByNamedQuery(
			PlayerBean.FIND_BY_NAME, result.getFirstName(),
			result.getLastName());
		result.setPlayer(player);
		if (playerDependend) {
		    ClubBean singleClub = player.getSingleLeagueClub();
		    if (singleClub != null)
			result.setSingleRelevant(singleClub.getName().equals(
				club.getName()));
		}
		// System.out.println("\t\tCreate Result: " + result);
		resultHome.createOrUpdate(result);
	    }
	}
	System.out.println("results imported");
    }

    private void importEvents() throws IOException {
	HashMap<String, ClubBean> clubMap = new HashMap<String, ClubBean>();
	for (ClubBean club : clubHome.listAll())
	    clubMap.put(club.getName(), club);

	List<EventBean> eventsToImport = getEventsToImport();
	System.out.println("Importing " + eventsToImport.size() + " Events");
	for (EventBean event : eventsToImport) {
	    System.out.println("\tevent: " + event);
	    event.setClub(clubMap.get(event.getClubName()));
	    event.setChecker1Club(clubMap.get(event.getChecker1ClubImport()));
	    event.setChecker2Club(clubMap.get(event.getChecker2ClubImport()));
	    eventHome.createOrUpdate(event);
	}
	System.out.println("events imported");
    }

    private static class PlayerKey {
	private String first;
	private String last;

	public PlayerKey(String first, String last) {
	    this.first = first;
	    this.last = last;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((first == null) ? 0 : first.hashCode());
	    result = prime * result + ((last == null) ? 0 : last.hashCode());
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
	    return true;
	}

	@Override
	public String toString() {
	    return "Player [" + first + ", " + last + "]";
	}

    }

    private void importPlayer() throws IOException {
	HashMap<PlayerKey, PlayerBean> playerMap = new HashMap<PlayerKey, PlayerBean>();
	HashMap<PlayerKey, HashSet<ClubBean>> playerClubMap = new HashMap<PlayerKey, HashSet<ClubBean>>();

	System.out.println("Importing Player of clubs");
	for (ClubBean club : clubHome.listAll()) {
	    System.out.println("\tworking on Club: " + club.getName());
	    List<PlayerBean> players = getPlayerToImportByClub(club);
	    for (PlayerBean player : players) {
		PlayerKey playerKey = new PlayerKey(player.getFirstName(),
			player.getLastName());
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

    private void importClubs() throws IOException {
	System.out.println("Importing Clubs ...");
	List<ClubBean> clubs = getClubsToImport();
	for (ClubBean club : clubs) {
	    System.out.println("\tClub: " + club.getName());
	    clubHome.createOrUpdate(club);
	}
	System.out.println("clubs imported");
    }

    private List<ClubBean> getClubsToImport() throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("name", "name");
	columnMapping.put("type", "typefromString");
	columnMapping.put("foundingYear", "foundingYear");
	columnMapping.put("memberCount", "memberCount");
	columnMapping.put("location", "location");
	// columnMapping.put("channel", "channel");
	// columnMapping.put("chairman", "chairman");
	// columnMapping.put("address", "address");
	// columnMapping.put("phone", "phone");
	// columnMapping.put("mail", "mail");

	HeaderColumnNameTranslateMappingStrategy<ClubBean> strategy = new HeaderColumnNameTranslateMappingStrategy<ClubBean>();
	strategy.setType(ClubBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(
		ImporterService.class).getResource("clubs.txt");
	CSVReader reader = new CSVReader(new InputStreamReader(
		resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<ClubBean> bean = new CsvToBean<ClubBean>();
	return bean.parse(strategy, reader);
    }

    private List<EventBean> getEventsToImport() throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("date", "dateImport");
	columnMapping.put("clubName", "clubName");
	columnMapping.put("round", "round");
	columnMapping.put("location", "location");
	columnMapping.put("checker1Club", "checker1ClubImport");
	columnMapping.put("checker2Club", "checker2ClubImport");

	HeaderColumnNameTranslateMappingStrategy<EventBean> strategy = new HeaderColumnNameTranslateMappingStrategy<EventBean>();
	strategy.setType(EventBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(
		ImporterService.class).getResource("events.txt");
	CSVReader reader = new CSVReader(new InputStreamReader(
		resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<EventBean> bean = new CsvToBean<EventBean>();
	return bean.parse(strategy, reader);
    }

    private List<PlayerBean> getPlayerToImportByClub(ClubBean club)
	    throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("firstName", "firstName");
	columnMapping.put("lastName", "lastName");
	columnMapping.put("gender", "gender");
	columnMapping.put("singleRelevant", "singleRelevant");

	HeaderColumnNameTranslateMappingStrategy<PlayerBean> strategy = new HeaderColumnNameTranslateMappingStrategy<PlayerBean>();
	strategy.setType(PlayerBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(
		ImporterService.class).getResource("player/" + club.getName()
		+ ".txt");
	CSVReader reader = new CSVReader(new InputStreamReader(
		resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<PlayerBean> bean = new CsvToBean<PlayerBean>();
	return bean.parse(strategy, reader);
    }

    private List<ResultBean> getResultsToImportByClub(ClubBean club) {
	try {
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    columnMapping.put("firstName", "firstName");
	    columnMapping.put("lastName", "lastName");
	    columnMapping.put("round", "round");
	    columnMapping.put("specialType", "specialType");
	    columnMapping.put("v1", "v1");
	    columnMapping.put("r1", "r1");
	    columnMapping.put("v2", "v2");
	    columnMapping.put("r2", "r2");
	    columnMapping.put("9er", "ninerCount");
	    columnMapping.put("released", "released");

	    HeaderColumnNameTranslateMappingStrategy<ResultBean> strategy = new HeaderColumnNameTranslateMappingStrategy<ResultBean>();
	    strategy.setType(ResultBean.class);
	    strategy.setColumnMapping(columnMapping);

	    Resource resource = new ClassRelativeResourceLoader(
		    ImporterService.class).getResource("results/"
		    + club.getName() + ".txt");
	    CSVReader reader = new CSVReader(new InputStreamReader(
		    resource.getInputStream(), "UTF-16"), '\t');
	    CsvToBean<ResultBean> bean = new CsvToBean<ResultBean>();
	    return bean.parse(strategy, reader);
	} catch (Exception e) {
	    System.out.println("Keine Results für " + club.getName()
		    + "gefunden");
	    e.printStackTrace();
	    return new ArrayList<ResultBean>();
	}
    }
}
