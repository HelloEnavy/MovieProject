package viewer;
import model.UserDTO;
import controller.UserController;
import util.ScannerUtil;
import java.util.Scanner;

public class UserViewer {
	private UserController userController;
	private UserDTO login;
	private Scanner scanner;
	private MovieViewer movieViewer;
	private ScoreViewer scoreViewer;
	private TheaterViewer theaterViewer;
	private InfoViewer infoViewer;
	
	public UserViewer() {
		userController = new UserController();
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer = movieViewer;
	}
	
	public void setScoreViewer(ScoreViewer scoreViewer) {
		this.scoreViewer = scoreViewer;
	}
	
	public void setTheaterViewer(TheaterViewer theaterViewer) {
		this.theaterViewer = theaterViewer;
	}
	
	public void setInfoViewer(InfoViewer infoViewer) {
		this.infoViewer = infoViewer;
	}
	
	
	public void showLogin() {
		while(true) {
			String message = new String("1.로그인 2.회원가입 3.프로그램 종료");
			int userChoice = ScannerUtil.nextInt(scanner, message);
			
			if(userChoice == 1) {
				userLogin();
				if(login!=null) {
					movieViewer.setLogin(login);
					scoreViewer.setLogin(login);
					theaterViewer.setLogin(login);
					infoViewer.setLogin(login);
					loginMenu();
				}
			} else if(userChoice == 2) {
				userRegister();
			} else if(userChoice == 3) {
				System.out.println("이용해 주셔서 감사합니다^^");
				scanner.close();
				break;
			}
		}
	}
	
	private void userLogin() {		
		while(login==null) {
			String message = new String("아이디를 입력해 주세요.(뒤로가기 X번)");
			String username = ScannerUtil.nextLine(scanner, message);
			
			if(username.equalsIgnoreCase("x")) {
				break;
			}

			message = new String("비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			login = userController.auth(username, password);
			
			if(login==null) {
				userController.check(username, password);
			}		
		} 
	}
	
	private void loginMenu() {
		System.out.println("정상적으로 로그인 되었습니다.");
		System.out.println("-------------------------------------------------------------");
		System.out.printf("[안녕하세요. %s님(%s)]\n", login.getNickname(), login.getGradeName());
		
		while(login!=null) {
			System.out.println("-------------------------------------------------------------");
			String message = new String("1.영화보기 2.극장보기 3.회원정보 4.로그아웃");
			int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
			
			if(userChoice == 1) {
				movieViewer.showMenu();
			} else if(userChoice == 2) {
				//극장 메소드
				theaterViewer.showTheater();
			} else if(userChoice == 3) {
				printInfo(login.getId());
			} else if(userChoice == 4) {
				login = null;		
				movieViewer.setLogin(login);
				scoreViewer.setLogin(login);
				theaterViewer.setLogin(login);
				infoViewer.setLogin(login);
				System.out.println("정상적으로 로그아웃 되었습니다^^");
				System.out.println("-------------------------------------------------------------");
				
			}
		}
	}
	
	private void userRegister() {
		UserDTO u = new UserDTO();
		
		while(true) {			
			String message = new String("아이디를 입력해 주세요.(뒤로가기 X번)");
			String username = ScannerUtil.nextLine(scanner, message);
			
			if(username.equalsIgnoreCase("x")) {
				break;
			}
			
			if(userController.sameUsername1(username)) {			
				System.out.println("중복된 아이디입니다.");
				continue;
			}
			
			message = new String("비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
				
			message = new String("닉네임을 입력해 주세요.");
			String nickname = ScannerUtil.nextLine(scanner, message);
			
			u.setUsername(username);
			u.setPassword(password);
			u.setNickname(nickname);
			u.setGradeNum(1);
			
			userController.insert(u);
			System.out.println("정상적으로 회원가입 되었습니다^^");
			break;
		
		}
	}
	
	private void printInfo(int id) {
		while(true) {		
			UserDTO u = userController.selectOne(id);
			userInfo(u);
			
			if(!(u.getGradeNum() == 1)) {
				String message = new String("뒤로가시려면 0번을 눌러주세요.");
				int userChoice = ScannerUtil.nextInt(scanner, message, 0, 0);			
				break;				
			}
			
			//관리자계정, 영화평론가는 수정을 하지 못하게 하였습니다.
			if(u.getGradeNum() == 1) {
				String message = new String("1.회원정보수정 2.회원탈퇴 3.뒤로가기(메인메뉴)");
				int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
			
				if(userChoice == 1) {
					userUpdate(u.getId());
				} else if(userChoice == 2) {
					userDelete(u.getId());
				} else if(userChoice == 3) {
					break;
				}
			}
		} 
	}
	
	private void userInfo(UserDTO u) {
		System.out.println("-------------------------------------------------------------");
		System.out.printf("[ %s님의 회원정보 ]\n", u.getNickname());
		System.out.println("등급 : " + u.getGradeName());
		System.out.println("아이디 : " + u.getUsername());
		System.out.println("-------------------------------------------------------------");
	}
	
	private void userUpdate(int id) {
		UserDTO u = userController.selectOne(id);

		String message = new String("새로운 닉네임을 입력해 주세요.");
		u.setNickname(ScannerUtil.nextLine(scanner, message));
		
		message = new String("새로운 비밀번호를 입력해 주세요.");
		u.setPassword(ScannerUtil.nextLine(scanner, message));
		
		message = new String("정말로 수정하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("y")) {
			message = new String("본인확인을 위해 기존의 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(password.equals(login.getPassword())) {
				userController.update(u);
				login = u;
				movieViewer.setLogin(login);
				scoreViewer.setLogin(login);
				theaterViewer.setLogin(login);
				infoViewer.setLogin(login);
				System.out.println("새로운 정보로 수정되었습니다.");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			}
		}
	}
	
	
	private void userDelete(int id) {
		String message = new String("정말로 탈퇴하시겠습니까? Y/N");
		String answer = ScannerUtil.nextLine(scanner, message);
		
		if(answer.equalsIgnoreCase("y")) {
			message = new String("본인확인을 위해 비밀번호를 입력해 주세요.");
			String password = ScannerUtil.nextLine(scanner, message);
			
			if(password.equals(login.getPassword())) {
				userController.delete(id);
				login = null;
				movieViewer.setLogin(login);
				scoreViewer.setLogin(login);
				theaterViewer.setLogin(login);
				infoViewer.setLogin(login);
				scoreViewer.deleteByUser(id);
				System.out.println("정상적으로 회원탈퇴 되었습니다.");
				System.out.println("-------------------------------------------------------------");
				System.out.println("그동안 이용해 주셔서 감사합니다^^");
				System.out.println("-------------------------------------------------------------");
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
			}
		}
	}
	
	// 아이디를 넘겨주면 유저의 닉네임을 넘겨주는 메소드
	public String printUserId(int id) {
		UserDTO u = userController.selectOne(id);
		return u.getNickname();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
