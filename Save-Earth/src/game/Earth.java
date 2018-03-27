package game;

import java.awt.Color;
import java.awt.Graphics;

public class Earth extends GameObject {

	public Earth(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x, y, 150, 150);
	}

}
