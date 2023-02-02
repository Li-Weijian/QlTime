package com.qltime.utils;

/**
 * <p>
 * 数据权限助手
 * 主要为了简化 查询语句中拼接的另一半的ID
 * </p>
 *
 * @author liweijian
 * @date 2023/2/2 10:18
 */
public class DataScopeHelper {

    protected static final ThreadLocal<Integer> threadLocal = new ThreadLocal();

    /**
     * 设置另一半标识，如果不开启，默认不拼接查询另一半用户数据
     */
    public static void startDataScope(){
        threadLocal.set(RequestUtils.getHalfId());
    }

    /**
     * 获取另一半标识
     * @return
     */
    public static Integer getDataScope(){
        return threadLocal.get();
    }

    public static void cleanDataScopes(){
        threadLocal.remove();
    }

}
