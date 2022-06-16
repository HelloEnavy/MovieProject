package controller;
import model.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
	private ArrayList<UserDTO> list;
	// 배워서 한번 써보려고 넣었습니다....
	private HashMap<String, String> map;
	private int nextId;
	
	public UserController() {
		list = new ArrayList<>();
		map = new HashMap<String, String>();
		nextId = 1;
		
		//영화평론가 3명
		for(int i=1 ; i<=3 ; i++) {
			UserDTO u = new UserDTO();
			u.setUsername("g"+i);
			u.setPassword("g"+i);
			map.put("g"+i, "g"+i);
			u.setNickname("G"+i);
			u.setGradeNum(2);
			
			insert(u);
		}
		
		//관리자 1명
		UserDTO u = new UserDTO();
		u.setUsername("master");
		u.setPassword("master");
		u.setNickname("Master");
		map.put("master", "master");
		u.setGradeNum(3);
		
		insert(u);
		
	}
	// 유저 회원가입시 정보 list 추가
	public void insert(UserDTO u) {
		u.setId(nextId++);		
		if(u.getGradeNum() == 1) {
			u.setGradeName("일반관람객");
		} else if(u.getGradeNum() == 2) {
			u.setGradeName("영화평론가");
		} else if(u.getGradeNum() == 3) {
			u.setGradeName("관리자");
		}
		map.put(u.getUsername(), u.getPassword());
		
		list.add(u);
	}
	
	// 입력한 아이디와 비밀번호가 맞는지 확인
	public void check(String username, String password) {
		if(map.containsKey(username)) {
			if(!(map.get(username).equals(password))) {
				System.out.println("비밀번호가 맞지 않습니다.");
			} 		
		} else {
			System.out.println("존재하지 않는 아이디입니다.");
		}
	}

	// 중복 확인 메서드
	public boolean sameUsername1(String username) {
		return map.containsKey(username);
	}
	
	public UserDTO auth(String username, String password) {
		for(UserDTO u : list) {
			if(u.getUsername().equalsIgnoreCase(username)&&u.getPassword().equals(password)) {
				return new UserDTO(u);
			}
		}
		return null;
	}
	
	// 지정 유저의 정보 가져옴
	public UserDTO selectOne(int id) {
		for(UserDTO u : list) {
			if(u.getId() == id) {
				return new UserDTO(u);
			}
		}
		return null;
	}
	
	// 유저 정보 수정
	public void update(UserDTO u) {
		list.set(list.indexOf(u), u);
	}
	
	// 유저 삭제
	public void delete(int id) {
		UserDTO u = new UserDTO();
		u.setId(id);
		list.remove(u);
	}

	
	
	
}
