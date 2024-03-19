package ui;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Geme;

public class AudioOptions {
	private SoundButton musicButton,sfxButton;
	public AudioOptions()
	{
		createSoundButtons();
	}
	private void createSoundButtons() {
		int soundX=(int)(450*Geme.SCALE);
		int musicY=(int)(140*Geme.SCALE);
		int sfxY=(int)(186*Geme.SCALE);
		musicButton=new SoundButton(soundX, musicY,SOUND_SIZE, SOUND_SIZE);
		sfxButton=new SoundButton(soundX, sfxY,SOUND_SIZE, SOUND_SIZE);
	}
	public void update()
	{
		musicButton.update();
		sfxButton.update();
	}
	public void draw(Graphics g)
	{
		//sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
	}
	public void mousePressed(MouseEvent e) {
		if(isIn(e,musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e,sfxButton))
			sfxButton.setMousePressed(true);
	}


	public void mouseReleased(MouseEvent e) {
		if(isIn(e,musicButton))
		{
			if(musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());
		}
		else if(isIn(e,sfxButton))
		{
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		
		if(isIn(e,musicButton))
			musicButton.setMouseOver(true);
		else if(isIn(e,sfxButton))
			sfxButton.setMouseOver(true);
		
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private boolean isIn(MouseEvent e,PauseButton b)
	{
		return b.getBounds().contains(e.getX(),e.getY());
	}
}
