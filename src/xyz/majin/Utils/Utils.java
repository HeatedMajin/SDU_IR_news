package xyz.majin.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static Date Str2Date(String str, String dateFormat) {
		//yyyy��MM��dd�� HH:mm
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

		//���벶���쳣
		try {
			Date date = simpleDateFormat.parse(str);
			return date;
		} catch (ParseException px) {
			px.printStackTrace();
		}
		return null;
	}

	/**
	 * �ַ������� ת�� long���ͣ���������Ƚ�
	 * @param str
	 * @param dateFormat
	 * @return
	 * 		���ڽ�����ȷ�����ڵĺ���
	 * 		���ڽ�������-1
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
