package world;

import java.util.ArrayList;
import java.util.Random;

import sprite.Animator;
import utils.SoundEffect;
import character.GameObject;

public class BloodHandler {

	private SoundEffect beatSound1 = new SoundEffect("BEAT1");
	private SoundEffect beatSound2 = new SoundEffect("BEAT2");
	private SoundEffect beatSound3 = new SoundEffect("BEAT3");
	
	public BloodHandler(GameObject obj,ArrayList<Animator> _bloodList,boolean onlyBlood){
		String blood1 = "blood1";
		String blood2 = "blood2";
		String blood3 = "blood3";
		
		if(onlyBlood){
			blood2 = blood1;
			blood3 = blood1;
		}
		
		Random r = new Random();
		int value = r.nextInt(4);
		Animator blood;
		if(value == 3){
			beatSound3.play();
			blood = new Animator(blood2);
			blood.setOrigin(40, 40);
			blood.translate(obj.getX()-40, obj.getZ()-20);
			blood.setAnimation("idle");
			blood.setSpeed(20);
			blood.getTransform().scale(2, 2);
			if(onlyBlood){
				if(!obj.getTransform().isFlippedRight()){
					blood.flip();
				}
			}

		}else{
			if(value==2){
				blood = new Animator(blood3);
				blood.setOrigin(40, 40);
				blood.translate(obj.getX()-40, obj.getZ()-20);
				blood.setAnimation("idle");
				blood.setSpeed(20);
				blood.getTransform().scale(2, 2);
				beatSound2.play();
				if(onlyBlood){
					if(!obj.getTransform().isFlippedRight()){
						blood.flip();
					}
				}
			}else if(value==3){
				blood = new Animator(blood1);
				blood.setOrigin(40, 40);
				blood.translate(obj.getX()-40, obj.getZ()-20);
				blood.setAnimation("idle");
				blood.setSpeed(20);
				blood.getTransform().scale(2, 2);
				beatSound2.play();
				if(!obj.getTransform().isFlippedRight()){
					blood.flip();
				}
			}else{
				blood = new Animator(blood1);
				blood.setOrigin(40, 40);
				blood.translate(obj.getX()-40, obj.getZ()-20);
				blood.setAnimation("idle");
				blood.setSpeed(20);
				blood.getTransform().scale(2, 2);
				beatSound1.play();
				if(!obj.getTransform().isFlippedRight()){
					blood.flip();
				}
			}


		}
		_bloodList.add(blood);
	}
}
