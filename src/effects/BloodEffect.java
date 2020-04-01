package effects;

import java.util.ArrayList;

import sprite.Animation;
import sprite.Image;

public class BloodEffect extends Effect{
	
	private Animation _animation;
	private static int _xoff = -20;
	private static int _yoff = -50;
	
	public BloodEffect(int x, int y, boolean isNotFlipped){
		super(x,y,_xoff,_yoff,50,isNotFlipped);
		
		ArrayList<Image> img = new ArrayList<Image>();
		img.add(new Image("res/b0.png"));
		img.add(new Image("res/b1.png"));
		img.add(new Image("res/b4.png"));
		img.add(new Image("res/b5.png"));
		img.add(new Image("res/b6.png"));
		_animation = new Animation(img);
		
		getAnimator().addAnimation(_animation,"effect");
		getAnimator().setAnimation("effect");
		getAnimator().scale(0.5,0.5);

	}

}
