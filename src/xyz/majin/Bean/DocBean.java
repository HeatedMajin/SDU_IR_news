package xyz.majin.Bean;

//文档对象
public class DocBean {
	public String id;
	public String title;
	public String date;
	public String content;
	public String url;
	public String clickTimes;
	public DocBean() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(String clickTimes) {
		this.clickTimes = clickTimes;
	}

	public DocBean(String id, String title, String date, String content, String url,String clickTimes) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.content = content;
		this.url = url;
		this.clickTimes = clickTimes;
	}
	
	
}
