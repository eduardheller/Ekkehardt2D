package world;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sprite.Animation;
import sprite.Animator;
import sprite.GameAnimations;
import sprite.Image;
import utils.XMLHandler;
import config.Config;

public class RessourceLoader {
	
	public void loadAnimations(){
		XMLHandler rxml2 = new XMLHandler(Config.ANIMATION_FILE);
		String animSpeed = rxml2.getDoc().getDocumentElement().getAttribute("speed");
		int speed  = Integer.parseInt(animSpeed);
		NodeList nList2 = rxml2.getDoc().getDocumentElement().getChildNodes();
		Element child2;
		NodeList iList;
		Animation anim;
		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<GameAnimations> anims = new ArrayList<GameAnimations>();
		String aniName = null;
		Image img;
		
		for (int i = 0; i < nList2.getLength(); i++) {
			if (nList2.item(i).getNodeType() == 1) {
				
				String keyName = nList2.item(i).getNodeName();
				
				NodeList nList3 = nList2.item(i).getChildNodes();
				
				for (int j = 0; j < nList3.getLength(); j++) {
					if (nList3.item(j).getNodeType() == 1) {
						aniName = nList3.item(j).getNodeName();
						
						Element child3 = (Element) nList3.item(j);
						iList = child3.getChildNodes();
						for(int k = 0; k < iList.getLength(); k++){
							if (iList.item(k).getNodeType() == 1){
								System.out.println("LOAD Image: "+iList.item(k).getTextContent());
								img = new Image("res/" + iList.item(k).getTextContent());
								images.add(img);
							}
						}
						anims.add(new GameAnimations(aniName,new Animation(images)));
						images = new ArrayList<Image>();
					}

					
				}
				Animator.gameAnimations.put(keyName, anims);
				anims = new ArrayList<GameAnimations>();
			}
			
		}
	}
}
