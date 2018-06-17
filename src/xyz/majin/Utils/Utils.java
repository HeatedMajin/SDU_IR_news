package xyz.majin.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static Date Str2Date(String str, String dateFormat) {
		//yyyy年MM月dd日 HH:mm
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

		//必须捕获异常
		try {
			Date date = simpleDateFormat.parse(str);
			return date;
		} catch (ParseException px) {
			px.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串日期 转成 long类型，便于排序比较
	 * @param str
	 * @param dateFormat
	 * @return
	 * 		日期解析正确：日期的毫米
	 * 		日期解析错误：-1
	 * 		
	 */
	public static long StrDate2long(String str, String dateFormat) {
		Date date = Str2Date(str, dateFormat);
		if (null != date) {
			return date.getTime();
		}
		return -1l;
	}
}
