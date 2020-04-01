package sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import config.Config;
import utils.XMLHandler;
/**
* Das ist der Animator. Es beinhaltet alle Funktionen um einen Animierten Sprite zu behandeln.
* @author  Eduard Heller
* @version 1.0
*/
public class Animator extends Transform implements ActionListener{
	
	private HashMap<String, Animation> _frames;
	private Timer _tm;
	private int _currentFrame = 0;
	private int _maxFrame = 0;
	private Animation _currentAnim;
	private boolean _originSetted = false;
	public static HashMap<String,ArrayList<GameAnimations>> gameAnimations = new HashMap<String,ArrayList<GameAnimations>>();
	/**
	 * Konstruktor der Klasse Animator
	 * @param speed (in ms). Geschwindigkeit in der der Animator abspielen soll 
	 */
	public Animator(int speed){
		super();
		_frames = new HashMap<String, Animation>();
		_tm = new Timer(speed,this);
		_tm.start();
		translate(0, 0);
	}
	
	/**
	 * Anderer Konstruktor der die Animationen entsprechend dem Parameter aus der XML lädt
	 * @param name Wurzelname der Animation
	 */
	public Animator(String name){
		super();
		_frames = new HashMap<String, Animation>();
		_tm = new Timer(120,this);
		_tm.start();
				
		for(int i = 0;i<=gameAnimations.get(name).size()-1;i++){
			this.addAnimation(gameAnimations.get(name).get(i).animations, gameAnimations.get(name).get(i).animname);
		}
	
		
	}
	
	/**
	 * Fügt eine Animation zum Animator hinzu
	 * @param anim AnimationsObjekt 
	 * @param name Name der Animation
	 */
	public void addAnimation(Animation anim, String name){
		
		_frames.put(name,anim);
	}
	
	public void setSpeed(int speed){
		_tm.setDelay(speed);
	}

	/**
	 * Setzt eine neue aktuelle Animation zum Animator hinzu
	 * @param name Name der Animation
	 */
	public void setAnimation(String name){
		if(_frames.get(name)!=null){
			_currentFrame = 0;
			_currentAnim = _frames.get(name);
			Image img = _currentAnim.getImages().get(_currentFrame);
			if(!_originSetted){
				//setOrigin(img.getImage().getWidth()/2,img.getImage().getHeight()/2);
				_originSetted = true;
			}

			_maxFrame = _currentAnim.getFrameCount()-1;	
		}else{
			System.out.println("ERROR: Animation mit Name "+name+ " existiert nicht!");
		}
	}
	
	/**
	 * Fragt nach ob eine Animation zurzeit abspielt.
	 * @param name Name der Animation
	 * @return Wahr oder Falsch
	 */
	public boolean isPlaying(String name){
		return (_currentAnim==_frames.get(name));
	}
	
	/**
	 * Zeigt die Animation auf den Bildschirm an
	 * @param g Graphics2D Objekt
	 */
	public void show(Graphics2D g){
		if(_currentAnim.getImages().get(_currentFrame) != null){
			Image img = _currentAnim.getImages().get(_currentFrame);
			g.drawImage(img.getImage(),getTransform(),null);
		}else{
			System.out.println("ERROR: Animation kann nicht gerendert werden da NULL");
		}
	}
	
	/**
	 * Timerfunktion der Animation. Behandelt die Frames
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		_currentFrame++;
		if(_currentFrame>_maxFrame){
			_currentFrame = 0;
		}
	}
	
	/**
	 * 
	 * @return Derzeitiger Frame der derzeitigen Animation
	 */
	public int getCurrentFrame(){
		return _currentFrame;
	}

	/**
	 * 
	 * @return Maximal Frameanzahl der derzeitigen Animation
	 */
	public int getMaxFrame(){
		return _maxFrame;
	}
	
}
