package states;

import java.awt.Graphics2D;

public class MenuBeendenState extends State{

		@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Graphics2D g, double delta) {
		System.exit(0);
	}
}
