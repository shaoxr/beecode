package com.newland.beecode.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.intensoft.dao.hibernate.SimpleQueryCondition;

/**
 * @author shaoxr:
 * @version 2011-4-28 下午05:25:50
 * 
 */
public class BaseDao<T, PK extends Serializable> extends SimpleHibernateTemplate<T, PK>{
	public int countSimpleQuery(SimpleQueryCondition cond){
		return this.countByCriteria(genSimpleQueryCondition(cond));
	}
    public Integer countByCriteria(final DetachedCriteria detachedCriteria){
		   return (Integer )getHibernateTemplate().execute(new HibernateCallback<Object>() {  
			   public Object doInHibernate(Session session) throws HibernateException { 
				   Criteria criteria = detachedCriteria.getExecutableCriteria(session); 
	                criteria.setProjection(null);
	               return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();  
			   }
		   });
	   }
    
    @SuppressWarnings("unchecked")
	public List<Object[]> findBySql(final String sql){
    	return (List<Object[]> )getHibernateTemplate().execute(new HibernateCallback<Object>() { 
    		public Object doInHibernate(Session session) throws HibernateException {
    			 Query query=session.createSQLQuery(sql);
				return query.list(); 
    			
    		}
    	});
    }
	@SuppressWarnings("unchecked")
	public List<Object[]> findBySqlByLimit(final String sql,final Integer start,final Integer end){
    	return (List<Object[]> )getHibernateTemplate().execute(new HibernateCallback<Object>() { 
    		public Object doInHibernate(Session session) throws HibernateException {
    			 Query query=session.createSQLQuery(sql);
				return query.setFirstResult(start).setMaxResults(end).list(); 
    			
    		}
    	});
    }
	public int countBySql(final String sql){
    	return (Integer )getHibernateTemplate().execute(new HibernateCallback<Object>() { 
    		public Object doInHibernate(Session session) throws HibernateException {
    			 Query query=session.createSQLQuery(sql);
				List<BigInteger> list=query.list();
				BigInteger obj = (BigInteger) list.get(0);
	            return obj.intValue();
    			
    		}
    	});
    }
}
