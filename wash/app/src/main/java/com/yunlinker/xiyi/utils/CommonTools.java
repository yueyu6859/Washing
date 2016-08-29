package com.yunlinker.xiyi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class CommonTools {
	/**
	 * 手机号码正则表达
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern pattern = Pattern.compile("[1][358]\\d{9}");
		Matcher matcher = pattern.matcher(mobiles);

		return matcher.matches();
	}
}
