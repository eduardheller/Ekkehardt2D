package states;

import java.awt.Color;
import java.awt.Graphics2D;





import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sprite.Animation;
import sprite.Animator;
import sprite.GameAnimations;
import sprite.Image;
import utils.XMLHandler;
import config.Config;
import world.Background;
import world.World;
import character.*;
import effects.BloodEffect;


public class LarsEddyState extends State{


	private World _world;
	
	@Override
	public void init() {
		_world = new World(1);
	}

	@Override
	public void update(Graphics2D g, double delta) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		_world.update(g,delta);
		
		g.setColor(Color.white);
		g.drawString("World Kooardinate X: "+_world.getX(), 20, 120);
	}

}
