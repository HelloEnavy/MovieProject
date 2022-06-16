package controller;
import java.util.ArrayList;
import model.MovieDTO;
public class MovieController {
	private ArrayList<MovieDTO> list;
	private int nextId;
	
	public MovieController() {
		list = new ArrayList<>();
		nextId=1;
	}
	
	// 영화 등록
	public void insert(MovieDTO m) {
		m.setId(nextId++);
		if(m.getGradeNum() == 1) {
			m.setGradeName("전체관람가");
		} else if(m.getGradeNum() == 2) {
			m.setGradeName("12세관람가");
		} else if(m.getGradeNum() == 3) {
			m.setGradeName("15세관람가");
		} else if(m.getGradeNum() == 4) {
			m.setGradeName("18세관람가");
		}

		list.add(m);	
	}
	
	// 전체 영화 목록
	public ArrayList<MovieDTO> selectAll() {
		ArrayList<MovieDTO> temp = new ArrayList<>();
		for(MovieDTO m : list) {
			temp.add(new MovieDTO(m));
		}
		return temp;
	}
	
	// 선택한 영화 상세보기
	public MovieDTO selectOne(int id) {
		for(MovieDTO m : list) {
			if(m.getId()==id) {
				return new MovieDTO(m);
			}
		}
		return null;
	}
	
	// 영화 수정
	public void update(MovieDTO m) {
		list.set(list.indexOf(m), m);
	}
	
	// 영화 삭제
	public void delete(int id) {
		MovieDTO m = new MovieDTO();
		m.setId(id);
		list.remove(m);
	}
	
	public void printMovieId(int id) {
		
	}
	
}
