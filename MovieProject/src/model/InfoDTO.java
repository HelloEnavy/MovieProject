package model;

public class InfoDTO {
	private int id;
	private int movieId;
	private int theaterId;
	private int time;
	private String startTime;

		
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getTheaterId() {
		return theaterId;
	}
	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public InfoDTO() {
		id = 0;
		movieId = 0;
		theaterId = 0;
		time = 0;
		startTime = new String();

	}
	
	public InfoDTO(InfoDTO i) {
		id = i.id;
		movieId = i.movieId;
		theaterId = i.theaterId;
		time = i.time;
		startTime = new String(i.startTime);

	}
	
	public boolean equals(Object o) {
		if(o instanceof InfoDTO) {
			InfoDTO i = (InfoDTO)o;
			return id == i.id;
		}
		
		return false;
	}
}
