package com.wugui.dataxweb.util;

public class CacheKey {
	
	// 服务名
	private CacheKeyPrefix1 prefix1;
	
	// 模块
	private CacheKeyPrefix2 prefix2;
	
	// 方法名或参数名
	private String prefix3;

	public CacheKey(CacheKeyPrefix1 prefix1, CacheKeyPrefix2 prefix2, String methodOrParam) {
		super();
		this.prefix1 = prefix1;
		this.prefix2 = prefix2;
		this.prefix3 = methodOrParam;
	}
	
	public CacheKey(CacheKeyPrefix2 prefix2, String methodOrParam) {
		super();
		this.prefix1 = CacheKeyPrefix1.klks_app;
		this.prefix2 = prefix2;
		this.prefix3 = methodOrParam;
	}

	public String getKeyStr() {
		return prefix1.getCode() + prefix2.getCode() + prefix3;
	}
	
	
	public static void main(String[] args) {
		CacheKey key = new CacheKey(CacheKeyPrefix1.klks_app, CacheKeyPrefix2.user, "token");
		System.out.println(key.getKeyStr());
	}
	
	public enum CacheKeyPrefix1 {

	    klks_app("klks-service:");

	    private CacheKeyPrefix1(String key){
	        this.code = key;
	    }

	    private String code;

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	}

	public enum CacheKeyPrefix2 {

		aspect("aspect:"),
	    user("user:"),
	    activity("activity:"),
	    product("product:"),
	    pos("pos:"),
	    college("college:"),
	    pay("pay:"),
	    system("system:"),
		activityTop("activityTop:"),
		activityManageTop("activityManageTop:"),
		activityTopBook("activityTopBook:"),
	    share("share:");

	    private CacheKeyPrefix2(String key){
	        this.code = key;
	    }

	    private String code;

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	}
	
}

