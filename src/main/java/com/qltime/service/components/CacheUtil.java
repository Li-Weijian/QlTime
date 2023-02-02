package com.qltime.service.components;

import cn.hutool.cache.impl.FIFOCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 缓存工具
 * @author liweijian
 * @date 2023/2/1 20:25
 */
@Slf4j
@Component("CacheUtil")
@Order(0)
public class CacheUtil {

    /**
     * 用户另一半ID缓存
     */
    private static FIFOCache<Integer, Integer> userHalfIdCache;

    public CacheUtil() {
        userHalfIdCache = cn.hutool.cache.CacheUtil.newFIFOCache(10000);
    }

    /**
     * 保存另一半的Id到缓存中
     * @param halfId
     * @return
     */
    public static void saveHalfId(Integer halfId, Integer myId){
        log.info("缓存另一半ID成功: halfId: {}, myId: {}", halfId, myId);
        userHalfIdCache.put(myId, halfId);
    }

    public static Integer getHalfId(Integer myId){
        return userHalfIdCache.get(myId);
    }


}
