package controller;
import model.ScoreDTO;
import java.util.ArrayList;
import java.util.Calendar;

public class ScoreController {
	private ArrayList<ScoreDTO> list;
	private int nextId;

	public ScoreController() {
		list = new ArrayList<>();
		nextId = 1;
	}
	
	// 평론 등록
	public void insert(ScoreDTO s) {
		s.setId(nextId++);
		s.setWrittenDate(Calendar.getInstance());
		s.setUpdateDate(Calendar.getInstance());
		list.add(s);
	}
	
	// 영화아이디로 비교하여 평론 목록 출력
	public ArrayList<ScoreDTO> selectAll(int movieId) {
		ArrayList<ScoreDTO> s = new ArrayList<>();
		for(ScoreDTO temp : list) {
			if(temp.getMovieId() == movieId) {
				s.add(new ScoreDTO(temp));
			}
		}
		return s;
	}
	
	// 평론 상세보기 메소드
	public ScoreDTO selectOne(int id) {
		for(ScoreDTO s : list) {
			if(s.getId() == id) {
				return new ScoreDTO(s);
			}
		}
		return null;
	}
	
	
	
	
	// 해당 영화에 해당 감상평
	public ScoreDTO selectOne(int id, int movieId) {
		for(ScoreDTO s : list) {
			if(s.getId()==id && s.getMovieId() == movieId) {
				return new ScoreDTO(s);
			}
		}
		return null;
	}
	
	// 해당 영화의 감상평에 유저감상평이 비어있는지
	public boolean user(ArrayList<ScoreDTO> list) {
		ArrayList<ScoreDTO> temp = list;
		for(ScoreDTO s : temp) {
			if(s.getUserBoard().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	// 해당 영화의 감상평에 전문가 감상평이 있는지
	public boolean master(int movieId) {
		ArrayList<ScoreDTO> list = selectAll(movieId);
		for(ScoreDTO s : list) {
			if(s.getMasterBoard()==null) {
				return true;
			}
		}
		return false;
	}
	
	
	// 평점 수정 
	public void update(ScoreDTO s) {
		list.set(list.indexOf(s), s);
		s.setUpdateDate(Calendar.getInstance());
	}
	
	
	public void delete(int id) {
		ScoreDTO s = new ScoreDTO();
		s.setId(id);
		list.remove(s);
	}
	
	//영화삭제시 삭제된 영화아이디 받아 해당 평점 삭제
	public void deleteByMovieId(int movieId) {
		for(int i=0 ; i<list.size() ; i++) {
			ScoreDTO s = list.get(i);
			if(s.getMovieId() == movieId) {
				list.remove(i);
				i--;
			}
		}
	}
	
	//회원탈퇴시 탈퇴한 회원아이디 받아 평점 삭제
	public void deleteByUserId(int writeId) {
		for(int i=0 ; i<list.size() ; i++) {
			ScoreDTO s = list.get(i);
				if(s.getWriteId() == writeId) {
					list.remove(i);
					i--;
				}
			}
		}
	
	//해당 영화에 평점등록한 총 사람의 수 
	public int scorePerson(int movieId) {
		ArrayList<ScoreDTO> s = new ArrayList<>();
		for(ScoreDTO temp : list) {
			if(temp.getMovieId() == movieId) {
				s.add(new ScoreDTO(temp));
			}
		}		
		if(s.isEmpty()) {
			return 0;
		} else {
		return s.size();
		}
	}

}
