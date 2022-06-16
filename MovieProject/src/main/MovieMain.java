package main;
import java.util.Scanner;
import viewer.UserViewer;
import viewer.MovieViewer;
import viewer.ScoreViewer;
import viewer.TheaterViewer;
import viewer.InfoViewer;
public class MovieMain {
	public static void main(String[] args) {
		System.out.println("<WELCOME BTCP THEATER>");
		Scanner scanner = new Scanner(System.in);
		
		UserViewer userViewer = new UserViewer();
		MovieViewer movieViewer = new MovieViewer();
		ScoreViewer scoreViewer = new ScoreViewer();
		TheaterViewer theaterViewer = new TheaterViewer();
		InfoViewer infoViewer = new InfoViewer();
		
		userViewer.setScanner(scanner);
		movieViewer.setScanner(scanner);
		scoreViewer.setScanner(scanner);
		theaterViewer.setScanner(scanner);
		infoViewer.setScanner(scanner);
		
		userViewer.setMovieViewer(movieViewer);
		userViewer.setScoreViewer(scoreViewer);
		userViewer.setTheaterViewer(theaterViewer);
		userViewer.setInfoViewer(infoViewer);
		
		movieViewer.setUserViewer(userViewer);
		movieViewer.setScoreViewer(scoreViewer);
		movieViewer.setInfoViewer(infoViewer);
		
		theaterViewer.setInfoViewer(infoViewer);
		
		scoreViewer.setUserViewer(userViewer);
		scoreViewer.setMovieViewer(movieViewer);
		
		infoViewer.setMovieViewer(movieViewer);
		infoViewer.setTheaterViewer(theaterViewer);
		
		userViewer.showLogin();
		
	}

}
