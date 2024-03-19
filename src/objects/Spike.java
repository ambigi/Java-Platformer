package objects;

import main.Geme;

public class Spike extends GameObject{

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(32, 16);
		int xDrawOffset=0;
		int YDrawOffset=(int)(Geme.SCALE*16);
		hitbox.y+=yDrawOffset;
		
	}
	

}
