package cn.ccsit.common.list;

import java.util.ArrayList;
import java.util.List;

 public final class ListUtils {

	/**
	 * 查找多个属性值匹配的list
	 * @param list
	 * @param u
	 * @param hook
	 * @return
	 */
	public static <T> List<T> filter(List<T> list,T u, ListUtilsHook<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {
        	
            if (hook.contains(t,u)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }
	
	/**
	 * 查找字符串属性值匹配的list
	 * @param list
	 * @param value
	 * @param hook
	 * @return
	 */
	public static <T> List<T> filter(List<T> list,String value, ListUtilsHookString<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {
        	
            if (hook.contains(t,value)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }
	
	/**
	 * 查找int值匹配的LIST
	 * @param list
	 * @param value
	 * @param hook
	 * @return
	 */
	public static <T> List<T> filter(List<T> list,int value, ListUtilsHookInt<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {        	
            if (hook.contains(t,value)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }
	
	/**
	 * 查找字符串属性值匹配的T
	 * @param list
	 * @param value
	 * @param hook
	 * @return 
	 * @return
	 */
	public static <T> T filterOne(List<T> list,String value, ListUtilsHookT<T> hook) {       
		T r=null;
        for (T t : list) {        	
            if (hook.contains(t,value)) {            	
            	r=t;
            	break;
            }
        }
        return r;
    }
}
