package kegelmeisterschaft.model.result;

import java.util.Comparator;

import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class ResultModel {
    public static final Comparator<ResultModel> RESULT_COMPARATOR = new Comparator<ResultModel>() {
	@Override
	public int compare(ResultModel o1, ResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getScore(), o2.getScore());
	    builder.append(o1.getNinerCount(), o2.getNinerCount());
	    return builder.toComparison() * -1;
	}
    };

    private int score = 0;
    private int ninerCount = 0;
    private boolean isTop = false;
    private boolean isClubRelevant = true;

    public ResultModel() {
    }

    public ResultModel(int score, int ninerCount) {
	this.score = score;
	this.ninerCount = ninerCount;
    }

    public int getScore() {
	return score;
    }

    public void setScore(int score) {
	this.score = score;
    }

    public int getNinerCount() {
	return ninerCount;
    }

    public void setNinerCount(int ninerCount) {
	this.ninerCount = ninerCount;
    }

    protected void addResult(ResultBean result) {
	score += result.getScore();
	ninerCount += result.getNinerCount();
    }

    public boolean isTop() {
	return isTop;
    }

    public void setTop(boolean isTop) {
	this.isTop = isTop;
    }

    public boolean isClubRelevant() {
	return isClubRelevant;
    }

    public void setClubRelevant(boolean isClubRelevant) {
	this.isClubRelevant = isClubRelevant;
    }
}
