package kegelmeisterschaft.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

public abstract class AbstractDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> entityClass;
    protected final String entityName;
    protected String entityAlias;
    protected String tableName;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
	ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
	entityClass = (Class<T>) type.getActualTypeArguments()[0];

	Entity entity = entityClass.getAnnotation(Entity.class);
	entityName = entity != null ? entity.name() : entityClass.getName();

	Table table = entityClass.getAnnotation(Table.class);
	tableName = table != null ? table.name() : entityClass.getName();

	entityAlias = String.valueOf(entityName.charAt(0)).toLowerCase();
    }

    protected Session getSession() {
	return (Session) em.getDelegate();
    }

    @SuppressWarnings("unchecked")
    public T createOrUpdate(T object) {
	return (T) getSession().merge(object);
    }

    public void remove(T object) {
	remove(object, false);
    }

    @SuppressWarnings("unchecked")
    public void remove(T object, boolean immediate) {
	Session session = getSession();
	object = (T) session.merge(object); // must do this for attaching the
					    // instance
	session.delete(object);
	// Relationen sollen sofort gelöscht werden, da es sonst Probleme bei
	// gleichzeitigen INSERTs und DELETEs geben kann
	if (immediate)
	    session.flush();
    }

    public void removeAll() {
	for (T t : listAll())
	    getSession().delete(t);
    }

    @SuppressWarnings("unchecked")
    public T findById(int id) {
	return (T) getSession().get(entityClass, id);
    }

    public List<T> listAll() {
	return listByQuery("SELECT " + entityAlias + " FROM " + entityName + " " + entityAlias);
    }

    @SuppressWarnings("unchecked")
    public List<T> listByQuery(String query, Object... args) {
	return (List<T>) createQuery(query, args).list();
    }

    @SuppressWarnings("unchecked")
    public List<T> listByNamedQuery(String query, Object... args) {
	return (List<T>) createNamedQuery(query, args).list();
    }

    private T findExactlyOne(List<T> list) throws ObjectNotFoundException {
	switch (list.size()) {
	case 0:
	    throw new ObjectNotFoundException(entityName, entityClass.toString());
	case 1:
	    return list.get(0);
	default:
	    throw new ObjectNotFoundException(entityName, "More than one " + entityName + " found!");
	}
    }

    public T findExactlyOneByNamedQuery(String query, Object... args) throws ObjectNotFoundException {
	return findExactlyOne(listByNamedQuery(query, args));
    }

    private Query createQuery(String query, Object... args) {
	if (!query.toUpperCase().startsWith("SELECT"))
	    query = "SELECT " + (query.contains("IN(") ? "DISTINCT " : "") + entityAlias + " FROM " + entityName + " "
		    + entityAlias + " "
		    + (StringUtils.isEmpty(query) || query.toUpperCase().contains("WHERE") ? "" : "WHERE ") + query;
	Query q = getSession().createQuery(query);
	if (args != null) {
	    int i = 0;
	    for (Object arg : args)
		q.setParameter(i++, arg);
	}
	return q;
    }

    private Query createNamedQuery(String namedQuery, Object... args) {
	Query q = getSession().getNamedQuery(namedQuery);
	// q.setHint("org.hibernate.cacheable", Boolean.TRUE);
	if (args != null) {
	    int i = 0;
	    for (Object arg : args) {
		q.setParameter(i++, arg);
	    }
	}
	return q;
    }

    /**
     * Lösche alle Entitäten aus dem EntityManager
     */
    public void clear() {
	getSession().clear();
    }

    /**
     * Schreibt alle Änderungen in die Datenbank
     */
    public void flush() {
	getSession().flush();
    }

    public void resetId() {
	getSession().createSQLQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1").executeUpdate();
    }

}
