package com.fubon.mplus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;




public class CheckUtils{

	
	public static final String VALID_RESULT = "validResult";
	public static final String VALID_MSG = "validMsg";
	
    /**
	 * 檢核身份證字號/外國人證號.
	 * @param strId 要檢核的身份證字號/外國人證號
	 * @return ture:合法 false:不合法
	 */
	public static boolean isValidID(String str){
		if (str == null || "".equals(str)) {
			return false;
		}
			
		final char[] pidCharArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		// 原身分證英文字應轉換為10~33，這裡直接作個位數*9+10
		final int[] pidIDInt = { 1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11, 20, 48, 29, 38, 47, 56, 65, 74, 83, 21, 3, 12, 30 };

		// 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod 10]
		final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3, 12, 10 };
		
		// 原居留證第二碼英文字應轉換為10~33，並僅取個位數*6，這裡直接取[(個位數*6) mod 10]
		final int[] pidResidentSecondInt = {0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8, 4};
			
		str = str.toUpperCase();// 轉換大寫
		final char[] strArr = str.toCharArray();// 字串轉成char陣列
		int verifyNum = 0;

		/* 檢查身分證字號 */
		if (str.matches("[A-Z]{1}[1-2]{1}[0-9]{8}")) {
			// 第一碼
			verifyNum = verifyNum + pidIDInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// 第二~九碼
			for (int i = 1, j = 8; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// 檢查碼
			verifyNum = (10 - (verifyNum % 10)) % 10;
			
			return verifyNum == Character.digit(strArr[9], 10);
		}

			/* 檢查統一證(居留證)編號 */
			verifyNum = 0;
		if (str.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
			// 第一碼
			verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// 第二碼
			verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, strArr[1])];
			// 第三~八碼
			for (int i = 2, j = 7; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// 檢查碼
			verifyNum = (10 - (verifyNum % 10)) % 10;
			
			return verifyNum == Character.digit(strArr[9], 10);
		}
		
