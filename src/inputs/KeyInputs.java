package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.Gpanel;
public class KeyInputs implements KeyListener{
	private Gpanel gpanel;
	public KeyInputs(Gpanel gpanel)
	{
		this.gpanel=gpanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(Gamestate.state)
		{
		case MENU:
			gpanel.getGeme().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gpanel.getGeme().getPlaying().keyReleased(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(Gamestate.state)
		{
		case MENU:
			gpanel.getGeme().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gpanel.getGeme().getPlaying().keyPressed(e);
			break;
		case OPTIONS:
			gpanel.getGeme().getGameOptions().keyPressed(e);
		default:
			break;
		
		}
		
		}
	}


