package util;
// 우리가 입력을 처리할 때 도움이 되는 메소드를 모아둔 클래스
import java.util.Scanner;
public class ScannerUtil {

	// 1.입력 시 메시지의 출력 방법을 담당하는 printMessage(String)
	private static void printMessage(String message) {
		System.out.println(message);
		System.out.print("> ");
	}
	// 2.정수 입력에 사용할 nextInt(Scanner,String)
	public static int nextInt(Scanner scanner,String message) {
		String temp = nextLine(scanner, message);	
		
		while(!validateNumber(temp)) {
			System.out.println("잘못 입력하셨습니다.");
			temp = nextLine(scanner, message);
		}
			return Integer.parseInt(temp);
	}
	// 3. 범위의 정수 입력에 사용할 nextInt(Scanner, String, int, int)
	public static int nextInt(Scanner scanner, String message, int min, int max) {
		int temp = nextInt(scanner, message);
	
		while(temp<min || temp>max) {
			System.out.println("잘못 입력하셨습니다.");
			System.out.println("다시 입력하세요.");
			System.out.print("> ");
			temp = nextInt(scanner, message);			
		}
		
		return temp;
	}
	
	//4. String 입력에 사용할 nextLine(Scanner,String)
	public static String nextLine(Scanner scanner, String message) {
		printMessage(message);
		String temp = scanner.nextLine();
		
		if(temp.isEmpty()) {
			temp = scanner.nextLine();
			
		}
		
		return temp;
	}
	
	// 5. 사용자가 입력한 값이 숫자로만 이루어져있으면 true, 그 외에는 false가 반환되는 메소드
	private static boolean validateNumber(String s) {
		// 숫자로만 이루어져있는지 체크
		String regEx = new String("^\\d+$");
		if(s.matches(regEx)) {
			return true;
		}
		
		// -숫자로만 이루어져있는지 체크
		regEx = new String("^-\\d+$");
		if(s.matches(regEx)) {
			return true;
		}
		
		return false;
	}
	
	public static double nextDouble(Scanner scanner, String message, double min, double max) {
		printMessage(message);
		double user = scanner.nextDouble();
		while(user<min || user>max) {
			System.out.println("잘못 입력하셨습니다.");
			System.out.println("다시 입력하세요.");
			user = scanner.nextInt();
		}
		return user;
	}
	
	
	
	
}
	


