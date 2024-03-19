package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Geme;
import utilz.LoadSave;
import static utilz.Constants.UI.UrmButtons.*;

public class PauseOverlay {
	
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX,bgY,bgW,bgH;
	private UrmButton menuB,replayB,unpauseB;
	private AudioOptions audiooptions;
	public PauseOverlay(Playing playing)
	{
		this.playing =playing;
		loadBackground();
		audiooptions=playing.getGeme().getAudiooptions();
		createUrmButtons();
	}
	
	private void createUrmButtons() {
		int menuX=(int)(313*Geme.SCALE);
		int replayX=(int)(387*Geme.SCALE);
		int unpauseX=(int)(462*Geme.SCALE);
		int bY=(int)(325*Geme.SCALE);
		
		menuB=new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB=new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB=new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);

		
	}

	

	private void loadBackground() {
		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW=(int) (backgroundImg.getWidth()*Geme.SCALE);
		bgH=(int) (backgroundImg.getHeight()*Geme.SCALE);
		bgX=Geme.GAME_WIDTH/2-bgW/2;
		bgY=(int) (25*Geme.SCALE);
	}

	public void update()
	{
		
		
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		audiooptions.update();
	}
	public void draw(Graphics g)
	{
		//background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		//urm buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		audiooptions.draw(g);
	}
	
	
	public void mousePressed(MouseEvent e) {
		
		if(isIn(e,menuB))
			menuB.setMousePressed(true);
		else if(isIn(e,replayB))
			replayB.setMousePressed(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMousePressed(true);
		else
			audiooptions.mousePressed(e);
	}


	public void mouseReleased(MouseEvent e) {
		
		if(isIn(e,menuB))
		{
			if(menuB.isMousePressed())
			{
				playing.resetAll();
				Gamestate.state=Gamestate.MENU;
				playing.unPauseGame();
			}
		}else if(isIn(e,replayB))
		{
			if(replayB.isMousePressed())
			{
				
				playing.resetAll();
				playing.unPauseGame();
			}	
		}else if(isIn(e,unpauseB))
		{
			if(unpauseB.isMousePressed())
				playing.unPauseGame();
		}
		else
			audiooptions.mousePressed(e);
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		if(isIn(e,menuB))
			menuB.setMouseOver(true);
		else if(isIn(e,replayB))
			replayB.setMouseOver(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMouseOver(true);
		else
			audiooptions.mouseMoved(e);
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private boolean isIn(MouseEvent e,PauseButton b)
	{
		return b.getBounds().contains(e.getX(),e.getY());
	}
	{
		
	}
	
	
}
