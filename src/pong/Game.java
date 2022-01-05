package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	
	public static int width = 160;
	public static int height = 120;
	public static int scale = 5;
	public boolean isRunning = true;
	private Thread thread;
	
	public static  Player player;
	public static Enemy enemy;
	public static Ball ball;
	
	public BufferedImage layer = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
	
	
	public Game() {
		initFrame();
		player = new Player(90, height - 15);
		enemy = new Enemy(90, 0);
		ball = new Ball(100, height/2 - 3);
		this.addKeyListener(this);
	}
	
	public void initFrame() {
		JFrame frame = new JFrame("Pong");
		frame.setPreferredSize(new Dimension(width*scale, height*scale));
		frame.pack();
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void update() {
		player.update();
		enemy.update();
		ball.update();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = layer.getGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		player.render(g);
		enemy.render(g);
		ball.render(g);
		

		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, width*scale, height*scale, null);
		bs.show();
		
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}


	public void run() {
		long lastTime = System.nanoTime();
		double ticks = 60.0;
		double ns = 1000000000 / ticks;
		int frames = 0;
		double delta = 0;
		double timer = System.currentTimeMillis();
			while(isRunning) {
				requestFocus();
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				
				if(delta >= 1) {
					update();
					render();
					frames++;
					delta--;
				}
				
				if(System.currentTimeMillis() - timer >= 1000) {
					//System.out.println("FPS = " + frames);
					frames = 0;
					timer += 1000;
				}
							
			}
		
	}


	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
	}


	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}

		
	}


	public void keyTyped(KeyEvent arg0) {

		
	}

}
