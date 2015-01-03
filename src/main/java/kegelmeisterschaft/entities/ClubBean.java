package kegelmeisterschaft.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "ClubBean")
@NamedQueries({
	@NamedQuery(name = ClubBean.FIND_BY_NAME, query = "SELECT c FROM ClubBean c WHERE c.name = ?"),
	@NamedQuery(name = ClubBean.FIND_BY_TYPE, query = "SELECT c FROM ClubBean c WHERE c.type = ?"), //
	@NamedQuery(name = ClubBean.FIND_BY_TYPE_AND_YEAR, query = "SELECT c FROM ClubBean c WHERE c.type = ? and c.year = ?"), //
	@NamedQuery(name = ClubBean.FIND_BY_YEAR, query = "SELECT c FROM ClubBean c WHERE c.year = ?"),//
})
@Table(name = "club")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClubBean {
    public static final String FIND_BY_NAME = "findClubByName";
    public static final String FIND_BY_TYPE = "findClubByType";
    public static final String FIND_BY_TYPE_AND_YEAR = "findClubByTypeAndYear";
    public static final String FIND_BY_YEAR = "findClubsByYear";

    public enum ClubType {
	MALE, FEMALE, MIXED;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ClubType type;

    @Column(name = "year")
    private String year;

    private String foundingYear;
    private int memberCount;
    private String location;

    @ManyToMany(targetEntity = PlayerBean.class, fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
	    CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
    @JoinTable(name = "club_player", joinColumns = { @JoinColumn(name = "club_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "player_id", referencedColumnName = "id") })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerBean> player = new HashSet<PlayerBean>();

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public ClubType getType() {
	return type;
    }

    public void setTypeFromString(String type) {
	this.type = ClubType.valueOf(type);
    }

    public void setType(ClubType type) {
	this.type = type;
    }

    public String getFoundingYear() {
	return foundingYear;
    }

    public void setFoundingYear(String foundingYear) {
	this.foundingYear = foundingYear;
    }

    public int getMemberCount() {
	return memberCount;
    }

    public void setMemberCount(int memberCount) {
	this.memberCount = memberCount;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
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
	ClubBean other = (ClubBean) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public Set<PlayerBean> getPlayer() {
	return player;
    }

    public void setPlayer(Set<PlayerBean> player) {
	this.player = player;
    }

    public void addPlayer(PlayerBean player) {
	if (this.player == null) {
	    this.player = new HashSet<PlayerBean>();
	}
	this.player.add(player);
    }

    @Override
    public String toString() {
	return "ClubBean [id=" + id + ", name=" + name + ", type=" + type + ", foundingYear=" + foundingYear
		+ ", memberCount=" + memberCount + ", location=" + location + ", player=" + player + "]";
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

}
