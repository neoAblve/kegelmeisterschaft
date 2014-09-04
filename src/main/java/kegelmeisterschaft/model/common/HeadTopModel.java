package kegelmeisterschaft.model.common;

import java.util.HashMap;
import java.util.List;

import kegelmeisterschaft.model.result.CheckerResultModel;
import kegelmeisterschaft.model.result.ClubResultModel;
import kegelmeisterschaft.model.result.PlayerResultModel;

public class HeadTopModel {

    private static final HashMap<Integer, String> TITLE_MAP = new HashMap<Integer, String>() {
	private static final long serialVersionUID = 1L;
	{
	    put(0, "Clubwertung - Herren");
	    put(1, "Clubwertung - Damen");
	    put(2, "Clubwertung - Gemischte Clubs");
	    put(3, "Einzelwertung - Herren");
	    put(4, "Einzelwertung - Damen");
	    put(5, "Aufschreiberwertung - Herren");
	    put(6, "Aufschreiberwertung - Damen");

	}
    };

    private String title;

    private HeadTopRow[] rows = new HeadTopRow[3];

    public HeadTopModel(Integer type) {
	title = TITLE_MAP.get(type);
	if (title == null)
	    title = TITLE_MAP.get(0);
    }

    public void fillWithClubs(List<ClubResultModel> results) {
	for (int i = 0; i < rows.length; i++) {
	    ClubResultModel clubResult = results.get(i);
	    rows[i] = new HeadTopRow(clubResult.getClub().getName(),
		    clubResult.getTotalNinerCount(), clubResult.getTotalScore());
	}
    }

    public void fillWithPlayer(List<PlayerResultModel> results) {
	for (int i = 0; i < rows.length; i++) {
	    PlayerResultModel playerResult = results.get(i);
	    String name = playerResult.getPlayer().getFirstName() + " "
		    + playerResult.getPlayer().getLastName();
	    rows[i] = new HeadTopRow(name, playerResult.getTotalNinerCount(),
		    playerResult.getTotalScore());
	}
    }

    public void fillWithChecker(List<CheckerResultModel> results) {
	for (int i = 0; i < rows.length; i++) {
	    CheckerResultModel checkerResult = results.get(i);
	    String name = checkerResult.getChecker().getFirstName() + " "
		    + checkerResult.getChecker().getLastName();
	    rows[i] = new HeadTopRow(name, checkerResult.getNinerCount(),
		    checkerResult.getScore());
	}
    }

    public String getTitle() {
	return title;
    }

    public HeadTopRow[] getRows() {
	return rows;
    }
}
