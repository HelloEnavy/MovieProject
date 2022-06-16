package controller;
import model.TheaterDTO;
import java.util.ArrayList;
public class TheaterController {
	private ArrayList<TheaterDTO> list;
	private int nextId;
	
	public TheaterController() {
		list = new ArrayList<>();
		nextId = 1;
	}
	
	public void insert(TheaterDTO t) {
		t.setId(nextId++);
		list.add(t);
	}
	
	public ArrayList<TheaterDTO> selectAll() {
		ArrayList<TheaterDTO> t = new ArrayList<>();
		for(TheaterDTO temp : list) {
			t.add(new TheaterDTO(temp));
		}
		return t;
	}
	
	public TheaterDTO selectOne(int id) {
		for(TheaterDTO t : list) {
			if(t.getId() == id) {
				return new TheaterDTO(t);
			}
		}
		return null;
	}
	
	public void update(TheaterDTO t) {
		list.set(list.indexOf(t), t);
	}
	
	public void delete(int id) {
		TheaterDTO t = new TheaterDTO();
		t.setId(id);
		list.remove(t);
	}
}
