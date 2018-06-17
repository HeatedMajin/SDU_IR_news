package xyz.majin.News;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import xyz.majin.Utils.Utils;

/**
 * 使用Solr管理索引库
 * 使用SolrJ为索引库创建索引
 * @author majin
 *
 */
public class IndexCreate {
	private static final String docsBase = "G:\\trash\\news";
	private static SolrServer server;
	static {
		String baseURL = "http://localhost/solr/collection2";
		server = new HttpSolrServer(baseURL);
	}

	/**
	 * 主程序
	 * @param args
	 */
	public static void main(String[] args) {
		File docsBaseFolder = new File(docsBase);

		List<SolrInputDocument> docs = new LinkedList<>();
		for (File doc : docsBaseFolder.listFiles()) {
			SolrInputDocument document = createDoc(doc);
			if (null != document) {
				docs.add(document);
			}
		}

		try {
			server.add(docs);
			server.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	private static SolrInputDocument createDoc(File doc) {

		//id使用文件的文件名
		String name = doc.getName();
		String id = name.substring(0, name.lastIndexOf("."));

		//新闻的标题、日期、URL和内容
		String title = "";
		long date_l = 0;
		String url = "";
		String content = "";
		int clickTimes = 0;

		//从文件中读取上面四条信息
		FileInputStream fileInputStream = null;
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;

		String str = null;
		int index = 0;
		boolean stored = true;

		try {
			fileInputStream = new FileInputStream(doc);

			streamReader = new InputStreamReader(fileInputStream, "utf-8");

			bufferedReader = new BufferedReader(streamReader);

			while ((str = bufferedReader.readLine()) != null) {
				switch (index) {
				case 0://第一行标题
					title = str;
					break;
				case 1://第二行日期
					date_l = Utils.StrDate2long(str, "yyyy年MM月dd日 HH:mm");
					//日期解析错误不进行保存
					if (-1 == date_l) {
						stored = false;
					}
					break;
				case 2://第三行点击数
					clickTimes = Integer.parseInt(str);
					break;
				case 3://第四行url
					url = str;
					break;
				case 4://第五行正文
					content = str;
					break;
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
				}
				if (null != streamReader) {
					streamReader.close();
				}
				if (null != fileInputStream) {
					fileInputStream.close();
				}
			} catch (Exception e) {
			}

		}
		if (stored) {
			//创建doc对象
			SolrInputDocument solrDoc = new SolrInputDocument();
			solrDoc.addField("id", id);
			solrDoc.addField("news_title", title);
			solrDoc.addField("news_date_l", date_l);
			solrDoc.addField("news_lick_times_i", clickTimes);
			solrDoc.addField("news_url", url);
			solrDoc.addField("news_content", content);

			return solrDoc;
		}
		return null;

	}

}
