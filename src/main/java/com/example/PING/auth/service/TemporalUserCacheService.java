package com.example.PING.auth.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TemporalUserCacheService {

    // 데이터 조회 (캐시된 값이 있으면 반환, 없으면 null)
    @Cacheable(value = "localCache", key = "#p0", unless = "#result == null")
    public <T> T get(String key, Class<T> clazz) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Cache key cannot be null or empty");
        }
        return null;
    }

    // 데이터 저장 (항상 실행되며, 캐시에 저장됨)
    @CachePut(value = "localCache", key = "#p0")
    public Object set(String key, Object value) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Cache key cannot be null or empty");
        }
        return value;
    }

    // 데이터 삭제
    @CacheEvict(value = "localCache", key = "#p0")
    public void delete(String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Cache key cannot be null or empty");
        }
    }
}
