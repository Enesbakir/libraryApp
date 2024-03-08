package Models;

public class Books {
	private String id;
	private String author_id;
	private String name;
	private String publishDate;
	public Books(String id, String author_id, String name, String publishDate) {
		super();
		this.id = id;
		this.author_id = author_id;
		this.name = name;
		this.publishDate = publishDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	
}
