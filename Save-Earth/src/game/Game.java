package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4584388369897487885L;
	
	public static final int WIDTH=800, HEIGHT=WIDTH/12*9;
	private Thread thread;
	private boolean running=false;
	
	private Handler handler;
	
	public Game(){
		new Window(WIDTH, HEIGHT, "Save Earth", this);
		handler = new Handler();
		
		handler.addObject(new Earth((Game.WIDTH-150)/2, (Game.HEIGHT-150)/2,ID.Earth)); 
	}

	public synchronized void start(){
		thread= new Thread(this);
		thread.start();
		running=true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running=false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime= System.nanoTime();
		double amountOfTicks= 60.0;
		double ns= 1000000000 / amountOfTicks;
		double delta =0;
		long timer = System.currentTimeMillis();
		int frames=0;
		while(running){
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			while(delta>=1){
				tick();
				delta--;
			}
			if(running){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
				System.out.println("FPS:  "+ frames);
				frames=0;
			}
		}
		stop();
	}
	
	private void tick(){
		handler.tick();
	}
	
	private void render(){
		BufferStrategy bs= this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g= bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]){
		new Game();
	}
	
}
