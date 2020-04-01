package world;

import java.util.ArrayList;

public class Sequences {
	ArrayList<Phase> phases; 
	private int _x;
	
	
	public Sequences(int x){
		_x = x;
		phases = new ArrayList<Phase>();
	}
	
	public void addPhase(Phase phase){
		phases.add(phase);
	}
	
	public int getX(){
		return _x;
	}
	
	public Phase getPhaseAt(int i){
		return phases.get(i);
	}
	
	public Phase getLastPhase(){
		return phases.get(phases.size()-1);
	}
	
	public ArrayList<Phase> getPhases(){
		return phases;
	}
	

}
