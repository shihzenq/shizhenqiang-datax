package com.wugui.dataxweb.util;

import com.wugui.dataxweb.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 */
@Component
@Slf4j
public class KlksRedisUtils extends RedisUtils {


	/**
	 * 从缓存查询userInfo
	 *
	 * @param userId
	 * @return
	 */
	public UserEntity getUserInfoFromCache(String userId) {
		try {
			String key = "userInfo:";
			CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.user, key + userId);
			return get(cacheKey, UserEntity.class);
		} catch (Exception e) {
			log.error("从缓存查询userInfo异常: ", e);
			return null;
		}
	}

	/**
	 * 缓存userInfo
	 *
	 * @param userId
	 * @return
	 */
	public void saveUserInfoToCache(String userId, UserEntity userInfo) {
		String key = "userInfo:";
		try {
			CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.user, key + userId);
			set(cacheKey, userInfo);
		} catch (Exception e) {
			log.error("用户信息缓存更新失败: ", e);
		}

	}

	/**
	 * 删除merchantTop
	 * @param
	 * @return
	 */
	public void deleteMerchantTopCache() {
		String key = "activityTop:";
		try {
			CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.activityTop, key);
			delete(cacheKey);
		} catch (Exception e) {
			log.error("商户激活机具排行榜删除: ", e);
		}

	}

	/**
	 * 从缓存查询merchantTop
	 * @return
	 */
	public String getMerchantTopCache() {
		try {
			String key = "activityTop:";
			CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.activityTop, key);
			return get(cacheKey);
		} catch (Exception e) {
			log.error("获取商户激活机具排行榜: ", e);
			return null;
		}
	}


	/**
	 * 缓存token
	 *
	 * @param userId
	 * @return
	 */
	public void saveToken(String userId, String token) {
		String key = "token:" + userId;
		CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.user, key);
		set(cacheKey, token);
	}

	/**
	 * 从缓存查询token
	 *
	 * @param userId
	 * @return
	 */
	public String getToken(String userId) {
		String key = "token:" + userId;
		CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.user, key);
		return get(cacheKey);
	}

	/**
	 * 从缓存删除token
	 *
	 * @param userId
	 * @return
	 */
	public Boolean deleteToken(String userId) {
		String key = "token:" + userId;
		CacheKey cacheKey = new CacheKey(CacheKey.CacheKeyPrefix2.user, key);
		return delete(cacheKey);
	}

}