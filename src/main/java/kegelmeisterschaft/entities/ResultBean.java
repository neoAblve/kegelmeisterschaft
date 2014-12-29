package kegelmeisterschaft.entities;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "ResultBean")
@NamedQueries({
	@NamedQuery(name = ResultBean.FIND_BY_CLUB, query = "SELECT r FROM ResultBean r WHERE r.club = ?"),
	@NamedQuery(name = ResultBean.FIND_CHECKER_RESULTS_BY_GENDER, query = "SELECT r FROM ResultBean r INNER JOIN r.player p WHERE r.checker = true AND p.gender = ?"),
	@NamedQuery(name = ResultBean.FIND_BY_PLAYER, query = "SELECT r FROM ResultBean r INNER JOIN r.player WHERE r.checker = false AND r.singleRelevant = true AND r.player = ? AND r.player.singleLeagueClub.id = r.club.id"),
	@NamedQuery(name = ResultBean.FIND_BY_PLAYER_ALL, query = "SELECT r FROM ResultBean r INNER JOIN r.player WHERE r.player = ? ORDER BY r.club, r.round"),
	@NamedQuery(name = ResultBean.FIND_BY_PLAYER_AND_CLUB, query = "SELECT r FROM ResultBean r WHERE r.player = ? AND r.club = ? AND r.checker = false "), })
@Table(name = "result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultBean {

    public static final String FIND_BY_CLUB = "findResultsByClub";
    public static final String FIND_BY_PLAYER = "findByPlayer";
    public static final String FIND_BY_PLAYER_ALL = "findByPlayerAll";
    public static final String FIND_BY_PLAYER_AND_CLUB = "findByPlayerAndClub";
    public static final String FIND_CHECKER_RESULTS_BY_GENDER = "findCheckerResultsByGender";

    public static final Comparator<ResultBean> SCORE_COMPARATOR = new Comparator<ResultBean>() {
	@Override
	public int compare(ResultBean r1, ResultBean r2) {
	    CompareToBuilder builder = new CompareToBuilder();
	    builder.append(r1.getScore(), r2.getScore());
	    builder.append(r1.getNinerCount(), r2.getNinerCount());
	    builder.append(r1.getMaxV(), r2.getMaxV());
	    builder.append(r1.getMaxR(), r2.getMaxR());
	    return builder.toComparison() * -1;
	}
    };

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "fk_club", updatable = true, nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ClubBean club;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "fk_player", updatable = true, nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private PlayerBean player;

    @Column(name = "year")
    private String year;

    private transient String firstName;
    private transient String lastName;

    private int round;

    private boolean singleRelevant;

    private boolean clubRelevant;

    private boolean checker;

    private transient String specialType;

    private int v1;
    private int r1;
    private int v2;
    private int r2;
    private int ninerCount;

    private transient String released;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public ClubBean getClub() {
	return club;
    }

    public void setClub(ClubBean club) {
	this.club = club;
    }

    public PlayerBean getPlayer() {
	return player;
    }

    public void setPlayer(PlayerBean player) {
	this.player = player;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public boolean isSingleRelevant() {
	return singleRelevant;
    }

    public void setSingleRelevant(boolean singleRelevant) {
	this.singleRelevant = singleRelevant;
    }

    public boolean isClubRelevant() {
	return clubRelevant;
    }

    public void setClubRelevant(boolean clubRelevant) {
	this.clubRelevant = clubRelevant;
    }

    public boolean isChecker() {
	return checker;
    }

    public void setChecker(boolean checker) {
	this.checker = checker;
    }

    public String getSpecialType() {
	return specialType;
    }

    public void setSpecialType(String specialType) {
	this.specialType = specialType;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public int getV1() {
	return v1;
    }

    public void setV1(int v1) {
	this.v1 = v1;
    }

    public int getR1() {
	return r1;
    }

    public void setR1(int r1) {
	this.r1 = r1;
    }

    public int getV2() {
	return v2;
    }

    public void setV2(int v2) {
	this.v2 = v2;
    }

    public int getR2() {
	return r2;
    }

    public void setR2(int r2) {
	this.r2 = r2;
    }

    public int getNinerCount() {
	return ninerCount;
    }

    public void setNinerCount(int ninerCount) {
	this.ninerCount = ninerCount;
    }

    @Override
    public String toString() {
	return "ResultBean [id=" + id + ", club=" + club + ", player=" + player + ", round=" + round
		+ ", singleRelevant=" + singleRelevant + ", clubRelevant=" + clubRelevant + ", checker=" + checker
		+ ", v1=" + v1 + ", r1=" + r1 + ", v2=" + v2 + ", r2=" + r2 + ", ninerCount=" + ninerCount + "]";
    }

    public int getScore() {
	return v1 + v2 + r1 + r2;
    }

    public int getMaxV() {
	if (v1 > v2)
	    return v1;
	else
	    return v2;
    }

    public int getMaxR() {
	if (r1 > r2)
	    return r1;
	else
	    return r2;
    }

    public String getReleased() {
	return released;
    }

    public void setReleased(String released) {
	this.released = released;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

}
