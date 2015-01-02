package kegelmeisterschaft.model.result;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class PlayerResultModel extends RoundResultModel {

    public static final Comparator<PlayerResultModel> PLAYER_ROUND_TOTAL_COMPARATOR = new Comparator<PlayerResultModel>() {
	@Override
	public int compare(PlayerResultModel o1, PlayerResultModel o2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(o1.getTotalScore(), o2.getTotalScore());
	    builder.append(o1.getTopRoundScore(), o2.getTopRoundScore());
	    builder.append(o1.getTotalNinerCount(), o2.getTotalNinerCount());
	    builder.append(o2.getPlayer().getLastName(), o1.getPlayer().getLastName());
	    builder.append(o2.getPlayer().getFirstName(), o1.getPlayer().getFirstName());
	    return builder.toComparison();
	}
    };

    private PlayerBean player;

    private boolean singleRelevant;

    public PlayerResultModel(PlayerBean player, List<ResultBean> results) {
	this(player, results, null, false);
    }

    public PlayerResultModel(PlayerBean player, List<ResultBean> results, Set<Integer> topResults,
	    boolean singleRelevant) {
	this.player = player;
	this.singleRelevant = singleRelevant;
	for (ResultBean result : results)
	    addResult(result, topResults != null && topResults.contains(result.getId()));
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
