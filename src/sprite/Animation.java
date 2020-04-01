package sprite;

import java.util.ArrayList;

/**
* Das ist eine Animation. Es behandelt lediglich eine ArrayList von Sprites. Wird vom Animator benötigt.
* @author  Eduard Heller
* @version 1.0
*/
public class Animation {

	private ArrayList<Image> _images;
	private int _frames;
	
	/**
	 * Konstruktor der Animationklasse
	 * @param img ArrayList von Image Objekten
	 */
	public Animation(ArrayList<Image> images){
		_images = images;
		_frames = images.size();
	}
	
	/**
	 * @return die Anzahl der Frames
	 */
	public int getFrameCount(){
		return _frames;
	}
	
	/**
	 * @return ArrayListe der Imageobjekte
	 */
	public ArrayList<Image> getImages(){
		return _images;
	}
}
