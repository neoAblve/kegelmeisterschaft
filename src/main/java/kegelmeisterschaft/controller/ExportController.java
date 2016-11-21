package kegelmeisterschaft.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean.ClubType;
import kegelmeisterschaft.entities.PlayerBean.Gender;
import kegelmeisterschaft.model.result.CheckerResultModel;
import kegelmeisterschaft.model.result.ClubDetailResultModel;
import kegelmeisterschaft.model.result.ClubResultModel;
import kegelmeisterschaft.model.result.PlayerResultModel;
import kegelmeisterschaft.service.result.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVWriter;

@Controller
@RequestMapping
public class ExportController {
    @Autowired
    private ResultService resultService;

    @Autowired
    ControllerHelper helper;

    @RequestMapping(method = RequestMethod.GET, value = "/export/{year}")
    public ModelAndView exportResults(@PathVariable("year") String year) throws IOException {
	exportClubs(year, ClubType.MALE);
	exportClubs(year, ClubType.FEMALE);
	exportClubs(year, ClubType.MIXED);
	exportPlayer(year, Gender.MALE);
	exportPlayer(year, Gender.FEMALE);
	exportChecker(year, Gender.MALE);
	exportChecker(year, Gender.FEMALE);

	ModelAndView mv = new ModelAndView("start");
	helper.addCommonModels(mv);
	return mv;
    }

    private void exportChecker(String year, Gender gender) throws IOException {
	CSVWriter writer = new CSVWriter(new PrintWriter(new File("/Users/mathis/Development/checkerResults_" + gender
		+ ".csv")), '\t', CSVWriter.NO_QUOTE_CHARACTER);
	writer.writeNext(makeCheckerHeadStringArray());

	List<CheckerResultModel> results = resultService.provideCheckerResultsByGenderAndYear(gender, year);
	for (int i = 0; i < 20; i++) {
	    writer.writeNext(makeCheckerStringArray(i, results.get(i)));
	}
	writer.close();
    }

    private String[] makeCheckerStringArray(int i, CheckerResultModel checkerResultModel) {
	String[] result = new String[9];

	int k = 0;
	result[k++] = String.valueOf(i + 1);
	result[k++] = checkerResultModel.getChecker().getFirstName() + " "
		+ checkerResultModel.getChecker().getLastName();
	result[k++] = checkerResultModel.getCheckerClub().getName();
	result[k++] = String.valueOf(checkerResultModel.getScore());
	result[k++] = String.valueOf(checkerResultModel.getNinerCount());

	return result;
    }

    private String[] makeCheckerHeadStringArray() {
	String[] head = new String[5];

	int k = 0;
	head[k++] = "Pl.";
	head[k++] = "Name";
	head[k++] = "Club";
	head[k++] = "Gesamt";
	head[k++] = "9er";

	return head;
    }

    private void exportPlayer(String year, Gender gender) throws FileNotFoundException, IOException {
	CSVWriter writer = new CSVWriter(new PrintWriter(new File("/Users/mathis/Development/playerResults_" + gender
		+ ".csv")), '\t', CSVWriter.NO_QUOTE_CHARACTER);
	writer.writeNext(makePlayerHeadStringArray());

	List<PlayerResultModel> results = resultService.providePlayerResultsByGenderAndYear(gender, null, true, year);
	for (int i = 0; i < 20; i++) {
	    writer.writeNext(makePlayerStringArray(i, results.get(i)));
	}
	writer.close();
    }

    private String[] makePlayerHeadStringArray() {
	String[] head = new String[9];

	int k = 0;
	head[k++] = "Pl.";
	head[k++] = "Name";
	head[k++] = "Club";
	head[k++] = "1. DG";
	head[k++] = "2. DG";
	head[k++] = "3. DG";
	head[k++] = "4. DG";
	head[k++] = "Gesamt";
	head[k++] = "9er";

	return head;
    }

    private String[] makePlayerStringArray(int i, PlayerResultModel playerResultModel) {
	String[] result = new String[9];

	int k = 0;
	result[k++] = String.valueOf(i + 1);
	result[k++] = playerResultModel.getPlayer().getFirstName() + " " + playerResultModel.getPlayer().getLastName();
	result[k++] = playerResultModel.getPlayer().getSingleLeagueClub().getName();
	result[k++] = String.valueOf(playerResultModel.getFirstRoundResult().getScore());
	result[k++] = String.valueOf(playerResultModel.getSecondRoundResult().getScore());
	result[k++] = String.valueOf(playerResultModel.getThirdRoundResult().getScore());
	result[k++] = String.valueOf(playerResultModel.getFourthRoundResult().getScore());
	result[k++] = String.valueOf(playerResultModel.getTotalScore());
	result[k++] = String.valueOf(playerResultModel.getTotalNinerCount());

	return result;
    }

    private void exportClubs(String year, ClubType type) throws FileNotFoundException, IOException {
	CSVWriter writer = new CSVWriter(new PrintWriter(new File("/Users/mathis/Development/clubResults_" + type
		+ ".csv")), '\t', CSVWriter.NO_QUOTE_CHARACTER);
	writer.writeNext(makeClubHeadStringArray());

	List<ClubResultModel> results = resultService.provideClubsResultsByTypeAndYear(type, null, true, year);
	for (int i = 0; i < results.size(); i++) {
	    ClubResultModel clubResult = results.get(i);
	    writer.writeNext(makeClubStringArray(i, clubResult));

	    exportClubDetails(clubResult);
	}
	writer.close();
    }

