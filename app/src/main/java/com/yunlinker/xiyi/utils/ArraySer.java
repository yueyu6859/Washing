package com.yunlinker.xiyi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//序列化洗衣篮集合
public class ArraySer {
	 public void ser(Object object)throws Exception{
//			序列化
			try {
				File file = new File("/data/data/com.yunlinker.xiyixi/");
				String name ="basket.ser";
				file.mkdirs();// 创建文件夹
				String fileName = "/data/data/com.yunlinker.xiyixi/" +name;
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
				oos.writeObject(object);
				oos.flush();
	            oos.close();
	            
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 }
	 
	 
	 
	 
}
