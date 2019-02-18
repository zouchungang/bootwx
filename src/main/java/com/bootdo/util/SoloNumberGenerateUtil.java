package com.bootdo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获得各种随机编号
 */
public class SoloNumberGenerateUtil {

	/**
	 * 获得当品订单编号
	 * @return
	 */
	public static String getPawnOrderNum() {
		String result = "";
		//格式化当前时间
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = sfDate.format(new Date());
		//得到17位时间如：20170411094039080

		//为了防止高并发重复,再获取3个随机数
		String random = getRandom620(3);
		String random1 = getRandom620(3);

		//PO(pawn order)+前三位随机数+时间戳+后三位随机数
		result="PO"+random+strDate+random1;

		//最后得到20位订单编号。
		return result;
	}

	/**
	 * 获得支付交易编号
	 * @return
	 */
	public static String getTradeNum() {
		String result = "";
		//格式化当前时间
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = sfDate.format(new Date());
		//得到17位时间如：20170411094039080

		//为了防止高并发重复,再获取3个随机数
		String random = getRandom620(3);
		String random1 = getRandom620(3);

		//TN(trade number)+前三位随机数+时间戳+后三位随机数
		result="TN"+random+strDate+random1;

		//最后得到20位订单编号。
		return result;
	}


	/**
	 * 获取随机字母组合
	 *
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String getRandomChar(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str += (char) (65 + random.nextInt(26));// 取得大写字母
		}
		return str.toUpperCase();
	}

	/**
	 * 获取6-10 的随机位数数字
	 * @param length	想要生成的长度
	 * @return result
	 */
	private static String getRandom620(Integer length) {
		String result = "";
		Random rand = new Random();
		int n = 20;
		if (null != length && length > 0) {
			n = length;
		}
		int randInt = 0;
		for (int i = 0; i < n; i++) {
			randInt = rand.nextInt(10);
			result += randInt;
		}
		return result;
	}

	/**
	 * 获得商品订单编号
	 * @return
	 */
	public static String getGoodsOrderNum() {
		String result = "";
		//格式化当前时间
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = sfDate.format(new Date());
		//得到17位时间如：20170411094039080

		//为了防止高并发重复,再获取3个随机数
		String random = getRandom620(3);
		String random1 = getRandom620(3);

		//PO(pawn order)+前三位随机数+时间戳+后三位随机数
		result="GO"+random+strDate+random1;

		//最后得到20位订单编号。
		return result;
	}
}