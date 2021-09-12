package cn.ccsit.eeoi.data.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommonRepository {

	/**
	 * 根据jpql语句查询记录数量
	 * @param jpql
	 * @return
	 */
	long findCountByJPQLCriteria(String jpql);
		
	/**
	 * 根据jpql语句 及查询参数查询记录数量 
	 * @param jpql
	 * @param parameters
	 * @return
	 */
	long findCountByJPQLCriteria(String jpql,HashMap<String,Object> parameters);
		
	/**
	 * 根据sql语句查询记录数量
	 * @param sql
	 * @return
	 */
	long findCountBySQLCriteria(String sql);
	
	/**
	 * 根据sql语句及查询参数查询记录数量 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	long findCountBySQLCriteria(String sql,HashMap<String,Object> parameters);
	
	
	/**
	 * 根据jpql语句查询所有记录 
	 * @param c
	 * @param jpql
	 * @return
	 */
	<T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql);
	
		
	
	/**
	 * 根据jpql语句及参数查询所有记录 
	 * @param c
	 * @param jpql
	 * @param parameters
	 * @return
	 */
	<T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters);
			
	/**
	 * 根据jpql语句及参数查询所有记录 
	 * @param c
	 * @param jpql
	 * @param parameterName 一个参数查询条件
	 * * @param parameterValue 一个参数查询条件
	 * @return
	 */
	<T> List<T> findAllByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue);
	
	/**
	 * 根据sql语句查询所有记录 
	 * @param c
	 * @param sql
	 * @return
	 */
	<T> List<T> findAllBySQLCriteria(Class<T> c,String sql);
	
		
	
	/**
	 * 根据sql语句及参数查询所有记录 
	 * @param c
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> findAllBySQLCriteria(Class<T> c,String sql,HashMap<String,Object> parameters);
	
	
	
	/**
	 * 根据jpql语句查询分页记录
	 * @param c        泛型类
	 * @param jpql     jpql语句 
	 * @param pageable 分页对象 
	 * @return
	 */
	
	<T> Page<T> findPagedDataByJPQLCriteria(Class<T> c,String jpql,Pageable pageable);
	
	
	 <T>  Page<T> findPagedDataByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue,Pageable pageable);
	/**
	 * 根据jpql语句及参数查询分页记录 
	 * @param c        泛型类
	 * @param jpql     jpql语句 
	 * @param parameters  参数
	 * @param pageable   分页对象 
	 * @return
	 */
	<T> Page<T> findPagedDataByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters,Pageable pageable);
	
	
	/***
	 * 根据jpql语句查询分页记录,返回jqgrid格式 	
	 * @param c    泛型类
	 * @param jpql jpql语句 
	 * @param pageable 分页对象 
	 * @return
	 */
	<T> Map<String,Object> findPagedRecordByJPQLCriteria(Class<T> c,String jpql,Pageable pageable);
	
	/**
	 * 根据jpql语句及参数查询分页记录 ,返回jqgrid格式   
	 * 单一参数
	 * @param c        泛型类
	 * @param jpql     jpql语句 
	 * @param parameterName   参数名
	 * @param parameterValue  参数值
	 * @param pageable   分页对象 
	 * @return
	 */
	 <T> Map<String,Object>  findPagedRecordByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue,Pageable pageable);
	/**
	 * 根据jpql语句及参数查询分页记录 ,返回jqgrid格式
	 * @param c        泛型类
	 * @param jpql     jpql语句 
	 * @param parameters  参数
	 * @param pageable   分页对象 
	 * @return
	 */
	<T> Map<String,Object>  findPagedRecordByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters,Pageable pageable);
				

	/**
	 * 根据jpql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param c
	 * @param jpql
	 * @return
	 */
	
	<T> T findSingleByJPQLCriteria(Class<T> c,String jqpl);
	 
	/**
	 * 根据jpql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param c
	 * @param jpql
	 * @param parameters
	 * @return
	 */
	<T> T findSingleByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters);
	
	
	/**
	 * 根据jpql语句及一个字符串型参数查询符合条件的第一条记录
	 * @param c
	 * @param jpql
	 * @param parameterName  单一参数名
	 * @param parameterValue 单一参数值
	 * @return
	 */
	<T> T findFirstRecordByJPQLCriteria(Class<T> c,String jpql,String parameterName,String parameterValue);
	
	/**
	 * 根据jpql语句及参数查询符合条件的第一条记录
	 * @param c
	 * @param jpql
	 * @param parameters 查询参数
	 * @return
	 */
	<T> T findFirstRecordByJPQLCriteria(Class<T> c,String jpql,HashMap<String,Object> parameters);
	
	/**
	 * 根据sql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常
	 * @param sql
	 * @return
	 */
	Object findSingleBySQLCriteria(String sql);
	
	/**
	 * 根据sql语句及参数查询符合条件的一条记录，如果存在多条记录则抛出异常	 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	Object findSingleBySQLCriteria(String sql,HashMap<String,Object> parameters);
	
	/**
	 * 根据主键判断记录是否存在
	 * @param <T>
	 * @param id
	 * @return
	 */
	<T> boolean existsById(Class<T> c ,Object id);
	
	/**
	 * 根据主键值查询表中记录 
	 * @param c
	 * @param id
	 * @return
	 */
	<T> T findById(Class<T> c,Object id);
	
	
	/**
	 * 保存对象到数据库
	 * @param c  类名
	 * @param obj  对象
	 * @return
	 */
	<T> Object saveOrUpdate(Class<T> c,T obj);
	
	/**
	 * 保存对象集合到数据库
	 * @param c   类
	 * @param objs 对象集合
	 * @return
	 */
	<T> void saveOrUpdate(Class<T> c,List<T> objs);
	
	float findMaxByJPQLCriteria(String jpql);
	 
	float findMinByJPQLCriteria(String jpql);
	 
	float findSumByJPQLCriteria(String jpql);
	 
	float findAverageByJPQLCriteria(String jpql);
	 
	
}
