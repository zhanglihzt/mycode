package com.tp.modules.anjian.utils;

import com.google.common.collect.Maps;
import com.tp.common.annotation.FieldName;
import com.tp.common.utils.StringUtils;
import com.tp.modules.anjian.entity.RiskEnterprise;
import com.tp.modules.anjian.entity.view.RiskEnterpriseInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangli on 2018-1-8.
 */
public class Map2Object {
    private static Log logger= LogFactory.getLog(Map2Object.class);
    /***
     *  map转对象
      * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null){
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
          /*  int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }*/
            logger.error("fieldName:"+field.getName());
            field.setAccessible(true);
            if(map.get(field.getName())!=null){
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }

    /***
     * obj转map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

    public static Map<String,Object> objectToObject(Object object, Class<?> beanClass)  {
        if (object == null){
            return null;
        }

        Map<String,Object> map = Maps.newHashMap();
        int count = 0;
        StringBuffer sb = new StringBuffer("");
        try{
            Object obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Method method = null;
                try {
                    method = object.getClass().getDeclaredMethod("get"+upperCase(field.getName()));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                // System.out.println(field.getName()+"-----"+method.invoke(object));
                Object value = null;
                try {
                    value = method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(null == value){
                    count++;
                    String fieldDesc = field.getAnnotation(FieldName.class).value();
                    sb.append(fieldDesc).append(",");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        map.put(CommonConstant.COUNT,count);
        map.put(CommonConstant.SEARCH_RESULT,sb.toString());
        return map;
    }

    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
         ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RiskEnterprise riskEnterprise = new RiskEnterprise();
        riskEnterprise.setName("name");
        riskEnterprise.setAddress("address");
        riskEnterprise.setDistrictName("districtNAME");
        riskEnterprise.setDutyPersonName("dutyPersonName");
        riskEnterprise.setEconomy("economy");
        riskEnterprise.setEconomyName("economyName");
        RiskEnterpriseInfo info = new RiskEnterpriseInfo();
        info = (RiskEnterpriseInfo) objectToObject(riskEnterprise,RiskEnterpriseInfo.class);
        System.out.println("-----");
    }

    /**
     * 返回是否有空值
     * 有空值返回true，无空值返回false
     * @param object
     * @param beanClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static boolean objectToObj(Object object, Class<?> beanClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (object == null){
            return true;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            logger.error("fieldName:"+field.getName());
            field.setAccessible(true);
            Method method = object.getClass().getDeclaredMethod("get"+upperCase(field.getName()));
           // System.out.println(field.getName()+"-----"+method.invoke(object));
            Object value = method.invoke(object);
            if(null == value){
                return true;
            }
        }
        return false;
    }

}
