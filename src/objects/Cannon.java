	package objects;

import main.Geme;

public class Cannon extends GameObject{
	
	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY=y/Geme.TILES_SIZE;
		initHitbox(40,26);
		hitbox.x-=(int)(4*Geme.SCALE);
		hitbox.y+=(int)(6*Geme.SCALE);
	}
	public void update()
	{
		if(doAnimation)
			updateAnimationTick();
	}
	public int getTileY()
	{
		return tileY;
	}
	
}
