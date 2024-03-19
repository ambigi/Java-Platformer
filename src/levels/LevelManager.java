package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Geme;
import utilz.LoadSave;

public class LevelManager {

	private Geme geme;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex=0;
	
	public LevelManager(Geme geme)
	{
		this.geme=geme;
		importOutsideSprites();
		levels=new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel()
	{
		lvlIndex++;
		if(lvlIndex>=levels.size())
		{
			lvlIndex=0;
			System.out.println("big sad");
			Gamestate.state=Gamestate.MENU;
		}
		
		Level newlevel=levels.get(lvlIndex);
		geme.getPlaying().getEnemyManager().LoadEnemies(newlevel);
		geme.getPlaying().getPlayer().loadLvlData(newlevel.getLevelData());
		geme.getPlaying().setMaxLvlOffset(newlevel.getLvlOffset());
		geme.getPlaying().getObjectManager().loadObjects(newlevel);
	}
	
	private void buildAllLevels() {

		BufferedImage[] allLevels=LoadSave.getAllLevels();
		for(BufferedImage img:allLevels)
		levels.add(new Level(img));
	}

	private void importOutsideSprites() {
		BufferedImage img =LoadSave.GetSpriteAtlas(LoadSave.Level_Atlas);	
		levelSprite=new BufferedImage[48];
		for(int j=0;j<4;j++)
			for(int i=0;i<12;i++)
			{
				int index =j*12+i;
				levelSprite[index]=img.getSubimage(i*32, j*32, 32, 32);
			}
		
	}

	public void draw(Graphics g,int lvlOffset)
	{
		for(int j=0;j<Geme.TILES_IN_HEIGHT;j++)
			for(int i=0;i<levels.get(lvlIndex).getLevelData()[0].length;i++)
			{
				int index=levels.get(lvlIndex).getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Geme.TILES_SIZE*i-lvlOffset, Geme.TILES_SIZE*j,Geme.TILES_SIZE,Geme.TILES_SIZE, null);
			}
		
	}
	public void update()
	{
		
	}
	
	public Level getCurrentLevel()
	{
		return levels.get(lvlIndex);
	}
	public int getAmountOfLevels()
	{
		return levels.size();
	}
}
