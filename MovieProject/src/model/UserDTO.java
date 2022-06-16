package model;

public class UserDTO {	
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int gradeNum;
	private String gradeName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public UserDTO() {
		id = 0;
		username = new String();
		password = new String();
		nickname = new String();
		gradeNum = 0;
		gradeName = new String();
	}
	
	public UserDTO(UserDTO u) {
		id = u.id;
		username = new String(u.username);
		password = new String(u.password);
		nickname = new String(u.nickname);
		gradeNum = u.gradeNum;
		gradeName = new String(u.gradeName);
	}
	
	public boolean equals(Object o) {
		if(o instanceof UserDTO) {
			UserDTO u = (UserDTO) o;
				return id == u.id;
		}
		return false;
	}
	
}
