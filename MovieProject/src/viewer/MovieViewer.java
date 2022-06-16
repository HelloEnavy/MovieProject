package viewer;
import util.ScannerUtil;
import controller.MovieController;
import model.UserDTO;
import model.MovieDTO;
import java.util.Scanner;
import java.util.ArrayList;
import viewer.ScoreViewer;

public class MovieViewer {
	private MovieController movieController;
	private ScoreViewer scoreViewer;
	private UserViewer userViewer;
	private InfoViewer infoViewer;
	private UserDTO login;
	private Scanner scanner;
	
	
	public MovieViewer() {
		movieController = new MovieController();
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void setLogin(UserDTO login) {
		this.login = login;
	}
	
	public void setScoreViewer(ScoreViewer scoreViewer) {
		this.scoreViewer = scoreViewer;
	}
	
	public void setUserViewer(UserViewer userViewer) {
		this.userViewer = userViewer;
	}
	
	public void setInfoViewer(InfoViewer infoViewer) {
		this.infoViewer = infoViewer;
	}
	
	
	// 영화 목록 메서드
	public void showMenu() {
		while(true) {
			ArrayList<MovieDTO> list = movieController.selectAll();			
			
			if(list.isEmpty()) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("현재 등록된 영화가 없습니다.");
				System.out.println("-------------------------------------------------------------");
				
				if(login.getGradeNum() == 3) {
					String message = new String("1.개봉영화등록 2.뒤로가기(메인메뉴)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
					
					if(userChoice == 1) {
						movieRegister();
					} else if(userChoice == 2) {
						break;
					}
					
				} else {
				
					String message = new String("뒤로가시려면 0번을 눌러주세요.");
					int userChoice = ScannerUtil.nextInt(scanner, message, 0, 0);		
					break;
				
				}
			} else {	 
				System.out.println("-------------------------------------------------------------");
				for(MovieDTO m : list) {
					System.out.printf("%d번. %s(%s)\n", m.getId(), m.getTitle(), m.getGradeName());
				}
				System.out.println("-------------------------------------------------------------");
				
				if(login.getGradeNum() == 3) {
									
					String message = new String("1.개봉영화등록 2.영화정보보기 3.뒤로가기(메인메뉴)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
					if(userChoice == 1) {
						movieRegister();
					} else if (userChoice == 2) {
						message = new String("상세보기할 영화번호를 입력해 주세요.(뒤로가기 0번)");
						int userChoice1 = ScannerUtil.nextInt(scanner, message);
						if(userChoice1 != 0) {
							movieOne(userChoice1);
						}
					} else if(userChoice == 3) {
						break;
					} 
				
				} else {
				
					String message = new String("상세보기할 영화번호를 입력해 주세요.(뒤로가기 0번)");
					int userChoice = ScannerUtil.nextInt(scanner, message);
					
					if(userChoice != 0) {
						movieOne(userChoice);
					} else if (userChoice == 0) {
						break;
					}
				}
			}
		}
	}
	// 영화 상세보기 
	private void movieOne(int id) {
		MovieDTO m = movieController.selectOne(id);
		
		if(m==null) {
			System.out.println("해당 영화번호로는 조회되지 않습니다.");
		} else {
			while(true) {
				m = movieController.selectOne(id);
			
				System.out.println("-------------------------------------------------------------");
				System.out.println("<영화 상세 정보>");
				System.out.println("-------------------------------------------------------------");
				System.out.println("영화제목 : " + m.getTitle());
				System.out.println("영화등급 : " + m.getGradeName());
				System.out.println("영화줄거리 : " + m.getStory());
				// scoreViewers 클래스에서 추가
				System.out.printf("영화총평점 : %.1f점/5점(%d명)\n", scoreViewer.sum(id), scoreViewer.person(id));
				System.out.println("-------------------------------------------------------------");
				System.out.println("<현재 해당 영화를 상영하고 있는 극장 LIST>");
				System.out.println("-------------------------------------------------------------");
				printTheaterName(id); 
				System.out.println("-------------------------------------------------------------");
					
				// 관리자 계정일 시 영화정보 수정 삭제 권리 부여
				if(login.getGradeNum() == 3) {
					String message = new String("1.감상평보기 2.영화수정 3.영화삭제 4.뒤로가기(영화목록)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
					
					if(userChoice == 1) {
						//관람평 상세보기 메서드
						scoreViewer.printList(id);
					} else if(userChoice == 2) {
						moiveUpdate(id);
					} else if(userChoice == 3) {
						movieDelete(id);
						break;
					} else if(userChoice == 4) {
						break;
					}
				} 
				// 일반관람객, 영화평론가 계정일 시 감상평 보기 권한만 부여
				else {
					String message = new String("1.감상평보기 2.뒤로가기(영화목록)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
					
					if(userChoice == 1) {
						//관람평보기 메소드
						scoreViewer.printList(id);
					} else if (userChoice == 2) {
						break;
					}
				}
			}
		}
	}
	
	// 영화 정보 수정
	public void moiveUpdate(int id) {
		MovieDTO m = movieController.selectOne(id);
		System.out.println("-------------------------------------------------------------");
		
		String message = new String("새로운 영화 제목을 입력해 주세요.");
		m.setTitle(ScannerUtil.nextLine(scanner, message));
		
		message = new String("새로운 영화 줄거리를 입력해 주세요.");
		m.setStory(ScannerUtil.nextLine(scanner, message));
		
		System.out.println("새로운 영화 등급을 선택해 주세요.");
		message = new String("[1.전체관람가 2.12세관람가 3.15세관람가 4.18세관람가]");
		int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
		if(userChoice == 1) {
			m.setGradeName("전체관람가");
		} else if(userChoice == 2) {
			m.setGradeName("12세관람가");
		} else if(userChoice == 3) {
			m.setGradeName("15세관람가");
		} else if(userChoice == 4) {
			m.setGradeName("18세관람가");
		}
		System.out.println("-------------------------------------------------------------");
		
		message = new String("영화정보를 수정완료하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			message = new String("관리자님의 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(password.equals(login.getPassword())) {
				System.out.println("정상적으로 영화정보가 수정되었습니다^^");
				movieController.update(m);
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			}
		} else { 
			System.out.println("수정이 취소되었습니다.");
		}
	}
	
	// 영화 정보 삭제
	private void movieDelete(int id) {
		String message = new String("해당 영화를 정말 삭제하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			message = new String("관리자님의 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(password.equals(login.getPassword())) {
				movieController.delete(id);
				scoreViewer.deleteByMovie(id);
				infoViewer.deleteByMovie(id);
				System.out.println("정상적으로 해당 영화가 삭제되었습니다^^");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			}
		} else {
			System.out.println("삭제가 취소되었습니다.");
		}
	}
	
	//영화 등록 메서드
	private void movieRegister() {
		MovieDTO m = new MovieDTO();
		
		String message = new String("영화 제목을 입력해 주세요.");
		m.setTitle(ScannerUtil.nextLine(scanner, message));
		
		message = new String("영화 줄거리를 입력해 주세요.");
		m.setStory(ScannerUtil.nextLine(scanner, message));
		
		System.out.println("영화 등급을 선택해 주세요.");
		message = new String("[1.전체관람가 2.12세관람가 3.15세관람가 4.18세관람가]");
		int number = ScannerUtil.nextInt(scanner, message, 1, 4);
		m.setGradeNum(number);
		
		message = new String("새로운 영화를 등록하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		if(answer.equalsIgnoreCase("Y")) {
			System.out.println("정상적으로 영화가 등록되었습니다^^");
			movieController.insert(m);
		} else {
			System.out.println("등록이 취소되었습니다.");
		}
		
	}
	
	//상영정보등록시 등록된 영화리스트 보여주는 메소드(InfoViewer에서 사용)
	public void movieListToInfo() {
		ArrayList<MovieDTO> list = movieController.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("-------------------------------------------------------------");
			System.out.println("현재 등록된 영화가 없습니다.");
			System.out.println("-------------------------------------------------------------");
		} else {	 
			System.out.println("-------------------------------------------------------------");
			for(MovieDTO m : list) {
				System.out.printf("%d번. %s(%s)\n", m.getId(), m.getTitle(), m.getGradeName());
			}
			System.out.println("-------------------------------------------------------------");
		}
	}
	
	// 현재 영화목록이 비어있지 않으면 true값을 반환하는 메소드(InfoViewer 사용) 
	public boolean movieListEmpty() {
		ArrayList<MovieDTO> list = movieController.selectAll();
		
		if(!list.isEmpty()) {
			return true;
		}
		return false;
	}
	
	// 상영정보에서 사용할 영화이름 출력하는 메소드
	public String printByMovieName(int movieId) {
		MovieDTO m = movieController.selectOne(movieId);
		if(m==null) {
			return null;
		}	
		return m.getTitle();	
	}
	
	//해당 영화의 상영정보 보여주는 메소드(infoViewer에서 받아옴)
	public void printTheaterName(int movieId) {
		ArrayList<String> theaterName = infoViewer.printTheaterId(movieId);
		
		if(theaterName == null) {
			System.out.println("현재 해당영화의 상영정보가 없습니다.");
		} else {		
			for(int i=0 ; i<theaterName.size() ; i++) {
				System.out.printf("%d. %s\n", i+1, theaterName.get(i));
			}
		}
	}
	
	// 현재 등록된 영화가 없는 경우 true를 반환하는 메소드
	public boolean movieBoolean() {
		if(movieController.selectAll().isEmpty()) {
			return true;
		}
		
		return false;
	}
	

	

}
