package main;

import java.awt.Graphics;

import gamestates.GameOptions;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import ui.AudioOptions;

public class Geme implements Runnable{
	private GWin gwin;
	private Gpanel gpanel;
	private Thread gthread;
	private final int FPS_SET=120;
	private final int UPS_SET=200;
	
	private Playing playing;
	private Menu menu;
	private GameOptions gameOptions;
	private AudioOptions audiooptions;
	
	public final static int TILES_DEFAULT_SIZE=32;
	public final static float SCALE=1.5f;
	public final static int TILES_IN_WIDTH=26;
	public final static int TILES_IN_HEIGHT=14;
	public final static int TILES_SIZE=(int) (TILES_DEFAULT_SIZE*SCALE);
	public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
	public final static int GAME_HEIGHT=TILES_SIZE*TILES_IN_HEIGHT;
	public Geme()
	{
		initClasses();
		gpanel=new Gpanel(this);
		gwin= new GWin(gpanel);
		gpanel.setFocusable(true);
		gpanel.requestFocus();
		gameLoop();
		
	}
	private void initClasses() {
		audiooptions=new AudioOptions();
		menu = new Menu(this);
		playing = new Playing(this);
		gameOptions =new GameOptions(this);
	}
	private void gameLoop()
	{
		gthread=new Thread(this);
		gthread.start();
	}
	public void update()
	{
		
		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
			gameOptions.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;
		
		}
	}
	public void render(Graphics g)
	{
		switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case OPTIONS:
			gameOptions.draw(g);
			break;
		default:
			break;
		
		}
		
		
			}
	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		//will be greater than 1.0 when the time since the last update is equal or
		//more than the timeperupdate
		//used to prevent loss of frames in an update cycle
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gpanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}

	
	public void windowFocusLost() {
		if(Gamestate.state==Gamestate.MENU)
			playing.getPlayer().resetDirBooleans();
	}
	public Menu getMenu()
	{
		return menu;
	}
	public Playing getPlaying()
	{
		return playing;
	}
	public GameOptions getGameOptions() {
		return gameOptions;
	}
	public AudioOptions getAudiooptions() {
		return audiooptions;
	}
	
}