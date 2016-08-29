package com.yunlinker.xiyi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.ui.HomeActivity;

//返序列化集合
public class ArrayOppositeser {
   
	  public  void  ArrayOppositeser(){
		  File f=new File("/data/data/com.yunlinker.xiyixi/");  
		  String name ="basket.ser";
			 String fileName = "/data/data/com.yunlinker.xiyixi/" +name;
		       ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(new FileInputStream(fileName));
                   HomeActivity.list=(ArrayList<BasketBean>) ois.readObject();
					Log.d("地址信息","22256"+HomeActivity.list.size());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	  }
	
}
