package effects;

import java.awt.Graphics2D;
import sprite.Animator;


public class Effect {
	
	private Animator _animator;
	
	public Effect(int x, int y, int xoff, int yoff,int speed, boolean isNotFlipped){
		_animator = new Animator(speed);
		
		if(!isNotFlipped){
			_animator.flip();
			_animator.translate(x-xoff+40, y+yoff);
		}else{
			_animator.translate(x+xoff, y+yoff);
		}
	}
	
	public void update(Graphics2D g){
		_animator.show(g);
	}
	
	public boolean isDone(){
		return _animator.getCurrentFrame()==_animator.getMaxFrame();
	}
	
	public Animator getAnimator(){
		return _animator;
	}
}
