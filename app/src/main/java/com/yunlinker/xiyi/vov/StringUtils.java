package com.yunlinker.xiyi.vov;

import java.io.File;
import java.io.FileWriter;



import android.os.Environment;

public class StringUtils {
	public static void writeLogInfo(String errorStr) {
		StringUtils.checkOrCreateFile("XiYi");
		try {
			FileWriter fw = null;
			fw = new FileWriter("/sdcard/XiYi/"+System.currentTimeMillis()+"-Error.txt");
			fw.flush();
			fw.write(errorStr);
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	/**
	 * 
	 * @param yourPath 要检查或者创建的文件夹或者文件，基于sd卡目录
	 */
	public static void checkOrCreateFile(String yourPath) {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			File sd = Environment.getExternalStorageDirectory();
			String path = sd.getPath() + "/" + yourPath;
			File file = new File(path);
			if (!file.exists())
				file.mkdir();
		}
	}
}
