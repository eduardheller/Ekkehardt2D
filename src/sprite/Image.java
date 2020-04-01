package sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Das ist die Imageklasse. Es lädt ein Bild aus dem Classloader und speichert es als BufferedImage ab.
* @author  Eduard Heller
* @version 1.0
*/
public class Image {
	
	private BufferedImage _img;
	private int _width;
	private int _height;
	
	/**
	 * Konstuktor der Imageklasse
	 * @param path Zielort des Bildes
	 */
	public Image(String path){
		try {

			_img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_width = _img.getWidth(null);
		_height = _img.getHeight(null);
		_img = makeTransparent(_img);
	}
	
	public Image(BufferedImage img){
		_width = img.getWidth(null);
		_height = img.getHeight(null);
		_img = makeTransparent(img);
	}
	
	 private BufferedImage makeTransparent(BufferedImage tmpImage)
	  {
	    int h=tmpImage.getHeight(null);
	    int w=tmpImage.getWidth(null);
	 
	    BufferedImage resultImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
	 
	    // assume the upperleft corner of the original image is a transparent pixel
	    Color trans = new Color(255,0,255);
	    Color transOther = new Color(252,0,255);
	    int transparentColor=trans.getRGB();
	    int transparentColorOther=transOther.getRGB();
	    for (int y=0;y<h;y++)
	      for (int x=0;x<w;x++)
	      {
	        int color=tmpImage.getRGB(x,y);
	        if (color==transparentColor) color=color & 0x00FFFFFF; // clear the alpha flag
	        else if(color==transparentColorOther) color=color & 0x00FFFFFF;
	        resultImage.setRGB(x,y,color);
	      }
	 
	    return resultImage;
	  }
	
	/**
	 * @return BufferImage Objekt
	 */
	public BufferedImage getImage(){
		return _img;
	}
	
	/**
	 * @return Breite des Bildes
	 */
	public int getWidth(){
		return _width;
	}
	
	/**
	 * @return Höhe des Bildes
	 */
	public int getHeight(){
		return _height;
	}
	
}
