package model;
import java.util.Calendar;
public class ScoreDTO {
	private int id;
	private int writeId;
	private int movieId;
	
	private int userScore;
	private int masterScore;
	private double scoreSum;
	
	private String userBoard;
	private String masterBoard;
	
	private Calendar writtenDate;
	private Calendar updateDate;
	
	public Calendar getWritterDate() {
		return writtenDate;
	}
	public void setWrittenDate(Calendar writtenDate) {
		this.writtenDate = writtenDate;
	}
	public Calendar getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}
	public double getScoreSum() {
		return scoreSum;
	}
	public void setScoreSum(double scoreSum) {
		this.scoreSum = scoreSum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWriteId() {
		return writeId;
	}
	public void setWriteId(int writeId) {
		this.writeId = writeId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getUserScore() {
		return userScore;
	}
	
	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}
	
	public int getMasterScore() {
		return masterScore;
	}
	public void setMasterScore(int masterScore) {
		this.masterScore = masterScore;
	}
	
	public String getUserBoard() {
		return userBoard;
	}
	public void setUserBoard(String userBoard) {
		this.userBoard = userBoard;
	}
	public String getMasterBoard() {
		return masterBoard;
	}
	public void setMasterBoard(String masterBoard) {
		this.masterBoard = masterBoard;
	}
	public ScoreDTO() {
		id = 0;
		writeId = 0;
		movieId = 0;
		userScore = 0;
		masterScore = 0;
		scoreSum = 0;
		userBoard = new String();
		masterBoard = new String();
	}
	
	public ScoreDTO(ScoreDTO s) {
		id = s.id;
		writeId = s.writeId;
		movieId = s.movieId;
		userScore = s.userScore;
		masterScore = s.masterScore;
		scoreSum = s.scoreSum;
		userBoard = new String(s.userBoard);
		masterBoard = new String(s.masterBoard);
		writtenDate = Calendar.getInstance();
		writtenDate.setTime(s.writtenDate.getTime());
		updateDate = Calendar.getInstance();
		updateDate.setTime(s.updateDate.getTime());
	}
	
	public boolean equals(Object o) {
		if(o instanceof ScoreDTO) {
			ScoreDTO s = (ScoreDTO)o;
			return id == s.id;
		}
		return false;
	}
}
