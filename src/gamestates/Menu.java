package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Geme;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods{

	private MenuButton[] buttons=new MenuButton[3];
	private BufferedImage backgroundImg,backgroundImgPink;
	private int menuX,menuY,menuWidth,menuHeight;
	
	public Menu(Geme geme) {
		super(geme);
		loadButtons();
		loadBackground();
	}

	private void loadBackground() {
		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		backgroundImgPink=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		menuWidth=(int)(backgroundImg.getWidth()*Geme.SCALE);
		menuHeight=(int)(backgroundImg.getHeight()*Geme.SCALE);
		menuX=Geme.GAME_WIDTH/2 - menuWidth/2;
		menuY=(int)(45*Geme.SCALE);
	}

	private void loadButtons() {
		buttons[0]=new MenuButton(Geme.GAME_WIDTH/2,(int)(150*Geme.SCALE), 0, Gamestate.PLAYING);
		buttons[1]=new MenuButton(Geme.GAME_WIDTH/2,(int)(220*Geme.SCALE), 1, Gamestate.OPTIONS);
		buttons[2]=new MenuButton(Geme.GAME_WIDTH/2,(int)(290*Geme.SCALE), 2, Gamestate.QUIT);
		
	}

	@Override
	public void update() {
		for(MenuButton mb:buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImgPink, 0, 0, Geme.GAME_WIDTH, Geme.GAME_HEIGHT, null);
		g.drawImage(backgroundImg, menuX, menuY,menuWidth,menuHeight, null);
		for(MenuButton mb:buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb:buttons)
		{
			if(isIn(e,mb))
				mb.setMousePressed(true);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb:buttons)
			if(isIn(e,mb))
			{
				if(mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		resetButtons();
		
	}

	private void resetButtons() {
		
		for(MenuButton mb:buttons)
			mb.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb:buttons)
			mb.setMouseOver(false);
		for(MenuButton mb:buttons)
			if(isIn(e,mb))
			{
				mb.setMouseOver(true);
				break;
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			Gamestate.state=Gamestate.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}
