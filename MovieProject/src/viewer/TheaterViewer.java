package viewer;
import util.ScannerUtil;

import java.util.Scanner;
import java.util.ArrayList;

import controller.TheaterController;
import model.TheaterDTO;
import model.UserDTO; 

public class TheaterViewer {
	private TheaterController theaterController;
	private Scanner scanner;
	private UserDTO login;
	private InfoViewer infoViewer;
	
	public TheaterViewer() {
		theaterController = new TheaterController();
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void setLogin(UserDTO login) {
		this.login = login;
	}
	
	public void setInfoViewer (InfoViewer infoViewer) {
		this.infoViewer = infoViewer;
	}
	
	public void showTheater() {
		while(true) {
			ArrayList<TheaterDTO> list = theaterController.selectAll();
			
			if(list.isEmpty()) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("현재 등록된 극장이 없습니다.");
				System.out.println("-------------------------------------------------------------");
				
				if(login.getGradeNum() == 3) {
					String message = new String("1.극장등록하기 2.뒤로가기(메인메뉴)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
					if(userChoice == 1) {
						// 극장 등록 메소드
						theaterRegister();
					} else {
						break;
					}
				} 
				list = theaterController.selectAll();
				
				if(list.isEmpty()) {
					break;
				}
				
			} else {
				System.out.println("-------------------------------------------------------------");
				for(TheaterDTO t : list) {
					System.out.printf("%d. %s(%s)\n", t.getId(), t.getName(), t.getArea());
				}
				System.out.println("-------------------------------------------------------------");
				
				//관리자일경우
				if(login.getGradeNum() == 3) {
					String message = new String("1.극장등록하기 2.극장 및 상영정보보기 3.뒤로가기(메인메뉴)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
					if(userChoice == 1) {
						// 극장 등록 메소드
						theaterRegister();
					} else if(userChoice == 2) {
						message = new String("상세보기할 극장의 번호를 입력해 주세요.(뒤로가기 0번)");
						int userChoice1 = ScannerUtil.nextInt(scanner, message);
						
						if(userChoice1 != 0) {
							// 상세보기 메소드
							theaterOne(userChoice1);
						}
					} else {
						break;
					}
				} else {			
					
					String message = new String("극장 및 상영정보 원하시는 극장번호를 입력해 주세요.(뒤로가기 0번)");
					int userChoice = ScannerUtil.nextInt(scanner, message);
					
					if(userChoice != 0) {
						// 상세보기 메소드
						theaterOne(userChoice);
					} else {
						break;
					}				
				}
			}
		}
	}
	
	private void theaterRegister() {
		TheaterDTO t = new TheaterDTO();
		
		String message = new String("극장 이름을 입력해 주세요.");
		t.setName(ScannerUtil.nextLine(scanner, message));
			
		message = new String("극장 위치를 입력해 주세요.");
		t.setArea(ScannerUtil.nextLine(scanner, message));
			
		message = new String("극장 전화번호를 입력해 주세요.(예:123-456-7899)");
		String number = ScannerUtil.nextLine(scanner, message);
		String regEx = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$";
			
		while(!(number.matches(regEx))) {
			System.out.println("전화번호를 잘못입력하셨습니다.");
			message = new String("다시 입력해 주세요.");
			number = ScannerUtil.nextLine(scanner, message);
		}
			
		t.setNumber(number);
			
		message = new String("극장 정보를 등록하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
			
		if(answer.equalsIgnoreCase("Y")) {
			theaterController.insert(t);
			System.out.println("정상적으로 등록되었습니다.");
		} else {
			System.out.println("등록이 취소되었습니다.");
		}
	}
	
	
	private void theaterOne(int theaterId) {
		TheaterDTO t = theaterController.selectOne(theaterId);
		
		if(t == null) {
			System.out.println("해당 극장번호로는 조회되지 않습니다.");
		} else {
			while(true) {
				t = theaterController.selectOne(theaterId);
				System.out.println("-------------------------------------------------------------");
				System.out.println("<극장 상세 정보>");
				System.out.println("-------------------------------------------------------------");
				System.out.println("극장이름 : " + t.getName());
				System.out.println("극장위치 : " + t.getArea());
				System.out.println("극장번호 : " + t.getNumber());
				System.out.println("-------------------------------------------------------------");
			
				if(login.getGradeNum() == 3) {
					String message = new String("1.상영정보보기 2.극장정보수정 3.극장정보삭제 4.뒤로가기(극장목록)");
					int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
					
					if(userChoice == 1) {
						// 상영정보 보는 메소드
						infoViewer.showInfo(theaterId);
					}
					else if(userChoice == 2) {
						theaterUpdate(theaterId);
					} else if(userChoice == 3) {
						theaterDelete(theaterId);
						break;
					} else if(userChoice == 4) {
						break;
					}
					
				} else { 
					String message = new String("1.상영정보보기 2.뒤로가기(극장목록)");
					int userChoice = ScannerUtil.nextInt(scanner, message,1,2);
					
					if(userChoice == 1) {
						// 상영정보 보는 메소드
						infoViewer.showInfo(theaterId);
					} else if(userChoice == 2) {
						break;
					}					
				}
			}
		}
	}
	
	//극장 정보 수정 메소드
	private void theaterUpdate(int theaterId) {
		TheaterDTO t = theaterController.selectOne(theaterId);
		
		String message = new String("새로운 극장 이름을 입력해 주세요.");
		t.setName(ScannerUtil.nextLine(scanner, message));
		
		message = new String("새로운 극장 위치를 입력해 주세요.");
		t.setArea(ScannerUtil.nextLine(scanner, message));
		
		message = new String("새로운 극장 전화번호를 입력해 주세요.(예:123-456-7899)");
		String number = ScannerUtil.nextLine(scanner, message);
		String regEx = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$";
		
		while(!(number.matches(regEx))) {
			System.out.println("전화번호를 잘못입력하셨습니다.");
			message = new String("다시 입력해 주세요.");
			number = ScannerUtil.nextLine(scanner, message);
		}
		
		t.setNumber(number);
		
		message = new String("극장 정보를 수정하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			message = new String("본인확인을 위해 관리자님 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(login.getPassword().equals(password)) {
				theaterController.update(t);
				System.out.println("정상적으로 극장정보가 수정되었습니다.");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			} 
		} else {
			System.out.println("극장정보 수정이 취소되었습니다.");
		}
	}
	
	
	// 극장 정보 삭제 메소드
	private void theaterDelete(int theaterId) {
		String message = new String("해당 극장을 정말로 삭제하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("Y")) {
			message = new String("본인확인을 위해 관리자님 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(login.getPassword().equals(password)) {
				theaterController.delete(theaterId);
				infoViewer.deleteByTheater(theaterId);
				System.out.println("정상적으로 극장정보가 삭제되었습니다.");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			} 
		} else {
			System.out.println("극장정보 삭제가 취소되었습니다.");
		}
	}
	
	// 아이디받으면 해당 아이디의 영화 이름 반환
	public String printByTheaterName(int theaterId) {
		TheaterDTO t = theaterController.selectOne(theaterId);
		return t.getName();
	}
}
