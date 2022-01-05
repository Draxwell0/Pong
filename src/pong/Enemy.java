package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	
	public double x,y;
	public int width, height;
	public double speed = 0.037;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 5;
	}
	
	public void update() {
		
		x+= (Game.ball.x - x - 15) * speed;
		
		if(x + width > Game.width) {
			x = Game.width - width;
		} else if(x < 0) {
			x = 0;
		}
		
		

		
		}
		
	
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect((int)x, (int)y, width, height);
	}
}
