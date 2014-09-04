package kegelmeisterschaft.model.result;

import java.util.List;
import java.util.Set;

import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

public class PlayerResultModel extends RoundResultModel {

    private PlayerBean player;

    private boolean singleRelevant;

    public PlayerResultModel(PlayerBean player, List<ResultBean> results) {
	this(player, results, null, false);
    }

    public PlayerResultModel(PlayerBean player, List<ResultBean> results,
	    Set<Integer> topResults, boolean singleRelevant) {
	this.player = player;
	this.singleRelevant = singleRelevant;
	for (ResultBean result : results)
	    addResult(result,
		    topResults != null && topResults.contains(result.getId()));
    }

    public PlayerBean getPlayer() {
	return player;
    }

    public void setPlayer(PlayerBean player) {
	this.player = player;
    }

    public boolean isSingleRelevant() {
	return singleRelevant;
    }
}
