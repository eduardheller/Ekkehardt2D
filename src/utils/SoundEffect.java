package utils;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

   

public class SoundEffect {
	
	private Clip clip;
	private String path;
	
	
	public SoundEffect(String effect) {

		if (effect.equals("SHOOT")) path = "res/sounds/mshgun.wav";
		else if (effect.equals("BEAT1")) path = "res/sounds/beat1.wav";
		else if (effect.equals("BEAT2")) path = "res/sounds/beatb.wav";
		else if (effect.equals("BEAT3")) path = "res/sounds/beatc.wav";
		else if (effect.equals("BLOCK")) path = "res/sounds/block.wav";
		else if (effect.equals("DIE1")) path = "res/sounds/die2.wav";
		else if (effect.equals("DIE2")) path = "res/sounds/die3.wav";
		else if (effect.equals("STARTVOICE")) path = "res/sounds/timeclen.wav";
		else if (effect.equals("HOUNDS")) path = "res/sounds/houl.wav";
		else if (effect.equals("ZOMBIE")) path = "res/sounds/aah.wav";
		else if (effect.equals("CHOICE")) path = "res/sounds/goodch.wav";
		else if (effect.equals("SELECT")) path = "res/sounds/select.wav";
		else if (effect.equals("HOUNDS")) path = "res/sounds/houl.wav";
		else if (effect.equals("ZOMBIE")) path = "res/sounds/aah.wav";
		else if (effect.equals("CHOICE")) path = "res/sounds/goodch.wav";
		else if (effect.equals("SELECT")) path = "res/sounds/select.wav";
		else if (effect.equals("PUNCH")) path = "res/sounds/punch2.wav";
		else if (effect.equals("JUMP")) path = "res/sounds/jump.wav";
		else if (effect.equals("HEAL")) path = "res/sounds/money.wav";
		else if (effect.equals("AMMO")) path = "res/sounds/reload.wav";
		else if (effect.equals("GUYDIE")) path = "res/sounds/guydie.wav";
		else if (effect.equals("GIRLDIE")) path = "res/sounds/camdie.wav";
		else if (effect.equals("GO1")) path = "res/sounds/go.wav";
		else if (effect.equals("GO2")) path = "res/sounds/go1.wav";
		else if (effect.equals("MENU")) path = "res/music/menu.wav";
		else if (effect.equals("LEVEL1")) path = "res/music/lv1.wav";
		else if (effect.equals("LEVEL2")) path = "res/music/lv2.wav";
		else if (effect.equals("LEVEL3")) path = "res/music/lv3.wav";
		else if (effect.equals("LEVEL4")) path = "res/music/lv4.wav";
		else if (effect.equals("INTRO")) path = "res/music/boss.wav";
		else if (effect.equals("INTRO_HEROES")) path = "res/music/end2.wav";
		else if (effect.equals("BOSS_LAUGH")) path = "res/sounds/igorha.wav";
		else if (effect.equals("BOSS_DIE")) path = "res/sounds/igor.wav";
		else if (effect.equals("BOSS_WORLD")) path = "res/sounds/worldme.wav";
		else if (effect.equals("GAMEOVER")) path = "res/music/remix.wav";
		else if (effect.equals("BOSS_LEVEL")) path = "res/music/snake.wav";
		else if (effect.equals("BOSS_DESTROY")) path = "res/sounds/destroy.wav";
		else if (effect.equals("BOSS_END")) path = "res/sounds/bossend.wav";
		else if (effect.equals("BOSS_DEAD")) path = "res/sounds/die1.wav";
		else if (effect.equals("THATS_IT")) path = "res/sounds/thatsit.wav";
		else if (effect.equals("OUTRO")) path = "res/sounds/end3.wav";

		try {

	         URL url = this.getClass().getClassLoader().getResource(path);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	         
	         
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
		
	}

   public void play() {
	   	
         if (!clip.isRunning()) {
        	 clip.setFramePosition(0);
        	 clip.start();
         } 
   }
   
   public void loop(){
	   if (!clip.isRunning()) {
      	 clip.setFramePosition(0);
      	 clip.loop(Clip.LOOP_CONTINUOUSLY);
       } 
   }
   
   public void stop(){
	   clip.loop(0);
	   clip.stop();
	   clip.flush();
   }
   
   public boolean isRunning(){
	   return clip.isRunning();
   }

}