package kegelmeisterschaft.service.importer;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

public interface Importer {

    List<ResultBean> getResultsByClub(ClubBean club);

    List<PlayerBean> getPlayerByClub(ClubBean club) throws IOException;

    List<EventBean> getEventsByYear(String year) throws IOException, ParseException;

    List<ClubBean> getClubsByYear(String year) throws IOException;

}
