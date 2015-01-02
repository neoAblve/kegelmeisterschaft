package kegelmeisterschaft.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.ClubBean.ClubType;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class ClubResultModel extends RoundResultModel {

    public static final Comparator<ClubResultModel> CLUB_ROUND_TOTAL_COMPARATOR = new Comparator<ClubResultModel>() {
	@Override
	public int compare(ClubResultModel o1, ClubResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o2.getClub().getName(), o1.getClub().getName());
	    return builder.toComparison();
	}
    };

    private ClubBean club;

    public ClubResultModel(ClubBean club, List<ResultBean> clubResults) {
	this.club = club;

	HashMap<Integer, ArrayList<ResultBean>> roundResults = new HashMap<Integer, ArrayList<ResultBean>>();
	for (ResultBean result : clubResults) {
	    if (!result.isClubRelevant())
		continue;

	    ArrayList<ResultBean> round = roundResults.get(result.getRound());
	    if (round == null) {
		round = new ArrayList<ResultBean>();
		roundResults.put(result.getRound(), round);
	    }
	    round.add(result);
	}

	for (int i = 1; i <= ROUND_COUNT; i++) {
	    ArrayList<ResultBean> results = roundResults.get(i);
	    if (results == null)
		continue;

	    Collections.sort(results, ResultBean.SCORE_COMPARATOR);

	    if (ClubType.MIXED == club.getType()) {
		ArrayList<ResultBean> male = new ArrayList<ResultBean>();
		ArrayList<ResultBean> female = new ArrayList<ResultBean>();
		for (ResultBean result : results) {
		    if (PlayerBean.Gender.MALE.toString().equals(result.getPlayer().getGender()))
			male.add(result);
		    else
			female.add(result);
		}

		for (ResultBean result : male.subList(0, Math.min(results.size(), 2)))
		    addResult(result);

		for (ResultBean result : female.subList(0, Math.min(results.size(), 2)))
		    addResult(result);

		ResultBean thirdMale = male.get(2);
		ResultBean thirdFemale = female.get(2);
		if (ResultBean.SCORE_COMPARATOR.compare(thirdMale, thirdFemale) <= 0)
		    addResult(thirdMale);
		else
		    addResult(thirdFemale);

	    } else {
		for (ResultBean result : results.subList(0, Math.min(results.size(), 5)))
		    addResult(result);
	    }
	}
    }

    public ClubBean getClub() {
	return club;
    }

    public void setClub(ClubBean club) {
	this.club = club;
    }

}
