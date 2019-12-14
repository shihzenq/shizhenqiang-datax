package com.wugui.dataxweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {

    private StringUtil() {
    }

    /**
     * 过滤字符串首尾空格
     *
     * @param str 字符串
     */
    public static String trimInnerSpaceStr(String str) {
        try {
            str = str.trim();
            while (str.startsWith(" ")) {
                str = str.substring(1, str.length()).trim();
            }
            while (str.endsWith(" ")) {
                str = str.substring(0, str.length() - 1).trim();
            }
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    //String转Long类型转换
    public static Long[] StringtoLong(String str, String regex) {
        String strs[] = str.split(regex);
        Long array[] = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            array[i] = Long.parseLong(strs[i]);
        }
        return array;
    }
    
	/**
	 * 将一个字符串变成一个整数，如果为空或不能转换，则返回0
	 * 
	 * @param strSource
	 *            需要转换的字符串
	 * @return 转换后的整数
	 */
	public static int msReturnInt(String strSource) {
		if (StringUtil.msIsEmpty(strSource)) {
			return 0;
		} else {
			try {
				return Integer.parseInt(strSource);
			} catch (NumberFormatException ex) {
				return 0;
			}

		}
	}

	/**
	 * 判断一个字符串是否为空字符串
	 * 
	 * @param strSource
	 *            需要转换的字符串
	 * @return 如果为空返回true，否则返回false
	 */
	public static boolean msIsEmpty(String strSource) {
		char chrS;
		int intStrLength = 0;
		int i = 0;

		// 如果为空对象，则返回true
		if (strSource == null) {
			return true;
		}

		intStrLength = strSource.length();
		for (i = 0; i < intStrLength; i++) {
			chrS = strSource.charAt(i);
			if ((chrS != ' ') && (chrS != '\t') && (chrS != '\n')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断对象为空
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		}
		if ((object instanceof String)) {
			return ((String) object).trim().equals("");
		}
		if ((object instanceof List)) {
			return ((List) object).size() == 0;
		}
		return false;
	}

	public static String formatPeriod(String p) {
		String format = "";
		try {
			Integer.parseInt(p);
			String year = p.substring(0, 4);
			String month = p.substring(4, 6);
			format = year + "年" + month + "月折旧";
		} catch (Exception e) {
			format = p;
		}
		return format;
	}

	//校验输入的是否为数字
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; ++i) {
			if (!(Character.isDigit(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	//校验输入的是否为数字
	public static boolean isNumericAndCapital(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();		
		for (int i = 0; i < sz; ++i) {
			if (((!Character.isUpperCase(str.charAt(i)))&&(!(Character.isDigit(str.charAt(i)))))) {
				return false;
			}
		}
		return true;
	}
	
	//校验输入的日期格式（yyyyMMdd）
	public static boolean isSpecificFormat(String str) {
        boolean flag = true;     
        String strN = !str.contains("年") ? str : str.substring(0, str.lastIndexOf("年"))+str.substring(str.lastIndexOf("年")+1,str.lastIndexOf("月"))+str.substring(str.lastIndexOf("月")+1,str.length()-1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try{
            format.setLenient(false);
            format.parse(strN);
        }catch (ParseException e){
            flag = false;
        }        
        return flag;
    }
	//校验输入的日期格式（yyyyMMdd HH:mm:ss）
	public static boolean isStringToDateFormat(String str) {
        boolean flag = true;        
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try{
            format.setLenient(false);
            format.parse(str);
        }catch (ParseException e){
            flag = false;
        }        
        return flag;
    }

    // 校验输入的日期格式（yyyy-MM-dd）
	public static boolean isDateFormat(String str) {
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			format.setLenient(false);
			format.parse(str);
		}catch (ParseException e){
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @param strDate 需要验证的字符串 例如：2018-09-15 03:47:58
	 * @param fmt 时间格式 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static boolean isStringToDate(String strDate,String fmt) {	
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat(fmt);       
		try {
			format.setLenient(false);
            format.parse(strDate);
		} catch (ParseException e) {			
			flag = false;
		}
        return flag;
	}
	//校验输入的日期格式（yyyy-MM-dd）
	public static boolean isStringToOnlyDateFormat(String str) {
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			format.setLenient(false);
			format.parse(str);
			flag = isNumeric(str.substring(0,4))&&isNumeric(str.substring(5,7))&&isNumeric(str.substring(8))&&str.substring(8).length()==2;			
		}catch (ParseException e){
			flag = false;
		}
		return flag;
	}
	//校验输入的日期格式（yyyy-M-d）
	public static boolean isStringToSimpleDateFormat(String str) {
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
		try{
			format.setLenient(false);
			format.parse(str);
		}catch (ParseException e){
			flag = false;
		}
		return flag;
	}

	//校验输入的日期格式（yyyy/M/d）
	public static boolean isStringToSimpleDateFormatByCSV(String str) {
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
		try{
			format.setLenient(false);
			format.parse(str);
		}catch (ParseException e){
			flag = false;
		}
		return flag;
	}

	//校验输入的日期格式（ HH:mm:ss）
	public static boolean isStringToOnlyTimeFormat(String str) {
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try{
			format.setLenient(false);
			format.parse(str);
		}catch (ParseException e){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 字符串转时间
	 * @param strDate 需要转的字符串 例如：2019-03-04
	 * @param format  时间格式 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static Date stringToDate(String strDate,String format) {		
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parse = null;
		try {
			parse = sdf.parse(strDate);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
        return parse;
	}

	/**
	 * 生成36位的UUID
	 * @return String
	 */
	public static String getUUID(){
		String  uuid36 = UUID.randomUUID().toString();
		return uuid36;

	}

	/**
	 * 校验是否为正整数
	 * @param str 校验的数字
	 * @return 是否是正整数
	 */
	public static boolean checkPositiveInteger (String str){
		Pattern pattern = Pattern.compile("^[0-9]\\d*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 校验是否为金额
	 * @param str 校验的数字
	 * @return 是否为金额
	 */
	public static boolean checkNumber(String str) {
		Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
		return pattern.matcher(str).matches();
	}

	public static boolean checkNegativeNumber(String str){
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 校验包含几个小字符串
	 * @param maxString 大字符
	 * @param minString 小字符
	 * @return
	 */
	public static int getCount(String maxString, String minString) {
        // 定义一个统计变量，初始化值是0
        int count = 0;
        int index;
        //先查，赋值，判断
        while((index=maxString.indexOf(minString))!=-1){
            count++;
            maxString = maxString.substring(index + minString.length());
        }
        return count;
    }
}
