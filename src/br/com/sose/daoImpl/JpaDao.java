package br.com.sose.daoImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.messaging.MessageTemplate;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import flex.messaging.messages.AsyncMessage;

public class JpaDao<K, E> extends JpaDaoSupport {

	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	public E persist(E entity) {		
			getJpaTemplate().persist(entity);
			return entity;		
	}

	public E merge(E entity) {	
		entity=getJpaTemplate().merge(entity);
		return entity;
	}

	public void refresh(E entity) {
		getJpaTemplate().refresh(entity);
	}

	public E findById(K id) {
		return getJpaTemplate().find(entityClass, id); 
	}

	public E flush(E entity) {
		getJpaTemplate().flush();
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		try{
		Object res = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.id");
				return q.getResultList();
			}

		});
		return (List<E>) res;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public E remove(E entity) {
		entity=findById((K) this.getId(entity, entityClass));
		getJpaTemplate().remove(entity);
		logger.debug("Object " + entity + " removed");
		return entity;
	}

	@SuppressWarnings("unchecked")
	public Integer removeAll() {
		return (Integer) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("DELETE FROM " + entityClass.getName()	+ " h");
				return q.executeUpdate();
			}
		});
	}
	
	private MessageTemplate template;

    public MessageTemplate getTemplate() {
            return template;
    }

    @Autowired
    public void setTemplate(MessageTemplate template) {
            this.template = template;
    }

    //TODO - Esse método está funcionando
    public void sendMessage(String destination, String headerName,
                    String headerValue, Object body) {
            AsyncMessage message = template.createMessage();
    message.setDestination(destination);
    message.setBody(body);
    message.setHeader(headerName, headerValue);
    template.getMessageBroker().routeMessageToService(message, null);
    }
	
	 private Object getId(Object val, Class<? extends Object> classe) {  
	        try {  
	            return classe.getMethod("getId").invoke(val);  
	        } catch (IllegalArgumentException e) {  
	            logger.error("Error ao invocar id - IllegalArgumentException: ", e);  
	        } catch (SecurityException e) {  
	            logger.error("Erro ao invocar id - SecurityException: ", e);  
	        } catch (IllegalAccessException e) {  
	            logger.error("Erro ao invocar id - IllegalAccessException: ", e);  
	        } catch (InvocationTargetException e) {  
	            logger.error("Erro ao invocar id - InvocationTargetException: ", e);  
	        } catch (NoSuchMethodException e) {  
	            logger.error("Erro ao invocar id - NoSuchMethodException: ", e);  
	        }  
	        return null;  
	    }  
}