    private void exportClubDetails(ClubResultModel clubResult) throws IOException {
	CSVWriter writer = new CSVWriter(new PrintWriter(new File("/Users/mathis/Development/clubDetail_"
		+ clubResult.getClub().getName() + ".csv")), '\t', CSVWriter.NO_QUOTE_CHARACTER,
		CSVWriter.DEFAULT_ESCAPE_CHARACTER);
	writer.writeNext(makeClubDetailHeadStringArray(clubResult));

	ClubDetailResultModel clubDetails = resultService.provideClubDetails(clubResult.getClub().getId());

	List<PlayerResultModel> playerResults = clubDetails.getPlayerResults();
	for (PlayerResultModel playerResult : playerResults) {
	    writer.writeNext(makePlayerDetailHeadStringArray(playerResult));
	}
	writer.writeNext(makeClubDetailTotal(clubResult));
	writer.close();
    }

    private String[] makeClubDetailTotal(ClubResultModel clubResult) {
	String[] result = new String[11];

	int k = 0;
	result[k++] = "Clubergebnis";
	result[k++] = String.valueOf(clubResult.getFirstRoundResult().getScore());
	result[k++] = String.valueOf(clubResult.getFirstRoundResult().getNinerCount());
	result[k++] = String.valueOf(clubResult.getSecondRoundResult().getScore());
	result[k++] = String.valueOf(clubResult.getSecondRoundResult().getNinerCount());
	result[k++] = String.valueOf(clubResult.getThirdRoundResult().getScore());
	result[k++] = String.valueOf(clubResult.getThirdRoundResult().getNinerCount());
	result[k++] = String.valueOf(clubResult.getFourthRoundResult().getScore());
	result[k++] = String.valueOf(clubResult.getFourthRoundResult().getNinerCount());
	result[k++] = String.valueOf(clubResult.getTotalScore());
	result[k++] = String.valueOf(clubResult.getTotalNinerCount());

	return result;
    }

    private String[] makePlayerDetailHeadStringArray(PlayerResultModel playerResult) {
	String[] result = new String[11];

	int k = 0;
	result[k++] = playerResult.getPlayer().getFirstName() + " " + playerResult.getPlayer().getLastName();
	result[k++] = String.valueOf(playerResult.getFirstRoundResult().getScore());
	result[k++] = String.valueOf(playerResult.getFirstRoundResult().getNinerCount());
	result[k++] = String.valueOf(playerResult.getSecondRoundResult().getScore());
	result[k++] = String.valueOf(playerResult.getSecondRoundResult().getNinerCount());
	result[k++] = String.valueOf(playerResult.getThirdRoundResult().getScore());
	result[k++] = String.valueOf(playerResult.getThirdRoundResult().getNinerCount());
	result[k++] = String.valueOf(playerResult.getFourthRoundResult().getScore());
	result[k++] = String.valueOf(playerResult.getFourthRoundResult().getNinerCount());
	result[k++] = String.valueOf(playerResult.getTotalScore());
	result[k++] = String.valueOf(playerResult.getTotalNinerCount());

	return result;
    }

    private String[] makeClubDetailHeadStringArray(ClubResultModel clubResult) {
	String[] head = new String[11];

	int k = 0;
	head[k++] = clubResult.getClub().getName();
	head[k++] = "Durchgang 1";
	head[k++] = "9er";
	head[k++] = "Durchgang 2";
	head[k++] = "9er";
	head[k++] = "Durchgang 3";
	head[k++] = "9er";
	head[k++] = "Durchgang 4";
	head[k++] = "9er";
	head[k++] = "Holz";
	head[k++] = "9er";

	return head;
    }

    private String[] makeClubHeadStringArray() {
	String[] head = new String[8];

	int k = 0;
	head[k++] = "Pl.";
	head[k++] = "Club";
	head[k++] = "1. DG";
	head[k++] = "2. DG";
	head[k++] = "3. DG";
	head[k++] = "4. DG";
	head[k++] = "Gesamt";
	head[k++] = "9er";

	return head;
    }

    private String[] makeClubStringArray(int i, ClubResultModel clubResultModel) {
	String[] clubResult = new String[8];

	int k = 0;
	clubResult[k++] = String.valueOf(i + 1);
	clubResult[k++] = clubResultModel.getClub().getName();
	clubResult[k++] = String.valueOf(clubResultModel.getFirstRoundResult().getScore());
	clubResult[k++] = String.valueOf(clubResultModel.getSecondRoundResult().getScore());
	clubResult[k++] = String.valueOf(clubResultModel.getThirdRoundResult().getScore());
	clubResult[k++] = String.valueOf(clubResultModel.getFourthRoundResult().getScore());
	clubResult[k++] = String.valueOf(clubResultModel.getTotalScore());
	clubResult[k++] = String.valueOf(clubResultModel.getTotalNinerCount());

	return clubResult;
    }

}
