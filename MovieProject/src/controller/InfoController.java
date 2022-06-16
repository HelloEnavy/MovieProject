package controller;
import model.InfoDTO;
import java.util.ArrayList;
public class InfoController {
	private ArrayList<InfoDTO> list;
	private int nextId;
	
	public InfoController() {
		list = new ArrayList<>();
		nextId = 1;
	}
	
	public void insert(InfoDTO i) {
		i.setId(nextId++);
		list.add(i);
	}
	
	public ArrayList<InfoDTO> selectAll(int theaterId) {
		ArrayList<InfoDTO> i = new ArrayList<>();
		for(InfoDTO temp : list) {
			if(temp.getTheaterId() == theaterId) {
				i.add(new InfoDTO(temp));
			}
		}
		return i;
	}
	
	public InfoDTO selectOne(int id) {
		for(InfoDTO i : list) {
			if(i.getId() == id) {
				return new InfoDTO(i);
			}
		}
		return null;
	}
	
	public InfoDTO selectOne(int id, int theaterId) {
		for(InfoDTO i : list) {
			if(i.getId() == id && i.getTheaterId() == theaterId) {
				return new InfoDTO(i);
			}
		}
		return null;
	}
	
	
	public void update(InfoDTO i) {
		list.set(list.indexOf(i), i);
	}
	
	public void delete(int id) {
		InfoDTO i = new InfoDTO();
		i.setId(id);
		list.remove(i);
	}
	
	// 영화 삭제될 시 영화 아이디 받아 삭제 삭제
	public void deleteByMovieId(int movieId) {
		for(int i=0 ; i<list.size() ; i++) {
			InfoDTO l =list.get(i);
			if(l.getMovieId() == movieId) {
				list.remove(i);
				i--;
			}
		}
	}
	
	// 극장 삭제시 극장 아이디받아 상영정보 삭제
	public void deleteByTheaterId(int theaterId) {
		for(int i=0 ; i<list.size(); i++) {
			InfoDTO l = list.get(i);
			if(l.getTheaterId() == theaterId) {
				list.remove(i);
				i--;
			}
		}
	}
	
	// 영화아이디를 받으면 해당 영화아이디와 같은 상영정보만 담은 리스트 반환
	public ArrayList<InfoDTO> printTheater(int movieId) {
		ArrayList<InfoDTO> temp = new ArrayList<>();
		for(InfoDTO l : list) {
			if(l.getMovieId() == movieId) {
				temp.add(new InfoDTO(l));
			}
		}		
		if(temp.isEmpty()) {
			return null;
		} else {
			return temp;
		}
	}
	
}
