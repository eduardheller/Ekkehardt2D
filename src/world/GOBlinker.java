package world;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import sprite.Sprite;

public class GOBlinker implements ActionListener {

	private Timer _tm;
	private Sprite _spr;
	private boolean _started = false;
	private boolean _show = false;
	private int _counter = 0;
	
	public GOBlinker(Sprite spr){
		_tm = new Timer(300,this);
		_tm.start();
		_spr = spr;
		_spr.translate(300, 100);
		_spr.scale(2, 2);
	}
	
	public void start(){
		_started = true;
	}
	
	public void update(Graphics2D g){
		if(_show){
			_spr.show(g);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(_started){
			_show = !_show;
			_counter++;
			if(_counter>=10){
				_started = false;
				_counter = 0;
				_show = false;
			}
		}
	}

}
