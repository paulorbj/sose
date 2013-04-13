package br.com.sose.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

//Adicionando herança ao HibernateDaoSupport tempos diversas funcionalidades do Spring  
//junto ao Hibernate, implementamos também nosso DAO generico para ter mobilidade.  
public class HibernateDaoGenerico<T, ID extends Serializable> implements GenericDao<T, ID> {  

	private static Log LOG = LogFactory.getLog(HibernateDaoGenerico.class);  

	@Autowired
	protected SessionFactory sessionFactory;
	
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	// Nosso construtor vai setar automaticamente via Reflection qual classe  
	// estamos tratando.  
	@SuppressWarnings("unchecked")  
	public HibernateDaoGenerico() {  
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];  
	}  

	// Classe que será persistida.  
	public Class<T> entityClass;  
	
	public Class<T> getPersistentClass() {  
		return this.entityClass;  
	}  

	@Override  
	public void delete(T entity) {  
		try {  
			this.sessionFactory.getCurrentSession().delete(entity);  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  

	@SuppressWarnings("unchecked")  
	@Override  
	public T findById(ID id) {  
		try {  
			return (T) this.sessionFactory.getCurrentSession().get(getPersistentClass(), id);  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  

	@Override  
	public List<T> listAll() {  
		try {  
			return (List<T>) this.sessionFactory.getCurrentSession().createQuery("FROM "+getPersistentClass().getSimpleName()).list();
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  

	@Override  
	public T save(T entity) {  
		try {  
//			this.getHibernateTemplate().clear();
			this.sessionFactory.getCurrentSession().save(entity);  
			return entity;  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  
	
	@Override  
	public T update(T entity) {  
		try {
//			this.getHibernateTemplate().clear();
			this.sessionFactory.getCurrentSession().merge(entity);  
			return entity;  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  
	
	@Override  
	public T saveOrUpdate(T entity) {  
		try {  
//			this.getHibernateTemplate().clear();
			this.sessionFactory.getCurrentSession().saveOrUpdate(entity);  
			return entity;  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  
	
	public void clear() {  
		try {  
			this.sessionFactory.getCurrentSession().clear();   
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	} 

	protected List<T> findByCriteria(Criterion... criterion) {  
		try {  
			Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(getPersistentClass());  
			for (Criterion c : criterion) {  
				crit.add(c);  
			}  
			return crit.list();  
		} catch (final HibernateException ex) {  
			HibernateDaoGenerico.LOG.error(ex);  
			throw ex;  
		}  
	}  
}
