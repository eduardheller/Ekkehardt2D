package sprite;

import java.awt.Graphics2D;

/**
* Das ist die Spriteklasse. Es beinhaltet die Funktion einen Sprite zu zeichnen und die Transformationen auszuführen.
* Es ist sozusage der kleiner Bruder von dem Animator, da es keine Animationen enthählt
* @author  Eduard Heller
* @version 1.0
*/
public class Sprite extends Transform{

	private Image _img;
	
	/**
	 * Konstruktor der Spriteklasse
	 * @param path Ort des Bildes
	 */
	public Sprite(String path){
		super();
		_img = new Image(path);
		setOrigin(_img.getWidth()/2,_img.getHeight()/2);
		translate(0, 0);
	}
	
	/**
	 * Anderer Konstruktor der Spriteklasse
	 * @param img Instanz der Klasse Image
	 */
	public Sprite(Image img){
		super();
		_img = img;
		setOrigin(_img.getWidth()/2,_img.getHeight()/2);
		translate(0, 0);
	}
	
	/**
	 * Zeigt den Sprite auf den Bildschirm an
	 * @param g Graphics2D Objekt
	 */
	public void show(Graphics2D g){
		g.drawImage(_img.getImage(), getTransform(), null);
	}
	
	public void show(Graphics2D g, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2){
		g.drawImage(_img.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}
	
	
	/**
	 * @return Image Objekt
	 */
	public Image getImage(){
		return _img;
	}


	
	
}
