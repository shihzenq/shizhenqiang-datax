package com.wugui.dataxweb.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Resource(name = "valueOperations")
	private ValueOperations<String, String> valueOperations;

	/**
	 * 默认过期时长，单位：秒
	 */
	public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

	/**
	 * 不设置过期时长
	 */
	public final static long NOT_EXPIRE = -1;

	/**
	 * 插入缓存默认时间
	 *
	 * @param cacheKey 键
	 * @param value    值
	 * @author zmr
	 */
	public void set(CacheKey cacheKey, Object value) {
		try {
			set(cacheKey, value, DEFAULT_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 插入缓存
	 *
	 * @param cacheKey 键
	 * @param value    值
	 * @param expire   过期时间(s)
	 * @author zmr
	 */
	public void set(CacheKey cacheKey, Object value, long expire) {
		try {
			valueOperations.set(cacheKey.getKeyStr(), toJson(value), expire, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("插入缓存异常,KEY:" + cacheKey.getKeyStr(), e);
		}
	}

	/**
	 * 返回字符串结果
	 *
	 * @param cacheKey 键
	 * @return
	 * @author zmr
	 */
	public String get(CacheKey cacheKey) {
		try {
			return valueOperations.get(cacheKey.getKeyStr());
		} catch (Exception e) {
			log.error("查询获取返回字符串异常,KEY: " + cacheKey.getKeyStr(), e);
			return null;
		}
	}

	/**
	 * 返回指定类型结果
	 *
	 * @param cacheKey 键
	 * @param clazz    类型class
	 * @return
	 * @author zmr
	 */
	public <T> T get(CacheKey cacheKey, Class<T> clazz) {
		if (cacheKey == null)
			return null;
		try {
			String value = valueOperations.get(cacheKey.getKeyStr());
			return (null == value || "" == value) ? null : fromJson(value, clazz);
		} catch (Exception e) {
			log.error("查询CLASS类型结果异常" + cacheKey.getKeyStr(), e);
			return null;
		}
	}

	/**
	 * 删除缓存
	 *
	 * @param cacheKey 键
	 * @author zmr
	 */
	public Boolean delete(CacheKey cacheKey) {
		try {
			return redisTemplate.delete(cacheKey.getKeyStr());
		} catch (Exception e) {
			log.error("删除缓存异常" + cacheKey.getKeyStr(), e);
			return false;
		}
	}

	public Long increment(CacheKey cacheKey, long expire) {
		try {
			Long num = valueOperations.increment(cacheKey.getKeyStr());
			if(1 == num){
				redisTemplate.expire(cacheKey.getKeyStr(), expire, TimeUnit.SECONDS);
			}
			return num;
		} catch (Exception e) {
			log.error("查询计数器异常" + cacheKey.getKeyStr(), e);
			return 0L;
		}
	}

	/**
	 * 当缓存中没有时存入，缓存中存在时不存入
	 *
	 * @param expireSeconds s
	 * @return 缓存中没有时返回true，缓存中有时返回false
	 */
	public Boolean setIfAbsent(CacheKey cacheKey, Object value, int expireSeconds) {
		try {
			return redisTemplate.opsForValue().setIfAbsent(cacheKey.getKeyStr(), value, expireSeconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Object转成JSON数据
	 */
	private String toJson(Object object) {
		if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
				|| object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return JSON.toJSONString(object);
	}

	/**
	 * JSON数据，转成Object
	 */
	public <T> T fromJson(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}
}