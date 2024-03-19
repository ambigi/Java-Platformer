package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyInputs;
import inputs.MouseInputs;

import static main.Geme.*;

public class Gpanel extends JPanel{
	private MouseInputs mouseInputs;
	private Geme geme;
	public Gpanel(Geme geme)
	{
		mouseInputs= new MouseInputs(this);
		this.geme=geme;
		setPanelSize();
		addKeyListener(new KeyInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setPreferredSize(size);
		System.out.println("size: "+GAME_WIDTH+" : "+GAME_HEIGHT);
	}
	
	public void updateGame()
	{
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		geme.render(g);
	}
	
	public Geme getGeme()
	{
		return geme;
	}
	
	}

