package kegelmeisterschaft.model.common;

public class HeadTopRow {
    private String name;
    private int niner;
    private int score;

    public HeadTopRow(String name, int niner, int score) {
	this.name = name;
	this.niner = niner;
	this.score = score;
    }

    public String getName() {
	return name;
    }

    public int getNiner() {
	return niner;
    }

    public int getScore() {
	return score;
    }

}
