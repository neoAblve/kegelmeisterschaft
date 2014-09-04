package kegelmeisterschaft.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "EventBean")
@NamedQueries({
	@NamedQuery(name = EventBean.FIND_BY_CLUB, query = "SELECT e FROM EventBean e WHERE club = ?"),
	@NamedQuery(name = EventBean.FIND_BY_CHECKER_CLUB, query = "SELECT e FROM EventBean e WHERE checker1Club = ? or checker2Club = ?"),
	@NamedQuery(name = EventBean.FIND_BY_CLUB_AND_ROUND, query = "SELECT e FROM EventBean e WHERE club = ? AND round = ?"), })
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventBean {

    public static final String FIND_BY_CLUB = "findEventsByClub";
    public static final String FIND_BY_CHECKER_CLUB = "findEventsByCheckerClub";
    public static final String FIND_BY_CLUB_AND_ROUND = "findEventsByClubAndRound";

    private static final SimpleDateFormat IMPORT_FORMAT = new SimpleDateFormat(
	    "dd.MM.yyyy, HH:mm 'Uhr'");

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "fk_club", updatable = true, nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ClubBean club;

    private transient String clubName;

    private int round;

    private Date date;

    private String location;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "fk_checker1Club", updatable = true, nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ClubBean checker1Club;
    private transient String checker1ClubImport;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "fk_checker2Club", updatable = true, nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ClubBean checker2Club;
    private transient String checker2ClubImport;

    public ClubBean getChecker1Club() {
	return checker1Club;
    }

    public void setChecker1Club(ClubBean checker1Club) {
	this.checker1Club = checker1Club;
    }

    public ClubBean getChecker2Club() {
	return checker2Club;
    }

    public void setChecker2Club(ClubBean checker2Club) {
	this.checker2Club = checker2Club;
    }

    public String getChecker1ClubImport() {
	return checker1ClubImport;
    }

    public void setChecker1ClubImport(String checker1ClubImport) {
	this.checker1ClubImport = checker1ClubImport;
    }

    public String getChecker2ClubImport() {
	return checker2ClubImport;
    }

    public void setChecker2ClubImport(String checker2ClubImport) {
	this.checker2ClubImport = checker2ClubImport;
    }

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

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public void setDateImport(String date) throws ParseException {
	this.date = IMPORT_FORMAT.parse(date);
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    @Override
    public String toString() {
	return "EventBean [id=" + id + ", club=" + club + ", round=" + round
		+ ", date=" + date + ", location=" + location + "]";
    }

    public String getClubName() {
	return clubName;
    }

    public void setClubName(String clubName) {
	this.clubName = clubName;
    }

}
