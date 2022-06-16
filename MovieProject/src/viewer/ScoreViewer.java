package viewer;
import controller.ScoreController;
import viewer.UserViewer;
import model.ScoreDTO;
import model.UserDTO;
import util.ScannerUtil;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ScoreViewer {
	private ScoreController scoreController;
	private MovieViewer movieViewer;
	private UserViewer userViewer;
	private UserDTO login;
	private Scanner scanner;
	private final String FORMAT = "yy.MM.dd HH:mm:ss";

	
	public ScoreViewer() {
		scoreController = new ScoreController();
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void setLogin(UserDTO login) {
		this.login = login;
	}
	
	public void setUserViewer(UserViewer userViewer) {
		this.userViewer = userViewer;
	}
	
	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer = movieViewer;
	}
	
	//해당 영화의 관람평 목록
	public void printList(int movieId) {
		while(true) {	
			
		ArrayList<ScoreDTO> list = scoreController.selectAll(movieId);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		
		if(list.isEmpty()) {
			System.out.println("-------------------------------------------------------------");
			System.out.println("등록된 감상평이 없습니다.");
			System.out.println("-------------------------------------------------------------");
			
			//관리자에게는 감상평 등록권한을 주지 않았습니다
			if(login.getGradeNum() !=3) {			
				String message = new String("1.새 감상평 등록 2.뒤로가기(영화상세정보)");
				int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2) ;
					if(userChoice == 1) {
						scoreRegister(movieId);
					} else if(userChoice == 2) {
						break;
					}
			} else {
				String message = new String("뒤로가시려면 0번을 눌러주세요.(영화상세정보로 이동)");
				int userChoice = ScannerUtil.nextInt(scanner, message, 0, 0) ;
				break;
			}
				
		} else {
				
			
				System.out.println("-------------------------------------------------------------");
				System.out.printf("<영화평론가 감상평> 평점:%.1f점/5점\n", masterSum(movieId));
				System.out.println("-------------------------------------------------------------");
				for(ScoreDTO s : list){
					if(s.getWriteId()== 1 || s.getWriteId()== 2 || s.getWriteId()== 3) {
						System.out.printf("%d.[작성자:%s]-\"%s\"(%d점)(작성일:%s)\n", s.getId(), userViewer.printUserId(s.getWriteId()), s.getMasterBoard(), s.getMasterScore(), sdf.format(s.getWritterDate().getTime()));				
					}			
				}
				System.out.println("-------------------------------------------------------------");			
				System.out.printf("<일반관람객 감상평> 평점:%.1f점/5점\n", userSum(movieId));		
				System.out.println("-------------------------------------------------------------");			

				for(ScoreDTO s : list) {
					if (!(s.getWriteId()== 1 || s.getWriteId()== 2 || s.getWriteId()== 3)) {
						System.out.printf("%d.[작성자:%s]-\"%s\"(%d점)(작성일:%s)\n", s.getId(), userViewer.printUserId(s.getWriteId()), s.getUserBoard(), s.getUserScore(), sdf.format(s.getWritterDate().getTime()));				
						
					}	
				}
			
				System.out.println("-------------------------------------------------------------");
				
				if(login.getGradeNum()!=3) {
					String message = new String("1.새 감상평 등록 2.감상평 수정 3.감상평 삭제 4.뒤로가기(영화상세정보)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
					
					if(userChoice == 1) {
						scoreRegister(movieId);
					} else if(userChoice == 2) {
						// 수정
						message = new String("수정할 감상평 번호를 입력해 주세요.");
						int number = ScannerUtil.nextInt(scanner, message);
						scoreUpdate(number, movieId);
						
					} else if(userChoice == 3) {
						// 삭제
						message = new String("삭제할 감상평 번호를 입력해 주세요.");
						int number = ScannerUtil.nextInt(scanner, message);
						scoreDelete(number, movieId);
					} else if(userChoice == 4) {
						break;
					}
				}
			} 		
		}
	}
	
	// 새 감상평 등록
	private void scoreRegister(int movieId) {
		ScoreDTO s = new ScoreDTO();

		String message = new String("평점을 입력해 주세요.(0~5점)");
		int score = ScannerUtil.nextInt(scanner, message, 0, 5);
		
		message = new String("감상평을 입력해 주세요.");
		String board = ScannerUtil.nextLine(scanner, message);
		
		// 작성자가 일반관람객일경우
		if(login.getGradeNum() == 1) {
			s.setUserScore(score);
			s.setUserBoard(board);
		} 
		// 작성자가 영화평론가일경우
		else if(login.getGradeNum() == 2) {
			s.setMasterScore(score);
			s.setMasterBoard(board);
		}
		
		s.setMovieId(movieId);
		s.setWriteId(login.getId());
		
		message = new String("감상평을 등록하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			scoreController.insert(s);
			System.out.println("정상적으로 감상평이 등록되었습니다^^");
		} else {
			System.out.println("감상평 등록이 취소되었습니다.");
		}
	}
	
	
	//관람평 수정(본인 확인)
	private void scoreUpdate(int id, int movieId) {
		ScoreDTO s = scoreController.selectOne(id, movieId);
		
		if(s == null) {
			System.out.println("해당 번호로는 감상평이 조회되지 않습니다.");
		}
			else {
			
			if(!(login.getId() == s.getWriteId())) {
				System.out.println("작성자 본인만 수정하실 수 있습니다.");
			} else {
				String message = new String("새로운 평점을 입력해 주세요.(0~5점)");
				int score = ScannerUtil.nextInt(scanner, message, 0, 5);
				
				message = new String("새로운 감상평을 입력해 주세요."); 
				String board = ScannerUtil.nextLine(scanner, message);
				
				// 작성자가 일반관람객일경우
				if(login.getGradeNum() == 1) {
					s.setUserScore(score);
					s.setUserBoard(board);
				} 
				// 작성자가 영화평론가일경우
				else if(login.getGradeNum() == 2) {
					s.setMasterScore(score);
					s.setMasterBoard(board);
				}
				
				message = new String("감상평을 수정완료 하시겠습니까? Y/N");
				String answer = ScannerUtil.nextLine(scanner, message);
				
				if(answer.equalsIgnoreCase("Y")) {
					message = new String("본인확인을 위해 비밀번호를 입력해 주세요.");
					String password = ScannerUtil.nextLine(scanner, message);
					if(login.getPassword().equals(password)) {
						scoreController.update(s);
						System.out.println("정상적으로 감상평이 수정되었습니다^^");
					} else {
						System.out.println("본인확인 실패했습니다.");
					}
				} else {
					System.out.println("감상평 수정이 취소되었습니다.");
				}
			}
		}
	}
	
	//관람평 삭제(본인 확인)
	private void scoreDelete(int id, int movieId) {
		ScoreDTO s = scoreController.selectOne(id, movieId);
		
		if(s==null) {
			System.out.println("해당 번호로는 감상평이 조회되지 않습니다.");
		}
		
		else {
		
			if(!(login.getId() == s.getWriteId())) {
				System.out.println("작성자 본인만 삭제하실 수 있습니다.");
			} else {
				String message = new String("정말로 감상평을 삭제하시겠습니까? Y/N");
				String answer = ScannerUtil.nextLine(scanner, message);
				
				if(answer.equalsIgnoreCase("Y")) {
					message = new String("본인확인을 위해 비밀번호를 입력해 주세요.");
					String password = ScannerUtil.nextLine(scanner, message);
					if(login.getPassword().equals(password)) {
						scoreController.delete(id);
						System.out.println("정상적으로 감상평이 삭제되었습니다^^");
					} else {
						System.out.println("본인확인 실패했습니다.");
					}
					
				} else {
					System.out.println("감상평 삭제가 취소되었습니다.");
				}
			}
		}
	}
	
	// 전체 평점 구하는 메소드
	public double sum(int movieId) {		
		int score = 0;
		double average = 0;
		ArrayList<ScoreDTO> list = scoreController.selectAll(movieId);
		
		if(list.isEmpty()) {
			return 0;
		} else {
		
		for(int i=0 ; i<list.size(); i++) {
			ScoreDTO s = list.get(i);
			score = score + (s.getUserScore() + s.getMasterScore());
		}
		average = (double)score/list.size();
		return average;
		}
	}
	
	// 일반관람객 평점
	private double userSum(int movieId) {
		int score = 0;
		double average = 0;
		int count = 0;
		ArrayList<ScoreDTO> list = scoreController.selectAll(movieId);
		for(int i=0 ; i<list.size(); i++) {
			ScoreDTO s = list.get(i);
			if(!(s.getWriteId()== 1 || s.getWriteId()== 2 || s.getWriteId()== 3)) {
				score = score + s.getUserScore();
				count++;
			}
		}		
		if(count == 0) {
			return 0;
		} else {
			average = (double)score / count;
			return average;
		}
	}
	
	// 영화평론가 평점
	private double masterSum(int movieId) {
		int score = 0;
		double average = 0;
		int count = 0;
		ArrayList<ScoreDTO> list = scoreController.selectAll(movieId);
		for(int i=0 ; i<list.size(); i++) {
			ScoreDTO s = list.get(i);
			if(s.getWriteId()== 1 || s.getWriteId()== 2 || s.getWriteId()== 3) {
				score = score + s.getMasterScore();
				count++;
			}
		}
		if(count == 0) {
			return 0;
		} else {
			average = (double)score / count;
			return average;
		}
	}
	
	public void deleteByMovie(int movieId) {
		System.out.println("해당 영화의 감상평이 삭제되었습니다.");
		scoreController.deleteByMovieId(movieId);
	}
	
	public void deleteByUser(int writeId) {
		System.out.println("회원님의 감상평이 삭제되었습니다.");
		scoreController.deleteByUserId(writeId);
	}
	
	//평점 작성한 사람 수 
	public int person(int movieId) {
		return scoreController.scorePerson(movieId);
	}

}
