package xyz.majin.DAO;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import xyz.majin.Bean.DocBean;
import xyz.majin.Bean.QueryInfo;
import xyz.majin.Bean.QueryResult;
import xyz.majin.utils.Utils;

/**
 * 使用SolrJ实现DAO访问索引库
 * 
 * @author majin
 *
 */
public class SolrDao {
	/**
	 * 根据关键字，获取文档
	 * 
	 * @param wd
	 * @return
	 * @throws SolrServerException
	 */
	public QueryResult getDocs(ServletContext context,QueryInfo qInfo) throws Exception {

		LinkedList<DocBean> docBeans = new LinkedList<DocBean>();

		/** 连接solr服务器 **/
		String baseURL = Utils.getSolrBaseURL(context);
		SolrServer server = new HttpSolrServer(baseURL);
		
		/** 查询需要的参数 **/
		SolrQuery params = new SolrQuery();

		/** 获取关键字 **/
		String wd = qInfo.getWd();
		int curPage = qInfo.getCurrentPage();
		int pageSize = qInfo.getPageSize();

		/** 设置查询关键字 **/
		params.set("q", "news_title:" + wd + " OR news_content:" + wd);

		/** 设置分页 **/
		params.setStart((curPage - 1) * pageSize);
		params.setRows(pageSize);

		/** 获取排序的类型 **/
		String sortType = qInfo.getSortType();

		if (sortType.equals("time")) {// 按时间 由新到旧 毫秒由大到小
			params.addSort("news_date_l", ORDER.desc);
		} else if (sortType.equals("click")) {// 按点击数 从大到小
			params.addSort("news_lick_times_i", ORDER.desc);
		} else {
			//不设置排序，使用默认的DTF排序
		}

		/** 设置高亮 **/
		// 打开高亮
		params.setHighlight(true);
		// 高亮的域
		params.set("hl.fl", "news_title,news_content");
		// 高亮的前后缀
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");

		/** 执行查询，获取response **/
		QueryResponse response = null;
		response = server.query(params);

		/** 获取文档集 **/
		SolrDocumentList documentList = response.getResults();
		/** 找到的文档总数 **/
		long numFound = documentList.getNumFound();

		/** 获取高亮 **/
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// |--K：doc_ID v:map
		// ---------------|--K:hl_field v:hl_field_content

		for (SolrDocument doc : documentList) {

			// id、data、url不需要高亮
			Object id = doc.getFieldValue("id");

			/** 对日期进行处理： 将long格式的ms，转成日期字符串yyyy年MM月dd日 HH:mm **/
			Object date_l = doc.getFieldValue("news_date_l");
			String date = Utils.long2localDate(date_l);

			Object url = doc.getFieldValue("news_url");
			Object clickTimes = doc.getFieldValue("news_lick_times_i");

			// title和content需要高亮
			Map<String, List<String>> map = highlighting.get(id);
			List<String> title_list = map.get("news_title");
			String title = "";
			if (null != title_list) {
				title = title_list.get(0);
			} else {
				title = doc.getFieldValue("news_title").toString();
			}
			/** 对content进行压缩 **/
			String content = map.get("news_content").get(0);
			// String exted_content = Utils.extractContent(content);

			docBeans.add(new DocBean(id.toString(), title, date, content, url.toString(), clickTimes.toString()));
		}

		QueryResult result = new QueryResult();
		result.setContext(docBeans);

		result.setTotalSize((int) numFound);

		return result;
	}
}
