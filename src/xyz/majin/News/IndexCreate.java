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
 * ʹ��Solr����������
 * ʹ��SolrJΪ�����ⴴ������
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
	 * ������
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

		//idʹ���ļ����ļ���
		String name = doc.getName();
		String id = name.substring(0, name.lastIndexOf("."));

		//���ŵı��⡢���ڡ�URL������
		String title = "";
		long date_l = 0;
		String url = "";
		String content = "";
		int clickTimes = 0;

		//���ļ��ж�ȡ����������Ϣ
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
				case 0://��һ�б���
					title = str;
					break;
				case 1://�ڶ�������
					date_l = Utils.StrDate2long(str, "yyyy��MM��dd�� HH:mm");
					//���ڽ������󲻽��б���
					if (-1 == date_l) {
						stored = false;
					}
					break;
				case 2://�����е����
					clickTimes = Integer.parseInt(str);
					break;
				case 3://������url
					url = str;
					break;
				case 4://����������
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
			//����doc����
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
