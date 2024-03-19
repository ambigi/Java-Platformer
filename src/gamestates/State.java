package gamestates;

import java.awt.event.MouseEvent;

import main.Geme;
import ui.MenuButton;

public class State {

	protected Geme geme;
	public State(Geme geme) {
		this.geme=geme;
	}
	
	public boolean isIn(MouseEvent e,MenuButton mb)
	{
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	public Geme getGeme()
	{
		return geme;
	}
}
