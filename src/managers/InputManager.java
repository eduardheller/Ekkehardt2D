package managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Referenz: http://gpsnippets.blogspot.de/2008/03/keyboard-input-polling-system-in-java.html
/**
* InputManagerklasse die anstatt Events, eine Pollinglösung findet.
* Dadurch kann eine Gameloop besser strukturiert die Eingaben behandeln
*/
public class InputManager implements KeyListener {

    private static InputManager singleton = null;
    
    protected InputManager() {
    }

    public static InputManager getInstance() {
         if(singleton==null) {
              singleton = new InputManager();
         }
         return singleton;
    }
	
	private int[] keys = new int[256];
	
	private boolean[] key_state_up = new boolean[256];
	private boolean[] key_state_down = new boolean[256];
	
	private boolean keyPressed = false;
	
	private boolean keyReleased = false; 
	
	private String keyCache = "";
	
	private char keyChar;
	
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = (int) System.currentTimeMillis();
			key_state_down[e.getKeyCode()] = true;
			key_state_up[e.getKeyCode()] = false;
			keyPressed = true;
			keyReleased = false;
		}
	}

	public char getKeyChar(){
		return keyChar;
	}
	
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = 0;
			key_state_up[e.getKeyCode()] = true;
			key_state_down[e.getKeyCode()] = false;
			keyChar = e.getKeyChar();
			keyPressed = false;
			keyReleased = true;
		}
	}

	public void keyTyped(KeyEvent e) {	
		keyCache += e.getKeyChar();
		
	}
	
	public boolean isKeyDown( int key ) {
		return key_state_down[key];
	}
	
	public boolean isKeyUp( int key ) {
		return key_state_up[key];
	}

	public boolean isAnyKeyDown() {
		return keyPressed;
	}
	
	public boolean isAnyKeyUp() {
		return keyReleased;
	}
	
	public void update() {
		key_state_up = new boolean[256];
		keyReleased = false;
		if( keyCache.length() > 1024 ) {
			keyCache = "";
		}
	}
	
} 
