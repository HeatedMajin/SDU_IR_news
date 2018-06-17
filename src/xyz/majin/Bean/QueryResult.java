package xyz.majin.Bean;

import java.util.List;

public class QueryResult {
	int totalSize;
	List<DocBean> context;
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public List<DocBean> getContext() {
		return context;
	}
	public void setContext(List<DocBean> context) {
		this.context = context;
	}
}
