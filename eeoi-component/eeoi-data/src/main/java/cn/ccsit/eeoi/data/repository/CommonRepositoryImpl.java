package cn.ccsit.eeoi.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;

@Repository("commonRepository")
public class CommonRepositoryImpl implements CommonRepository {

	private Logger log = LoggerFactory.getLogger(CommonRepositoryImpl.class);
	
	//@PersistenceContext(unitName="stems-connect")
	@Autowired
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 根据jpql语句查询记录数量
	 * @param jpql
	 * @return
	 **/
	public long findCountByJPQLCriteria(String jpql) {	
		
		return this.findCountByJPQLCriteria(jpql,null);
	}
	
	/**
	 * 根据jpql语句及参数查询记录数量 
	 * @param jpql
	 * @param parameters
	 * @return
	 */
	public long findCountByJPQLCriteria(String jpql,HashMap<String,Object> parameters) {
		if (IsNullOrWhiteSpace(jpql)) {
			log.error("jpql parameter is null or empty");
			throw new RuntimeException("jpql parameter is null or empty");
		}
		log.debug(jpql);
		Query query = em.createQuery(jpql);
		if (parameters != null) {
			Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				query.setParameter(key, value);
			}
		}		
		
		Object obj = query.getSingleResult();
		if (obj != null) {
			return Long.parseLong(obj.toString());
		} else {
			return 0;
		}
	}
	
	/**
	 * 根据sql语句查询记录数量
	 * @param sql
	 * @return
	 */
	public long findCountBySQLCriteria(String sql) {
		
		return this.findCountBySQLCriteria(sql, null);
	}
	
	/**
	 * 根据sql语句及参数查询记录数量 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public long findCountBySQLCriteria(String sql,HashMap<String,Object> parameters) {
		if (IsNullOrWhiteSpace(sql))
		{
			log.error("sql parameter is null or empty");
			throw new RuntimeException("sql parameter is null or empty");			
		}
		log.debug(sql);
		Query nativeQuery=em.createNativeQuery(sql);
		if (parameters != null) {
			Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				nativeQuery.setParameter(key, value);
			}
		}
		Object obj =nativeQuery.getSingleResult();
		if (obj!=null)
		{
			return Long.parseLong(obj.toString());
		}else
		{
			return 0;
		}
	}
	
	/**
	 * 根据jpql语句查询所有记录 
	 * @param c
	 * @param jpql
	 * @return
	 */
	
	public <T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql ) {

		return this.findAllByJPQLCriteria(c, jpql,null);
	}
	
		
	/**
	 * 根据jpql语句及参数查询所有记录 
	 * @param c
	 * @param jpql
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters) {
		
		if (IsNullOrWhiteSpace(jpql))
		{
			log.error("jpql parameter is null or empty");
			throw new RuntimeException("jpql parameter is null or empty");			
		}
		log.debug(jpql);		
		Query query = em.createQuery(jpql,c); 
		if (parameters != null) {
			Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				query.setParameter(key, value);
			}
		}
		return query.getResultList();		
	}
	
	/**
	 * 根据jpql语句及参数查询所有记录 
	 * @param c
	 * @param jpql
	 * @param parameterName 一个参数查询条件
	 * * @param parameterValue 一个参数值
	 * @return
	 */
	public <T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue){
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put(parameterName,parameterValue);
		return this.findAllByJPQLCriteria(c, jpql,map);
	}

	public <T> List<T> findAllBySQLCriteria(Class<T> c,String sql) {
		
		return this.findAllBySQLCriteria(c, sql,null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllBySQLCriteria(Class<T> c,String sql,HashMap<String,Object> parameters) {
		
		if (IsNullOrWhiteSpace(sql))
		{
			log.error("sql parameter is null or empty");
			throw new RuntimeException("sql parameter is null or empty");			
		}
		log.debug(sql);
		Query nativeQuery=em.createNativeQuery(sql,c);		
		if (parameters != null) {
			Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				nativeQuery.setParameter(key, value);
			}
		}
		
		return nativeQuery.getResultList();		
	}
	
	/***
	 * 根据jpql语句查询分页记录
	 */
	public <T> Page<T> findPagedDataByJPQLCriteria(Class<T> c,String jpql,Pageable pageable)
	{
		return 	this.findPagedDataByJPQLCriteria(c,jpql,null,pageable);	
	}
	
	public <T>  Page<T> findPagedDataByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue,Pageable pageable){
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put(parameterName,parameterValue);		
		Page<T> page=this.findPagedDataByJPQLCriteria(c,jpql,map,pageable);
		return page;
	}
	
	/**
	 * 根据jpql语句查询分页记录,带参数
	 */
	public <T> Page<T> findPagedDataByJPQLCriteria(Class<T> c, String jpql,HashMap<String,Object> parameters,Pageable pageable)
	{
		if (IsNullOrWhiteSpace(jpql))
		{
			log.error("jpql parameter is null or empty");
			throw new RuntimeException("jpql parameter is null or empty");			
		}
		
		if (pageable==null)
		{
			log.error("pageable is null ");
			throw new RuntimeException("pageable is null");			
		}
		
		log.debug(jpql);
		
		int selectIndex=jpql.toUpperCase().indexOf("SELECT ");
		int fromIndex =jpql.toUpperCase().indexOf(" FROM ");	
		
		String selectJpqlQuery="select new "+c.getName()+"("+jpql.substring(selectIndex+6,fromIndex )+") "+jpql.substring(fromIndex);		
		String countJpqlQuery="select count(1) " +jpql.substring(fromIndex);	
		
		TypedQuery<T> query = em.createQuery(selectJpqlQuery,c); 
		TypedQuery<Long> queryCount =em.createQuery(countJpqlQuery,Long.class);
		
		if (parameters != null) {
			Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				query.setParameter(key, value);
				queryCount.setParameter(key, value);
			}
		}		
		
		Long total=  queryCount.getSingleResult();
		if (total==0) {
			return new PageImpl<T>(new ArrayList<T>(),pageable,total);
		}
		int offset=(int)pageable.getOffset() ;
		int pageSize=pageable.getPageSize();
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		
		
		List<T> list=query.getResultList();	
		return new PageImpl<T>(list,pageable,total);
	}
	
	
	/***
	 * 根据jpql语句查询分页记录 ,返回jqgrid格式
	 */
	public  <T> Map<String,Object> findPagedRecordByJPQLCriteria(Class<T> c,String jpql,Pageable pageable)
	{
		Page<T> page=this.findPagedDataByJPQLCriteria(c, jpql, pageable);		
		return ConvertJqGrid(page);			
	}
	
	public <T> Map<String,Object>  findPagedRecordByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue,Pageable pageable){
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put(parameterName,parameterValue);		
		Page<T> page=this.findPagedDataByJPQLCriteria(c,jpql,map,pageable);
		return ConvertJqGrid(page);	
	}
	
	/**
	 * 根据jpql语句及参数查询分页记录 ,返回jqgrid格式 
	 */
	
	public <T> Map<String,Object>  findPagedRecordByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters,Pageable pageable)
	{
		Page<T> page=this.findPagedDataByJPQLCriteria(c,jpql,parameters,pageable);
		return ConvertJqGrid(page);	
	}
		
	
	/**
	 * 根据jpql语句查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param c
	 * @param jpql
	 * @return
	 */
	public <T> T findSingleByJPQLCriteria(Class<T> c,String jpql) {		
		
		return findSingleByJPQLCriteria(c,jpql,null);
		
	}
	
	/**
	 * 根据jpql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param c
	 * @param jpql
	 * @return
	 */
	public <T> T findSingleByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters) {
		if (IsNullOrWhiteSpace(jpql))
		{
			log.error("jpql parameter is null or empty");
			throw new RuntimeException("jpql parameter is null or empty");			
		}
		log.debug(jpql);
		TypedQuery<T> query=em.createQuery(jpql, c);		
		try {			
			if (parameters != null) {
				Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					query.setParameter(key, value);
				}
			}
			return query.getSingleResult();		
			
		}catch(javax.persistence.NoResultException ex){
			log.warn("findSingleByJPQLCriteria:object is not found ");			
			return null;
		}catch(javax.persistence.NonUniqueResultException ex){
			log.error("findSingleByJPQLCriteria:object is multiple result ");				
			throw new RuntimeException("multiple result");
		}catch(javax.persistence.QueryTimeoutException ex){
			log.error("findSingleByJPQLCriteria:query timeout ");
			throw new RuntimeException("query timeout");
		}
	}
	
	/**
	 * 根据jpql语句及一个字符串型参数查询符合条件的第一条记录
	 * @param c
	 * @param jpql
	 * @param parameterName  单一参数名
	 * @param parameterValue 单一参数值
	 * @return
	 */
	public <T> T findFirstRecordByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue){
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put(parameterName,parameterValue);
		return this.findFirstRecordByJPQLCriteria(c, jpql, map);
	}
	
	/**
	 * 根据jpql语句及参数查询符合条件的第一条记录
	 * @param c
	 * @param jpql
	 * @param parameters 查询参数
	 * @return
	 */
	public <T> T findFirstRecordByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters) {
		if (IsNullOrWhiteSpace(jpql))
		{
			log.error("jpql parameter is null or empty");
			throw new RuntimeException("jpql parameter is null or empty");			
		}
		log.debug(jpql);
		TypedQuery<T> query=em.createQuery(jpql, c);		
		try {			
			if (parameters != null) {
				Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					query.setParameter(key, value);
				}
			}
			List<T> list=query.getResultList();
			if (list.isEmpty())
			{
				return null ;
			}else
			{
				return list.get(0);		
			}
			
		}catch(javax.persistence.NoResultException ex){
			log.warn("findSingleByJPQLCriteria:object is not found ");			
			return null;
		}catch(javax.persistence.NonUniqueResultException ex){
			log.error("findSingleByJPQLCriteria:object is multiple result ");				
			throw new RuntimeException("multiple result");
		}catch(javax.persistence.QueryTimeoutException ex){
			log.error("findSingleByJPQLCriteria:query timeout ");
			throw new RuntimeException("query timeout");
		}
	}
	
	/***
	 * 根据sql语句查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param sql
	 * @return
	 */
	public Object findSingleBySQLCriteria(String sql) {		
		
		return this.findSingleBySQLCriteria(sql,null);
		
	}
	
	/***
	 * 根据sql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public Object findSingleBySQLCriteria(String sql,HashMap<String,Object> parameters) {
		if (IsNullOrWhiteSpace(sql))
		{
			log.error("sql parameter is null or empty");
			throw new RuntimeException("sql parameter is null or empty");			
		}		
		log.debug(sql);		
		Query nativeQuery = em.createNativeQuery(sql); 		
		try {		
			if (parameters != null) {
				Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					nativeQuery.setParameter(key, value);
				}
			}
			return nativeQuery.getSingleResult();
			
		}catch(javax.persistence.NoResultException ex){
			log.warn("findSingleBySQLCriteria:object is not found ");			
			return null;
		}catch(javax.persistence.NonUniqueResultException ex){
			log.error("findSingleBySQLCriteria:object is multiple result ");				
			throw new RuntimeException("multiple result");
		}catch(javax.persistence.QueryTimeoutException ex){
			log.error("findSingleBySQLCriteria:query timeout ");
			throw new RuntimeException("query timeout");
		}
			
	}
	
	public float findMaxByJPQLCriteria(String jpql) {
		// TODO 
		return 0;
	}

	public float findMinByJPQLCriteria(String jpql) {
		// TODO 
		return 0;
	}

	public float findSumByJPQLCriteria(String jpql) {
		// TODO 
		return 0;
	}

	public float findAverageByJPQLCriteria(String jpql) {
		// TODO 
		return 0;
	}

		
	/**
	 *  
	 * @param value
	 * @return
	 */
	
	private boolean IsNullOrWhiteSpace(String value)
	{
		if (value==null)
		{
			return true ;
		}
		
		if ("".equals(value.trim()))
		{
			return true ;
		}
		return false;		
	}

	
	/**
	 * 根据主键判断记录是否存在
	 * @param <T>
	 * @param id
	 * @return
	 */
	public <T> boolean existsById(Class<T> c ,Object id)
	{
		Object obj =this.findById(c,id);
		return obj==null?false:true ;
	}
	
	/**
	 * 根据主键id查询 表中记录 
	 * @param c
	 * @param id
	 * @return
	 */
	public <T> T findById(Class<T> c, Object id) {
		if(id==null)
		{
			log.error("id is null ");
			throw new RuntimeException("id is null");			
		}			
		JpaEntityInformation<T,?> jpaEntityInformation=JpaEntityInformationSupport.getEntityInformation(c, em);
		SingularAttribute<? super T, ?> ID=jpaEntityInformation.getIdAttribute();
		if (!ID.getJavaType().equals(id.getClass()))
		{
			log.error("the type of entry value is not equal to the table's primary key type ");
			throw new RuntimeException("id is null");	
		}	
		T obj=em.find(c, id);	
		return obj;
	}
	
	@Override
	public <T> Object saveOrUpdate(Class<T> c,T obj) {
		JpaEntityInformation<T, ?> jpaEntityInformation=JpaEntityInformationSupport.getEntityInformation(c,em);
		if (jpaEntityInformation.isNew(obj)){
			em.persist(obj);
		}else {
			em.merge(obj);
		}		
		return obj;
	}
	
	@Override
	public <T> void saveOrUpdate(Class<T> c,List<T> objs) {
		JpaEntityInformation<T, ?> jpaEntityInformation=JpaEntityInformationSupport.getEntityInformation(c,em);
		for(T t :objs) {
		if (jpaEntityInformation.isNew(t)){
			em.persist(t);
		}else {
			em.merge(t);
		}
		
		};
	}
	
	public static  <T> Map<String,Object> ConvertJqGrid( Page<T> pageable)
	{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page",pageable.getNumber()+1);	//当前页	
		map.put("total",pageable.getTotalPages());//总页数 
		map.put("records",pageable.getTotalElements());//总行数
		map.put("rows", pageable.getContent()); //每页数据
		return map; 		
	}
	
}
