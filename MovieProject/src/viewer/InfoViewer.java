package viewer;
import java.util.Scanner;
import util.ScannerUtil;
import model.TheaterDTO;
import model.MovieDTO;
import model.UserDTO;
import model.InfoDTO;
import java.util.HashMap;
import controller.InfoController;
import viewer.MovieViewer;
import viewer.TheaterViewer;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class InfoViewer {
	private Scanner scanner;
	private UserDTO login;
	private MovieViewer movieViewer;
	private TheaterViewer theaterViewer;
	private InfoController infoController;
		
	public InfoViewer() {
		infoController = new InfoController();
	}
		
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer = movieViewer;
	}
	
	public void setTheaterViewer(TheaterViewer theaterViewer) {
		this.theaterViewer = theaterViewer;
	}
	
	public void setLogin(UserDTO login) {
		this.login = login;
	}
	
	public void showInfo(int theaterId) {
		while(true) {
			
		ArrayList<InfoDTO> list = infoController.selectAll(theaterId);
		
		if(list.isEmpty()) {
			System.out.println("-------------------------------------------------------------");
			System.out.println("해당 극장에는 현재 등록된 상영정보가 없습니다.");
			System.out.println("-------------------------------------------------------------");
			
			if(login.getGradeNum()==3) {
				String message = new String("1.상영정보등록 2.뒤로가기(극장상세정보)");
				int userChoice = ScannerUtil.nextInt(scanner, message,1,2);
				if(userChoice == 1) {
					//상영정보등록메소드
					infoRegister(theaterId);
					if(movieViewer.movieBoolean()) {
						break;
					}
				} else if(userChoice == 2) {
					break;
				}
				
			}
			
			
		} else {			
			
				System.out.println("-------------------------------------------------------------");
				System.out.println("<현재 극장에서 상영중인 영화 LIST>");
				System.out.println("-------------------------------------------------------------");
				for(InfoDTO i : list) {
					System.out.printf("%d. %s\n", i.getId(), movieViewer.printByMovieName(i.getMovieId()));
				}
				System.out.println("-------------------------------------------------------------");
				
				//관리자인 경우 
				if(login.getGradeNum()==3) {
					String message = new String("1.상영정보등록 2.상영영화 상세보기 3.뒤로가기(극장상세정보)");
					int userChoice = ScannerUtil.nextInt(scanner, message,1, 3);
					if(userChoice == 1) {
						infoRegister(theaterId);
					} else if(userChoice == 2) {
						message = new String("상세보기하려는 상영영화 번호를 입력해 주세요.");
						int userChoice1 = ScannerUtil.nextInt(scanner, message);
						infoOne(userChoice1, theaterId);
					} else if(userChoice == 3) {
						break;
					}
				} else {
				
					String message = new String("상세보기하려는 상영영화 번호를 입력해 주세요.(뒤로가기 0번)");
					int userChoice = ScannerUtil.nextInt(scanner, message);
				
					if(userChoice != 0) {
						// 상세보기 메서드
						infoOne(userChoice, theaterId);
					
					} else if(userChoice == 0) {
						break;
					}
				}	
			}		
		}
	}
	
	private void infoOne(int id, int theaterId) {			
			InfoDTO i = infoController.selectOne(id, theaterId);			
			if(i == null) {
				System.out.println("해당 번호로는 상영영화 정보가 조회되지 않습니다.");
				
			} else {		
			while(true) {
				i = infoController.selectOne(id);	
				System.out.println("-------------------------------------------------------------");
				System.out.println("<상영 상세 정보>");
				System.out.println("-------------------------------------------------------------");
				System.out.println("상영극장 : " + theaterViewer.printByTheaterName(i.getTheaterId()));
				System.out.println("상영영화 : " + movieViewer.printByMovieName(i.getMovieId()));
				System.out.println("영화시작시간 : " + i.getStartTime().substring(0,2) +"시 " + i.getStartTime().substring(3,5) + "분");
				System.out.println("상영시간 : " + i.getTime() + "분");
				System.out.println("-------------------------------------------------------------");
				
				if(login.getGradeNum() == 3) {
					String message = new String("1.상영정보수정 2.상영정보삭제 3.뒤로가기(상영영화목록)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
					
					if(userChoice == 1) {
						//수정메소드
						infoUpdate(id);
						if(movieViewer.movieBoolean()) {
							break;
						}
					} else if(userChoice == 2) {
						//삭제메소드
						infoDelete(id);
						break;
					} else if(userChoice == 3) {
						break;
					}
				} else {
					String message = new String("뒤로가시려면 0번을 눌러주세요.");
					int userChoice = ScannerUtil.nextInt(scanner, message, 0, 0);
					if(userChoice == 0) {
						break;
					}
				}
			}
		}
		
		
		
	}
	
	//상영정보등록
	private void infoRegister(int theaterId) {
		System.out.println("-------------------------------------------------------------");
		System.out.println("<현재 개봉한 영화 LIST>");
		movieViewer.movieListToInfo();
		
		if(movieViewer.movieBoolean()) {
			System.out.println("영화 등록 후 다시 이용해 주세요.");
		} else {
		
			if(movieViewer.movieListEmpty()) {		
				
				InfoDTO i = new InfoDTO();
				
				String message = new String("상영할 영화의 번호를 선택해 주세요.");
				int userChoice = ScannerUtil.nextInt(scanner, message);
				
				while(movieViewer.printByMovieName(userChoice) == null) {
					System.out.println("해당 영화번호는 존재하지 않습니다.");
					message = new String("다시 입력해 주세요.");
					userChoice = ScannerUtil.nextInt(scanner, message);		
				}
				
				i.setMovieId(userChoice);
				
				message = new String("시작시간을 입력해 주세요.(예:08:11)");
				String regEx = "^([01][0-9]|2[0-3]):([0-5][0-9])$";
				String start = ScannerUtil.nextLine(scanner, message);
				
				while(!(start.matches(regEx))) {
					System.out.println("잘못된 시간입니다.");
					start = ScannerUtil.nextLine(scanner, message);
				}
					
				i.setStartTime(start);
				
				message = new String("상영시간을 입력해 주세요.(단위:분)");
				i.setTime(ScannerUtil.nextInt(scanner, message));
				
				message = new String("해당 상영정보를 등록하시겠습니까? Y/N");
				String answer = ScannerUtil.nextLine(scanner, message);
				
				i.setTheaterId(theaterId);
				
				if(answer.equalsIgnoreCase("y")) {
					infoController.insert(i);
					System.out.println("정상적으로 상영정보가 등록되었습니다.");
				} else {
					System.out.println("상영정보 등록이 취소되었습니다.");
				}
			}
		}
	}
	
	private void infoUpdate(int id) {
		InfoDTO i = infoController.selectOne(id);
		System.out.println("-------------------------------------------------------------");
		System.out.println("<현재 개봉한 영화 LIST>");
		movieViewer.movieListToInfo();
		
		String message = new String("새로운 상영영화의 번호를 선택해 주세요.");
			
		int userChoice = ScannerUtil.nextInt(scanner, message);
		while(movieViewer.printByMovieName(userChoice) == null) {
			System.out.println("해당 영화번호는 존재하지 않습니다.");
			message = new String("다시 입력해 주세요.");
			userChoice = ScannerUtil.nextInt(scanner, message);		
		}
			
		i.setMovieId(userChoice);
			
		message = new String("새로운 시작시간을 입력해 주세요.(예:08:11)");
		String regEx = "^([01][0-9]|2[0-3]):([0-5][0-9])$";
		String start = ScannerUtil.nextLine(scanner, message);		
		while(!(start.matches(regEx))) {
			System.out.println("잘못된 시간입니다.");
			start = ScannerUtil.nextLine(scanner, message);
		}
				
		i.setStartTime(start);
			
		message = new String("새로운 상영시간을 입력해 주세요.(단위:분)");
		i.setTime(ScannerUtil.nextInt(scanner, message));
			
		message = new String("해당 상영정보를 수정하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
			
		if(answer.equalsIgnoreCase("y")) {
			infoController.update(i);
			System.out.println("정상적으로 상영정보가 수정되었습니다.");
		} else {
			System.out.println("상영정보 수정이 취소되었습니다.");
		}
	}

	
	private void infoDelete(int id) {
		String message = new String("정말로 상영정보를 삭제하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			message = new String("본인확인을 위해 관리자님 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(login.getPassword().equals(password)) {
				infoController.delete(id);
				System.out.println("정상적으로 상영정보가 삭제되었습니다.");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			} 
		} else {
			System.out.println("상영정보 삭제가 취소되었습니다.");
		}
	}
	
	public void deleteByMovie(int movieId) {
		System.out.println("해당 영화의 상영정보가 삭제되었습니다.");
		infoController.deleteByMovieId(movieId);
	}
	
	public void deleteByTheater(int theaterId) {
		System.out.println("해당 극장의 상영정보가 삭제되었습니다.");
		infoController.deleteByTheaterId(theaterId);
	}
	
	// 영화이름 받으면 해당 영화에 맞는 극장들 이름 가져오는 메소드
	public ArrayList<String> printTheaterId(int movieId) {
		ArrayList<InfoDTO> l = infoController.printTheater(movieId);
		if(l == null) {
			return null;
		} else {
			ArrayList<String> theaterList = new ArrayList<>();
			
			for(InfoDTO l1 : l) {
				theaterList.add(theaterViewer.printByTheaterName(l1.getTheaterId()));
			}
			// 한 극장에 동일한 영화가 여러번 등록되어있을 경우 극장이름이 중복으로 추가되지 않게 set을 이용했습니다.
			Set<String> set = new HashSet<String>(theaterList);
			ArrayList<String> theaterList1 = new ArrayList<String>(set);
			
			return theaterList1;
		}
	}
	
	
		
		
		
}
