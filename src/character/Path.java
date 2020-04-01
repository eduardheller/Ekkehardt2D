package character;

import java.util.ArrayList;

import utils.Vector2d;

public class Path {

	public boolean _fp;
	public ArrayList<Vector2d> vec2;
	
	public Path(){
		vec2 = new ArrayList<Vector2d>();
		_fp = true;
		
	}
	
	
	public Vector2d get(int i){
		
		Vector2d ret = new Vector2d();
		ret = vec2.get(i);
		return ret;
		
	}
	
	public void blockPath(){
		_fp = false;
	}
	
	public void addVector(double x,double y){
		vec2.add(new Vector2d(x,y));
	}

	
	
}
