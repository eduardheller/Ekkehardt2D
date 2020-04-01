package utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import sprite.Image;
import sprite.Sprite;

public class BitmapFont {
		
	private Sprite _spr;
	private int _slicex;
	private int _slicey;
	private boolean _ignore;
	public BitmapFont(Sprite spr,int sx,int sy, boolean ignore){
		_spr = spr;
		_slicex = sx;
		_slicey = sy;
		_ignore = ignore;
	}
	
	public void drawString(Graphics2D g,String msg, int x ,int y){
		msg = msg.toUpperCase();
		for(int i = 0; i< msg.length(); i++){
			int indexofimg = (msg.charAt(i)-32);
			int ycoord = (int)(indexofimg / 16);
			indexofimg -= ycoord*16;
			int newx = 0;
			if(_ignore){
				newx = x;
			}else{
				newx = x-_getCenterofStr(msg);
			}
			
			_spr.show(g, newx, y, newx+_slicex, y+_slicey, indexofimg*_slicex,  ycoord*_slicey, (indexofimg*_slicex)+_slicex, (ycoord*_slicey)+_slicey);
			x += _slicex;
		}
	}
	
	private int _getCenterofStr(String str){
		return (str.length()*_slicex)/2;
	}
}
