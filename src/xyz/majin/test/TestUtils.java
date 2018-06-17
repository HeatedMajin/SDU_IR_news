package xyz.majin.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import xyz.majin.Utils.Utils;

public class TestUtils {
	@Test
	public void testDate() throws Exception {
//		Date date1 = new Date();
//
//		long time = date1.getTime();
//
//		System.out.println();


		//2018年05月07日 11:40
		String strDate="2018年05月07日 11:40";
		Date date = Utils.Str2Date(strDate, "yyyy年MM月dd日 HH:mm");
        if(null == date) {
        	System.out.println("错误");
        	return;
        }   
        System.out.println(date);
	}
	
	
	@Test
	public void testFileGetName() {
		File f =new File("G:\\trash\\news\\sd_news0.txt");
		String name = f.getName();
		System.out.println(name.substring(0, name.lastIndexOf(".")));
	}
}
