package xyz.majin.Service;

import javax.servlet.ServletContext;

import xyz.majin.Bean.PageBean;
import xyz.majin.Bean.QueryInfo;
import xyz.majin.Bean.QueryResult;
import xyz.majin.DAO.SolrDao;

public class QueryService {
	/**
	 * 获取要显示页面的基本信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public PageBean getDocs(ServletContext context, QueryInfo info) throws Exception {
		SolrDao daoImpl = new SolrDao();
		
		// 从索引库中查询 内容 和 总数据量
		QueryResult result = daoImpl.getDocs(context, info);

		PageBean pageBean = new PageBean();
		pageBean.setContext(result.getContext());
		pageBean.setCurrentPage(info.getCurrentPage());
		pageBean.setTotalSize(result.getTotalSize());
		pageBean.setPageSize(info.getPageSize());
		pageBean.setSortType(info.getSortType());
		
		return pageBean;
	}
}
