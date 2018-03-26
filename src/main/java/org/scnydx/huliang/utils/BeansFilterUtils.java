package org.scnydx.huliang.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: CSG
 * @Description: bean处理工具类
 * @Date: Create in 18:09 2018/2/6
 * @Modify by:
 */
public class BeansFilterUtils {

    /**
     * 复制bean忽略为NULL的参数
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }

    /**
     * 复制bean忽略为NULL的参数
     * @param source
     * @param target
     * @param ignoreProperties 可以为null的参数
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target, String[] ignoreProperties){
        String[] nullProperties = removeIgnoreProperties(getNullProperties(source), ignoreProperties);
        BeanUtils.copyProperties(source, target, nullProperties);
    }

    /**
     * 复制bean 忽略为NULL的参数和参数值为默认值的
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNullAndZero(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullAndZeroProperties(source));
    }

    /**
     * 复制bean 忽略为NULL的参数和参数值为默认值的
     * @param source
     * @param target
     * @param ignoreProperties 可以为null或默认值的参数
     */
    public static void copyPropertiesIgnoreNullAndZero(Object source, Object target, String[] ignoreProperties) {
        String[] nullProperties = removeIgnoreProperties(getNullAndZeroProperties(source), ignoreProperties);
        BeanUtils.copyProperties(source, target, nullProperties);
    }



    /**
     * 获取为NULL的参数
     * @param source
     * @return
     */
    public static String[] getNullProperties(Object source){
        BeanWrapperImpl srcBean = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = srcBean.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 获取为NULL的参数
     * @param source
     * @return
     */
    public static String[] getNullAndZeroProperties(Object source){
        BeanWrapperImpl srcBean = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = srcBean.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            } else if ((srcValue instanceof String) && StringUtils.isEmpty(srcValue)) {
                emptyNames.add(pd.getName());
            } else if ((srcValue instanceof Double) && ((double) srcValue) == 0.0) {
                emptyNames.add(pd.getName());
            } else if ((srcValue instanceof Float) && ((float) srcValue) == 0.0) {
                emptyNames.add(pd.getName());
            } else if ((srcValue instanceof Integer) && ((int) srcValue) == 0) {
                emptyNames.add(pd.getName());
            } else if ((srcValue instanceof Long) && ((long) srcValue) == 0L) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 获取参数为null的参数名，但不包括ignoreProperties
     * @param nullPropertyNames
     * @param ignoreProperties
     * @return
     */
    public static String[] removeIgnoreProperties(String[] nullPropertyNames, String[] ignoreProperties) {
        List<String> ignorePropertyList = Arrays.asList(ignoreProperties);

        Set<String> emtryNames = new HashSet<String >();
        for (String nullPropertyName : nullPropertyNames) {
            if (!ignorePropertyList.contains(nullPropertyName)) {
                emtryNames.add(nullPropertyName);
            }
        }
        String[] result = new String[emtryNames.size()];
        return emtryNames.toArray(result);
    }

}
