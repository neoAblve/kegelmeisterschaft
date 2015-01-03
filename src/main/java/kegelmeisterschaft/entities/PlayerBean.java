package kegelmeisterschaft.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "PlayerBean")
@NamedQueries({
	@NamedQuery(name = PlayerBean.FIND_BY_GENDER, query = "SELECT p FROM PlayerBean p WHERE p.gender = ?"),
	@NamedQuery(name = PlayerBean.FIND_BY_GENDER_AND_YEAR, query = "SELECT p FROM PlayerBean p WHERE p.gender = ? AND p.year = ?"),
	@NamedQuery(name = PlayerBean.FIND_BY_CLUB, query = "SELECT p FROM PlayerBean p INNER JOIN p.clubs c WHERE c.name = ?"),
	@NamedQuery(name = PlayerBean.FIND_BY_CLUB_AND_YEAR, query = "SELECT p FROM PlayerBean p INNER JOIN p.clubs c WHERE c.name = ? and p.year = ?"),
	@NamedQuery(name = PlayerBean.FIND_BY_NAME, query = "SELECT p FROM PlayerBean p WHERE p.firstName = ? AND p.lastName = ?"),
	@NamedQuery(name = PlayerBean.FIND_BY_NAME_AND_YEAR, query = "SELECT p FROM PlayerBean p WHERE p.firstName = ? AND p.lastName = ? and p.year = ?"), })
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlayerBean {

    public static final String FIND_BY_GENDER = "findPlayerByGender";
    public static final String FIND_BY_GENDER_AND_YEAR = "findPlayerByGenderAndYear";
    public static final String FIND_BY_NAME = "findPlayerByName";
    public static final String FIND_BY_NAME_AND_YEAR = "findPlayerByNameAndYear";
    public static final String FIND_BY_CLUB = "findPlayerByClub";
    public static final String FIND_BY_CLUB_AND_YEAR = "findPlayerByClubAndYear";

    public enum Gender {
	MALE, FEMALE;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "year")
    private String year;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_singleLeagueClub")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ClubBean singleLeagueClub;

    private transient boolean singleRelevant;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "player", targetEntity = ClubBean.class, cascade = {
	    CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ClubBean> clubs = new HashSet<ClubBean>();

    public String getFirstName() {
	return firstName;
    }

    public String getGender() {
	return gender;
    }

    public String getLastName() {
	return lastName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PlayerBean other = (PlayerBean) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public Set<ClubBean> getClubs() {
	return clubs;
    }

    public void setClubs(Set<ClubBean> clubs) {
	this.clubs = clubs;
    }

    public ClubBean getSingleLeagueClub() {
	return singleLeagueClub;
    }

    public void setSingleLeagueClub(ClubBean singleLeagueClub) {
	this.singleLeagueClub = singleLeagueClub;
    }

    public boolean isSingleRelevant() {
	return singleRelevant;
    }

    public void setSingleRelevant(boolean singleRelevant) {
	this.singleRelevant = singleRelevant;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

}
