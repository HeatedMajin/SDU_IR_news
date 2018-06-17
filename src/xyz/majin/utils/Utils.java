package xyz.majin.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class Utils {

	public static String long2localDate(Object date_l) {
		long ms_l = Long.parseLong(date_l.toString());
		Date date = new Date(ms_l);

		// Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
		String now = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date);

		return now;
	}

	public static String extractContent(String content) {
		Pattern pattern = Pattern.compile("<span style\\=\"color:red;\">[^<]*</span>");

		Pattern pattern1 = Pattern
				.compile("[\\u4e00-\\u9fa5]{0,50}<span style\\=\"color:red;\">[^<]*</span>[\\u4e00-\\u9fa5]{0,50}");

		Pattern pattern2 = Pattern
				.compile("[\\u4e00-\\u9fa5]{0,25}<span style\\=\"color:red;\">[^<]*</span>[\\u4e00-\\u9fa5]{0,25}");

		Matcher matcher = pattern.matcher(content);

		int count = 0;
		while (matcher.find()) {
			count++;
		}

		if (count == 0) {

			if (content.length() > 100) {
				return content.substring(0, 100);
			}
			return content;

		} else if (count == 1) {

			Matcher matcher1 = pattern1.matcher(content);
			String res = "";
			while (matcher1.find()) {
				res = res + "..." + matcher1.group();
			}
			res = res + "...";

			return res;

		} else {
			Matcher matcher2 = pattern2.matcher(content);

			String res = "";
			while (matcher2.find()) {
				res = res + "..." + matcher2.group();
			}
			res = res + "...";
			return res;
		}
	}

	public static <T> T request2bean(HttpServletRequest request, Class<T> beanclass) {
		try {
			Map map = request.getParameterMap();
			T bean = beanclass.newInstance();
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(Class arg0, Object value) {
					if (value == null || !(value instanceof String)) {
						return null;
					}
					String str = (String) value;
					if (str.trim().equals("")) {
						return null;
					}

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						return dateFormat.parse(str);
					} catch (ParseException e) {
						e.printStackTrace();
						return null;
					}

				}
			}, Date.class);
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String getSolrBaseURL(ServletContext context) {
		FileInputStream fileInputStream = null;
		try {
			Properties pro = new Properties();
			fileInputStream = new FileInputStream(context.getRealPath("/WEB-INF/classes/conf.properties"));
			pro.load(fileInputStream);
			return pro.getProperty("SolrBaseURL");
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (null != fileInputStream) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return "";

	}

}
