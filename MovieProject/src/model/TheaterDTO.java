package model;

public class TheaterDTO {
	private int id;
	private String name;
	private String area;
	private String number;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public TheaterDTO() {
		id = 0;
		name = new String();
		area = new String();
		number = new String();
	}
	
	public TheaterDTO(TheaterDTO t) {
		id = t.id;
		name = new String(t.name);
		area = new String(t.area);
		number = new String(t.number);
	}
	
	public boolean equals(Object o) {
		if(o instanceof TheaterDTO) {
			TheaterDTO t = (TheaterDTO)o;
			return id == t.id;
		}
		return false;
	}
}
