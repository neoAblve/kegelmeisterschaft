package kegelmeisterschaft.dao;

import kegelmeisterschaft.entities.ClubBean;

import org.springframework.stereotype.Repository;

@Repository
public class ClubHome extends AbstractDao<ClubBean> {

    // Für fachliche Schlüssel
    // public ClubBean findClubByName(String name) {
    // Query query = em.createNamedQuery(ClubBean.FIND_BY_NAME).setParameter(
    // "name", name);
    // return (ClubBean) query.getSingleResult();
    // }

}
