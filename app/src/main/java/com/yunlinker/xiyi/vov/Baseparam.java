package com.yunlinker.xiyi.vov;

import org.json.JSONArray;

public class Baseparam {
	//获取最新版本
	public  static  final  String   VERSION="http://123.56.138.192:8002/appversions/get_latest_version/?os=0";
	//删除订单
	public static final String DELETE= "http://123.56.138.192:8002/orders/";
	//常见问题
	public static final String CHANGJIANWENTI = "http://123.56.138.192:8002/faq/";
	//用户协议
	public static final String XIEYI = "http://123.56.138.192:8002/protocol/";
	//获取banner
	public static final String BANNERS = "http://123.56.138.192:8002/banners/";
	// 获取验证码
	public static final String REQUEST_SMS_CODE = "http://123.56.138.192:8002/auth/request_sms_code/";
	//获取密码问题
	public static final String PASSWORDQUESTIONS = "http://123.56.138.192:8002/passwordquestions/";
	//重置密码
	public static final String RESET_PASSWORD = "http://123.56.138.192:8002/auth/reset_password/";
	// 登陆
	public static final String LOGING = "http://123.56.138.192:8002/auth/login/";
   //家居用品
	public static final String   HOME_SUPPLIES="http://123.56.138.192:8002/products/?category__name=";
	// 件洗1
	public static final String PRODUCTS_ONE = "http://123.56.138.192:8002/products/?category_id=1";
	// 件洗2
	public static final String PRODUCTS_TWO = "http://123.56.138.192:8002/products/?category_id=2";
	// 件洗3
	public static final String PRODUCTS_THREE = "http://123.56.138.192:8002/products/?category_id=3";
	// 件洗4
	public static final String PRODUCTS_four = "http://123.56.138.192:8002/products/?category__name=";
	// 袋洗
	public static final String PRODUCTS = "http://123.56.138.192:8002/products/?type=1";
	// 修改地址ַ
	public static final String UPDATE_USER_ADDRESS = "http://123.56.138.192:8002/address/update_user_address/";
	// 获取优惠劵
	public static final String DISCOUNTS = "http://123.56.138.192:8002/discounts/?user_id=";
	// 验证推荐码
	public static final String URL_INPUT_INVITE = "http://123.56.138.192:8002/auth/input_invite/";

	// 设置密码
	public static final String URL_SET_PASSWORD = "http://123.56.138.192:8002/auth/set_password";

	// 获取用户地址信息
	public static final String URL_GET_ADDRESS = "http://123.56.138.192:8002/address/get_user_address/";
	// 提交订单
		public static final String ORDERS = "http://123.56.138.192:8002/orders/";
	//小区
    public  static final String  AREAS="http://123.56.138.192:8002/areas/";
    //设置支付密码
    public static  final String  set_passwor="http://123.56.138.192:8002/auth/set_password/";
    
    //意见反馈
    public static  final String  FEEDBACKS="http://123.56.138.192:8002/feedbacks/";
	//支付订单
//    public static  final String  offer_order="http://182.92.64.159:8002/orders/{id}/offer_order/";
    
	// 支付密码
	public static final String URL_GET_offer_order = "http://123.56.138.192:8002/orders/offer_order/";
//获取订单列表
	public  static  final  String  GET_ORDERS="http://123.56.138.192:8002/orders/get_orders?status=";
	
	//修改支付密码
	public  static final  String   CHANGE_PASSWORD="http://123.56.138.192:8002/auth/change_password/";
	
    //获取单个用户信息
	public  static  final  String    USER="http://123.56.138.192:8002/auth/";
	//充值
	public static  final  String    RECHARGE="http://123.56.138.192:8002/orders/recharge/";
	//支付宝服务器异步通知页面
	public static  final  String     NOTIFY_URL="http://123.56.138.192:8002";
}
