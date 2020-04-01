package config;

import java.awt.event.*;

/**
* Configklasse die statische konstanten enthält, die überall im Spiel benutzt werden können.
* @version 1.0
*/
public class Config {
	// Spielstrings
	public final static String TITEL = "Ekkerhardt";
	public final static String VERSION = "v0.010";
	
	// Graphikeinstellungen
	public final static byte BUFFERS = 2;
	public final static int FPS = 60;
	
	// Debugvariablen
	public final static boolean DEBUG = false;
	
	public final static double TIMER_DIF = 15.0;
	public final static String ANIMATION_FILE = "Animationlist.xml";
	public final static String LEVEL_FILE = "Level.xml";
	public final static String HIGHSCORE_FILE = "highscore";
	
	public final static int PLAYER1_UP = KeyEvent.VK_W;
	public final static int PLAYER1_RIGHT = KeyEvent.VK_D;
	public final static int PLAYER1_LEFT = KeyEvent.VK_A;
	public final static int PLAYER1_DOWN = KeyEvent.VK_S;
	public final static int PLAYER1_JUMP = KeyEvent.VK_SPACE;
	public final static int PLAYER1_MELEE = KeyEvent.VK_F;
	public final static int PLAYER1_RANGE = KeyEvent.VK_T;
	
	public final static int PLAYER2_UP = KeyEvent.VK_UP;
	public final static int PLAYER2_RIGHT = KeyEvent.VK_RIGHT;
	public final static int PLAYER2_LEFT = KeyEvent.VK_LEFT;
	public final static int PLAYER2_DOWN = KeyEvent.VK_DOWN;
	public final static int PLAYER2_JUMP = KeyEvent.VK_C;
	public final static int PLAYER2_MELEE = KeyEvent.VK_V;
	public final static int PLAYER2_RANGE = KeyEvent.VK_X;
	
	
	public final static int BOSS_LEVEL = 5;
	
	public final static String ENEMY_ZOMBIE_1 = "enemy1";
	public final static String ENEMY_ZOMBIE_2 = "enemy3";
	public final static String ENEMY_GHOUL = "enemy2";
	public final static String ENEMY_BOSS = "boss";
	public final static String ENEMY_HOUND = "hounds";
	public final static String ENEMY_ZBOSS = "zboss";
	
	public final static String PLAYER_JACK = "jack";
	public final static String PLAYER_JASMIN = "jasmin";
	public final static String PLAYER_CHRIS = "chris";
	
	public final static int PLAYER_LIFE = 2;
	
	public final static int BOUND_LEFT = 50;
	public final static int BOUND_RIGHT = 555;
	public final static int BOUND_TOP = 150;
	public final static int BOUND_BOTTOM = 330;
	
	public final static int Z_FIGHT_DIST = 30;
	
	public final static int HOUND_DIFF = 8;
	
}
