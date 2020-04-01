package character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import states.GameOverState;
import states.IntroState;
import states.Level1;
import states.Level2;
import states.Level3;
import states.Level4;
import states.MenuAnleitungState;
import states.MenuBeendenState;
import states.MenuCreditsState;
import states.MenuHighscoreState;
import states.MenuNewGameState;
import states.MenuState;
import states.State;
import managers.GameStateManager;

public class PathHandler {
	
	private ArrayList<Path> _pathList;
	private static PathHandler instance = null;
	
	public PathHandler(){
		_pathList  = new ArrayList<Path>(); 
		Path p1 = new Path();
		p1.addVector(50,170);
		p1.addVector(200,200);
		p1.addVector(400,180);
		p1.addVector(500,200);
		Path p2 = new Path();
		p2.addVector(100,220);
		p2.addVector(150,200);
		p2.addVector(400,210);
		p2.addVector(555,200);
		Path p3 = new Path();
		p3.addVector(50,300);
		p3.addVector(100,330);
		p3.addVector(150,300);
		p3.addVector(300,250);
		Path p4 = new Path();
		p4.addVector(300,180);
		p4.addVector(350,250);
		p4.addVector(300,330);
		p4.addVector(200,150);
		Path p5 = new Path();
		p5.addVector(70,300);
		p5.addVector(90,250);
		p5.addVector(70,200);
		p5.addVector(100,150);
		Path p6 = new Path();
		p6.addVector(50,250);
		p6.addVector(200,300);
		p6.addVector(400,200);
		p6.addVector(500,330);
		Path p7 = new Path();
		p7.addVector(50,170);
		p7.addVector(200,200);
		p7.addVector(400,180);
		p7.addVector(500,200);
		Path p8 = new Path();
		p8.addVector(100,220);
		p8.addVector(150,200);
		p8.addVector(400,210);
		p8.addVector(555,200);
		Path p9 = new Path();
		p9.addVector(50,300);
		p9.addVector(100,330);
		p9.addVector(150,300);
		p9.addVector(300,250);
		Path p10 = new Path();
		p10.addVector(300,180);
		p10.addVector(350,250);
		p10.addVector(300,330);
		p10.addVector(200,150);
		Path p11 = new Path();
		p11.addVector(70,300);
		p11.addVector(90,250);
		p11.addVector(70,200);
		p11.addVector(100,150);
		Path p12 = new Path();
		p12.addVector(50,250);
		p12.addVector(200,300);
		p12.addVector(400,200);
		p12.addVector(500,330);
		_pathList.add(p1);
		_pathList.add(p2);
		_pathList.add(p3);
		_pathList.add(p4);
		_pathList.add(p5);
		_pathList.add(p6);
		_pathList.add(p7);
		_pathList.add(p8);
		_pathList.add(p9);
		_pathList.add(p10);
		_pathList.add(p11);
		_pathList.add(p12);
		
		
	}
	
	public static PathHandler getInstance(){
		if(instance == null){
			instance = new PathHandler();
			
		}
		return instance;
	}
	
	
	
	
	public Path getPath(int i){
		
		return _pathList.get(i);
	}
	
	public void setPath(){
	
		
		
	}
	public Path getFreePath(){

		
		Path ret = new Path();
		int sizeofpath = _pathList.size();
		Random r = new Random();
		int index = -1;
		while(index<0){
			index = r.nextInt(sizeofpath);
			System.out.println(index+"bumsmuschi");
			if(_pathList.get(index)._fp == true){
				ret = _pathList.get(index);
				ret.blockPath();
				return ret;
			}
			index = -1;
		}
		return ret;
		
	}
	
	
	public ArrayList<Path> getPathList(){
		
		return _pathList;
		
	}
	
	public void freePaths(){
		for(int i =0;i<PathHandler.getInstance().getPathList().size();i++){
			
			PathHandler.getInstance().getPathList().get(i)._fp=true;
			
		}
	}
	
	
}
