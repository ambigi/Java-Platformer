package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.Gpanel;

public class MouseInputs implements MouseListener,MouseMotionListener {
	private Gpanel gpanel;
	public MouseInputs(Gpanel gpanel)
	{
		this.gpanel=gpanel;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(Gamestate.state)
		{
		case MENU:
			gpanel.getGeme().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gpanel.getGeme().getPlaying().mouseMoved(e);
			break;
		case OPTIONS:
			gpanel.getGeme().getGameOptions().mouseMoved(e);
		default:
			break;
		
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestate.state)
		{
		case PLAYING:
			gpanel.getGeme().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(Gamestate.state)
		{
		case MENU:
			gpanel.getGeme().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gpanel.getGeme().getPlaying().mousePressed(e);
			break;
		case OPTIONS:
			gpanel.getGeme().getGameOptions().mousePressed(e);
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(Gamestate.state)
		{
		case MENU:
			gpanel.getGeme().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gpanel.getGeme().getPlaying().mouseReleased(e);
			break;
		case OPTIONS:
			gpanel.getGeme().getGameOptions().mouseReleased(e);
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
