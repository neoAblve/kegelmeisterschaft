package kegelmeisterschaft.model.result;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;

public class RoundResultModel {

    private static final Logger logger = Logger.getLogger(RoundResultModel.class);

    public static final Comparator<RoundResultModel> ROUND_TOTAL_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_TOTAL_NINER_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_1_SCORE_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getFirstRoundResult().getScore(), o2.getFirstRoundResult().getScore());
	    builder.append(o1.getFirstRoundResult().getNinerCount(), o2.getFirstRoundResult().getNinerCount());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_1_NINER_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getFirstRoundResult().getNinerCount(), o2.getFirstRoundResult().getNinerCount());
	    builder.append(o1.getFirstRoundResult().getScore(), o2.getFirstRoundResult().getScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_2_SCORE_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getSecondRoundResult().getScore(), o2.getSecondRoundResult().getScore());
	    builder.append(o1.getSecondRoundResult().getNinerCount(), o2.getSecondRoundResult().getNinerCount());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_2_NINER_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getSecondRoundResult().getNinerCount(), o2.getSecondRoundResult().getNinerCount());
	    builder.append(o1.getSecondRoundResult().getScore(), o2.getSecondRoundResult().getScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_3_SCORE_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getThirdRoundResult().getScore(), o2.getThirdRoundResult().getScore());
	    builder.append(o1.getThirdRoundResult().getNinerCount(), o2.getThirdRoundResult().getNinerCount());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_3_NINER_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getThirdRoundResult().getNinerCount(), o2.getThirdRoundResult().getNinerCount());
	    builder.append(o1.getThirdRoundResult().getScore(), o2.getThirdRoundResult().getScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_4_SCORE_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getFourthRoundResult().getScore(), o2.getFourthRoundResult().getScore());
	    builder.append(o1.getFourthRoundResult().getNinerCount(), o2.getFourthRoundResult().getNinerCount());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };
    public static final Comparator<RoundResultModel> ROUND_4_NINER_COMPARATOR = new Comparator<RoundResultModel>() {
	@Override
	public int compare(RoundResultModel o1, RoundResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getFourthRoundResult().getNinerCount(), o2.getFourthRoundResult().getNinerCount());
	    builder.append(o1.getFourthRoundResult().getScore(), o2.getFourthRoundResult().getScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    return builder.toComparison();
	}
    };

    public static final HashMap<String, Comparator<RoundResultModel>> ORDERS = new HashMap<String, Comparator<RoundResultModel>>();
    static {
	// ORDERS.put("", RoundResultModel.ROUND_TOTAL_COMPARATOR);
	ORDERS.put("total_score", RoundResultModel.ROUND_TOTAL_COMPARATOR);
	ORDERS.put("total_niner", RoundResultModel.ROUND_TOTAL_NINER_COMPARATOR);
	ORDERS.put("r1_score", RoundResultModel.ROUND_1_SCORE_COMPARATOR);
	ORDERS.put("r1_niner", RoundResultModel.ROUND_1_NINER_COMPARATOR);
	ORDERS.put("r2_score", RoundResultModel.ROUND_2_SCORE_COMPARATOR);
	ORDERS.put("r2_niner", RoundResultModel.ROUND_2_NINER_COMPARATOR);
	ORDERS.put("r3_score", RoundResultModel.ROUND_3_SCORE_COMPARATOR);
	ORDERS.put("r3_niner", RoundResultModel.ROUND_3_NINER_COMPARATOR);
	ORDERS.put("r4_score", RoundResultModel.ROUND_4_SCORE_COMPARATOR);
	ORDERS.put("r4_niner", RoundResultModel.ROUND_4_NINER_COMPARATOR);
    }

    protected static final int ROUND_COUNT = 4;

    protected ResultModel[] results = new ResultModel[4];

    protected Set<Integer> topResults = new HashSet<Integer>();

    private int totalScore = 0;
    private int totalNinerCount = 0;

    public RoundResultModel() {
	for (int i = 0; i < ROUND_COUNT; i++)
	    results[i] = new ResultModel();
    }

    protected void addResult(ResultBean result) {
	addResult(result, false);
    }

    protected void addResult(ResultBean result, boolean isTop) {
	if (result.getRound() - 1 > ROUND_COUNT) {
	    logger.warn("invalid round");
	    return;
	}

	if (!isTop)
	    topResults.add(result.getId());
	results[result.getRound() - 1].addResult(result);
	results[result.getRound() - 1].setTop(isTop);
	results[result.getRound() - 1].setClubRelevant(result.isClubRelevant());
	totalScore += result.getScore();
	totalNinerCount += result.getNinerCount();
    }

    public ResultModel getFirstRoundResult() {
	return results[0];
    }

    public ResultModel getSecondRoundResult() {
	return results[1];
    }

    public ResultModel getThirdRoundResult() {
	return results[2];
    }

    public ResultModel getFourthRoundResult() {
	return results[3];
    }

    public int getTotalScore() {
	return totalScore;
    }

    public void setTotalScore(int totalScore) {
	this.totalScore = totalScore;
    }

    public int getTotalNinerCount() {
	return totalNinerCount;
    }

    public void setTotalNinerCount(int totalNinerCount) {
	this.totalNinerCount = totalNinerCount;
    }

    public int getTopRoundScore() {
	int top = 0;
	for (ResultModel result : results) {
	    if (result.getScore() > top)
		top = result.getScore();
	}
	return top;
    }

}
