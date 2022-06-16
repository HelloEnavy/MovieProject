package model;

public class MovieDTO {
	private int id;
	private String title;
	private String story;
	private int gradeNum;
	private String gradeName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	
	public int getGradeNum() {
		return gradeNum;
	}
	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public MovieDTO() {
		id = 0;
		title = new String();
		story = new String();
		gradeNum = 0;
		gradeName = new String();
	}
	
	public MovieDTO(MovieDTO m) {
		id = m.id;
		title = new String(m.title);
		story = new String(m.story);
		gradeNum = m.gradeNum;
		gradeName = m.gradeName;
	}
	
	public boolean equals(Object o) {
		if(o instanceof MovieDTO) {
			MovieDTO m = (MovieDTO)o;
			return id == m.id;
		}
		return false;
	}
	
}