		return false;
	}
	
    /**
     * 字串為null或"null"皆轉換成空值.
     * @param src 要轉換的字串值
     * @return 轉換後的字串值
     */
	public static String checkNull(String src) {

      if (src == null || src.equals("null")) {
        src = "";
      }
      return src;
    }
	
	/**
     * 檢核SQL隱碼(系統預設)
     * @param str
     * @return NULL：檢核成功 不合法字元：檢核失敗
     */
    public static String checkSQLInject(String str){
        str = checkNull(str);
        str = str.toUpperCase();
        String SQLInject = null;
        String sign[] = {"'","~","!","$","%","^","&","\"\"",";","|",">","<","+","--","*","\\","[","]","{","}","=","TRUNCATE","SHUTDOWN","DROP","SELECT","DELETE","CREATE","INSERT","@@"};
        for(int i=0; i<sign.length; i++){
            if(str.indexOf(sign[i])!=-1){
                if(SQLInject==null) SQLInject="";
                if(!"".equalsIgnoreCase(SQLInject)) SQLInject+="、";
                SQLInject += sign[i];
            }
        }
        return SQLInject;
    }
    
    /**
     * 包含英文a-z A-Z, 數字0-9，之外的字元。
     * @param str
     * @return
     */
    public static boolean containNonEngNum(String str) {
    	boolean result = false;
    	for (int i = 0; i < str.length(); i++) {
    		String test = str.substring(i, i + 1);
    		if (test.matches("[^0-9a-zA-Z]")) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
    /**
     * 包含dash -, 英文a-z A-Z, 數字0-9，之外的字元。
     * @param str
     * @return
     */
    public static boolean containNonDashEngNum(String str) {
    	boolean result = false;
    	for (int i = 0; i < str.length(); i++) {
    		String test = str.substring(i, i + 1);
    		if (test.matches("[^-0-9a-zA-Z]")) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
	/**
	 * 包含dash -, 英文a-z A-Z, 數字0-9, 中文，之外的字元。
	 * @param str
	 * @return
	 */
	public static boolean containNonDashEngNumChinese(String str) {
		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			String test = str.substring(i, i + 1);
			if (test.matches("[^-0-9a-zA-Z\\u4E00-\\u9FA5]")) {
				result = true;
				break;
			}
		}
		return result;
	}
    

    // 檢查車主身分證號
	public static Map<String, Object> checkCarOwnerId(String carOwnerId) {
		String fieldName = "車主身分證號";
		return checkTwId(carOwnerId, fieldName);
	}
	
	// 檢查被保人ID
	public static Map<String, Object> checkInsuredId(String insuredId) {
		String fieldName = "被保人ID";
		return checkTwId(insuredId, fieldName);
	}
	
	
	private static Map<String, Object> checkTwId(String twId, String fieldName) {
		String validMsg = "";
		int MAX_ID_LENGTH = 10;
		if (StringUtils.isBlank(twId)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (twId.length() > MAX_ID_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonEngNum(twId)) {
				validMsg = String.format("%s需為英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(twId) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			} else if (CheckUtils.isValidID(twId) == false) {
				validMsg = String.format("無效的%s", fieldName);
			}
		}
		
		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
    
	// 檢查牌照號碼
	public static Map<String, Object> checkCarNo(String carNo, String part) {
		String fieldName = "牌照號碼" + part;
		String validMsg = "";
		int MAX_HALF_CAR_NO_LENGTH = 4;
		if (StringUtils.isBlank(carNo)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (carNo.length() > MAX_HALF_CAR_NO_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonDashEngNum(carNo)) {
				validMsg = String.format("%s需為英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(carNo) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查事故位置
	public static Map<String, Object> checkGeoLocation(String geoLocation) {
		String fieldName = "事故位置";
		String validMsg = "";
		int MAX_LOCATION_LENGTH = 100;

		if (StringUtils.isBlank(geoLocation)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (geoLocation.length() > MAX_LOCATION_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonDashEngNumChinese(geoLocation)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(geoLocation) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查事故位置
	public static Map<String, Object> checkCityAreaStreet(String city, String area, String street) {
		String fieldName = "事故位置";
		String validMsg = "";
		int MAX_LOCATION_LENGTH = 100;

		Map<String, Object> resultMap = new HashMap<>();
		if (MapUtils.getBoolean(resultMap, CheckUtils.VALID_RESULT, Boolean.TRUE)) {
			resultMap = CheckUtils.checkCity(city);
		} else {
			return resultMap;
		}
		if (MapUtils.getBoolean(resultMap, CheckUtils.VALID_RESULT, Boolean.TRUE)) {
			resultMap = CheckUtils.checkArea(area);
		} else {
			return resultMap;
		}
		if (MapUtils.getBoolean(resultMap, CheckUtils.VALID_RESULT, Boolean.TRUE)) {
			resultMap = CheckUtils.checkStreet(street);
		} else {
			return resultMap;
		}
		if (MapUtils.getBoolean(resultMap, CheckUtils.VALID_RESULT, Boolean.TRUE)) {
			int totalLength = city.length() + area.length() + street.length();
			if (totalLength > MAX_LOCATION_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			}
		} else {
			return resultMap;
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}

	// 檢查事故位置-縣市
	public static Map<String, Object> checkCity(String city) {
		String fieldName = "事故位置-縣市";
		String validMsg = "";

		if (StringUtils.isBlank(city)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (CheckUtils.containNonDashEngNumChinese(city)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(city) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查事故位置-地區
	public static Map<String, Object> checkArea(String area) {
		String fieldName = "事故位置-地區";
		String validMsg = "";

		if (StringUtils.isBlank(area)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (CheckUtils.containNonDashEngNumChinese(area)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(area) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	// 事故位置-街道
	public static Map<String, Object> checkStreet(String street) {
		String fieldName = "事故位置-街道";
		String validMsg = "";

		if (StringUtils.isBlank(street)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (CheckUtils.containNonDashEngNumChinese(street)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(street) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
    
	// 檢查車主姓名
	public static Map<String, Object> checkCarOwnerName(String oname) {
		String fieldName = "車主姓名";
		String validMsg = "";
		int MAX_CAR_OWNER_NAME_LENGTH = 100;
		
		if (StringUtils.isBlank(oname)) {
			//validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (oname.length() > MAX_CAR_OWNER_NAME_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonDashEngNumChinese(oname)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(oname) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}
		
		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查通報人姓名
	public static Map<String, Object> checkNotifierName(String name) {
		String fieldName = "通報人姓名";
		String validMsg = "";
		int MAX_NAME_LENGTH = 100;
		
		if (StringUtils.isBlank(name)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (name.length() > MAX_NAME_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonDashEngNumChinese(name)) {
				validMsg = String.format("%s需為中英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(name) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}

		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查通報人電話
	public static Map<String, Object> checkNotifierPhone(String phone) {
		String fieldName = "通報人電話";
		String validMsg = "";
		int MAX_PHONE_LENGTH = 30;
		
		if (StringUtils.isBlank(phone)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (phone.length() > MAX_PHONE_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.checkSQLInject(phone) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}
		
		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		String source = "2019-01/31";
		try {
			Date parse = sdf.parse(source );
			System.out.println(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	// 檢查事故日期
	public static Map<String, Object> checkAccidentDate(String dateStr) {
		String fieldName = "事故日期";
		String validMsg = "";
		
		if (StringUtils.isBlank(dateStr)) {
			validMsg = String.format("請輸入%s", fieldName);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			Date date = null;
			try {
				 date = sdf.parse(dateStr);
			} catch (ParseException e) {
				//to check data format and value, dont need to log exceptiong.
			}
			if (date==null) {
				validMsg = String.format("%s無效或格式錯誤", fieldName);
			}
		}
		
		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}
	
	// 檢查理賠號碼
	public static Map<String, Object> checkCaseNo(String caseNo) {
		String fieldName = "理賠號碼";
		String validMsg = "";
		int MAX_CASE_NO_LENGTH = 20;
		
		if (StringUtils.isBlank(caseNo)) {
			//validMsg = String.format("請輸入%s", fieldName);
		} else {
			if (caseNo.length() > MAX_CASE_NO_LENGTH) {
				validMsg = String.format("%s長度超過限制", fieldName);
			} else if (CheckUtils.containNonDashEngNum(caseNo)) {
				validMsg = String.format("%s需為英數字", fieldName);
			} else if (CheckUtils.checkSQLInject(caseNo) != null) {
				validMsg = String.format("%s不可包含特殊字元", fieldName);
			}
		}
		
		Boolean validResult = Boolean.TRUE;
		if (StringUtils.isNotBlank(validMsg)) {
			validResult = Boolean.FALSE;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(CheckUtils.VALID_RESULT, validResult);
		resultMap.put(CheckUtils.VALID_MSG, validMsg);
		return resultMap;
	}

}