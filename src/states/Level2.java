package states;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import utils.SoundEffect;
import world.World;


public class Level2 extends State{


	private World _world;
	


	@Override
	public void init() {

		_world = new World(2);
	}

	@Override
	public void update(Graphics2D g, double delta) {
		Graphics2D g2 = (Graphics2D) g;

		_world.update(g,delta);
	}

}
