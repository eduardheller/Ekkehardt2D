package world;


import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

import character.Player;
import character.PlayerController;
import sprite.Sprite;

public class Background {

	private Sprite _background;
	private Sprite _backgroundNext;
	private Sprite _cloud;
	private Sprite _cloudNext;
	private Sprite _fog;
	private Sprite _fogNext;
	private int _xoffsetBack = 1100;
	private int _yoffsetBack = 120;
	private int _x1PlayerOffset = 51;
	private int _x2PlayerOffset = 550;
	private float _backgroundSpeed;
	private int _cloudSpeed = 1;
	private float _fogSpeed = 0.5f;
	private static float _moveOffset = 0;
	private boolean _locked = false;
	
	public Background(String level){
		
		if(level=="level1"){
			_background = new Sprite("res/background/level1.png");
			_backgroundNext = new Sprite("res/background/level1_2.png");
			_cloud = new Sprite("res/background/level1_back.png");
			_cloudNext = new Sprite("res/background/level1_back.png");
			_fog = new Sprite("res/background/fog.png");
			_fogNext = new Sprite("res/background/fog_2.png");
		}else if(level=="level2"){
			_background = new Sprite("res/background/level2.png");
			_backgroundNext = new Sprite("res/background/level2_2.png");
			_cloud = new Sprite("res/background/level1_back.png");
			_cloudNext = new Sprite("res/background/level1_back.png");
			_fog = new Sprite("res/background/fog.png");
			_fogNext = new Sprite("res/background/fog_2.png");
		}else if(level=="level3"){
			_background = new Sprite("res/background/level3.png");
			_backgroundNext = new Sprite("res/background/level3_2.png");
			_cloud = new Sprite("res/background/level3_back.png");
			_cloudNext = new Sprite("res/background/level3_back.png");
			_fog = new Sprite("res/background/fog.png");
			_fogNext = new Sprite("res/background/fog_2.png");
		}else if(level=="level4"){
			_background = new Sprite("res/background/level4.png");
			_backgroundNext = new Sprite("res/background/level4_2.png");
			_cloud = new Sprite("res/background/level4_back.png");
			_cloudNext = new Sprite("res/background/level4_back.png");
			_fog = new Sprite("res/background/fog.png");
			_fogNext = new Sprite("res/background/fog_2.png");
		}else if(level=="level5"){
			_background = new Sprite("res/background/level4.png");
			_backgroundNext = new Sprite("res/background/level4_2.png");
			_cloud = new Sprite("res/background/level4_back.png");
			_cloudNext = new Sprite("res/background/level4_back.png");
			_fog = new Sprite("res/background/fog.png");
			_fogNext = new Sprite("res/background/fog_2.png");
		}

		
		_background.translate(-900, _yoffsetBack);
		_backgroundNext.translate(-900+_background.getImage().getWidth()*2,_yoffsetBack);
		_cloud.translate(0, _yoffsetBack);
		_cloudNext.translate(_cloud.getImage().getWidth()*2, _yoffsetBack);
		_fog.translate(0, _yoffsetBack);
		_fogNext.translate(_fog.getImage().getWidth()*2, _yoffsetBack);
		
		_background.scale(2, 2);
		_backgroundNext.scale(2, 2);
		_cloud.scale(2, 2);
		_cloudNext.scale(2, 2);
		_fog.scale(2, 2);
		_fogNext.scale(2, 2);
	}
	
	public void update(Graphics2D g, double delta,ArrayList<PlayerController> players){
		
		_moveOffset = 0;
		_cloud.show(g);
		_cloudNext.show(g);
		_background.show(g);
		_backgroundNext.show(g);
		

		
		
		double xArr[] = new double[players.size()];
	
		for(int i = players.size()-1;i>=0;i--){
			PlayerController currPlayer = players.get(i);
			xArr[i] = players.get(i).getX();
		}
		
		if(!_locked){
			outerloop:
				for(int i = players.size()-1;i>=0;i--){
					PlayerController currPlayer = players.get(i);
					if(currPlayer.hasMovePressed() && !currPlayer.isAttacking()){
						if(currPlayer.getX()>_x2PlayerOffset){
							for(int j = 0; j<=xArr.length-1;j++){
								if(xArr[j]<_x1PlayerOffset){
									break outerloop;
								}
							}
							_backgroundSpeed = currPlayer.getMoveSpeedX();
							_background.translate(-_backgroundSpeed*delta, 0);
							_backgroundNext.translate(-_backgroundSpeed*delta, 0);
							_cloud.translate(-_cloudSpeed*delta, 0);
							_cloudNext.translate(-_cloudSpeed*delta, 0);
							_moveOffset = _backgroundSpeed;
							break;
						}
//						else if(currPlayer.getX()<_x1PlayerOffset){
//							for(int j = 0; j<=xArr.length-1;j++){
//								if(xArr[j]>_x2PlayerOffset){
//									break outerloop;
//								}
//							}
//							_background.translate(_backgroundSpeed*delta, 0);
//							_backgroundNext.translate(_backgroundSpeed*delta, 0);
//							_cloud.translate(_cloudSpeed*delta, 0);
//							_cloudNext.translate(_cloudSpeed*delta, 0);
//							_moveOffset = -_backgroundSpeed;
//							break;
//
//						}
					}
					
				}	
		}


		
//		if(_background.getX()<-_xoffsetBack){
//			int off = -_background.getX()-_xoffsetBack;
//			_background.translate(_xoffsetBack+off+_backgroundNext.getX()+_background.getImage().getWidth()*2, 0);
//		}
//		if(_backgroundNext.getX()<-_xoffsetBack){
//			int off = -_backgroundNext.getX()-_xoffsetBack;
//			_backgroundNext.translate(_xoffsetBack+off+_background.getX()+_background.getImage().getWidth()*2,0);
//		}
//		
//		if(_cloud.getX()<-_xoffsetBack/2){
//			int off = -_cloud.getX()-_xoffsetBack/2;
//			_cloud.translate(_xoffsetBack/2+off+_cloudNext.getX()+_cloud.getImage().getWidth()*2, 0);
//		}
//		if(_cloudNext.getX()<-_xoffsetBack/2){
//			int off = -_cloudNext.getX()-_xoffsetBack/2;
//			_cloudNext.translate(_xoffsetBack/2+off+_cloud.getX()+_cloud.getImage().getWidth()*2, 0);
//		}
//		
//		if(_fog.getX()<-_xoffsetBack){
//			int off = -_fog.getX()-_xoffsetBack;
//			_fog.translate(_xoffsetBack+off+_fogNext.getX()+_fog.getImage().getWidth()*2, 0);
//		}
//		if(_fogNext.getX()<-_xoffsetBack){
//			int off = -_fogNext.getX()-_xoffsetBack;
//			_fogNext.translate(_xoffsetBack+off+_fog.getX()+_fog.getImage().getWidth()*2, 0);
//		}

	}
	
	public void drawFog(Graphics2D g, double delta){
		_fog.translate(-_fogSpeed*delta, 0);
		_fogNext.translate(-_fogSpeed*delta, 0);
		Composite original = g.getComposite();
        AlphaComposite ac1= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.1f);
        g.setComposite(ac1);
		_fog.show(g);
		_fogNext.show(g);
		g.setComposite(original);
	}
	public float getBackgroundSpeed(){
		return _backgroundSpeed;
	}
	
	public static float getMoveX(){
		return _moveOffset;
	}
	
	public void setLocked(boolean lock){
		_locked = lock;
	}
	
	public boolean isLocked(){
		return _locked;
	}
}

