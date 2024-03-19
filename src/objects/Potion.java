package objects;

import main.Geme;

public class Potion extends GameObject {

	private float hoverOffset;
	private int maxHoverOffset,hoverDir=1;
	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation=true;
		initHitbox(7,14);
		xDrawOffset=(int)(3*Geme.SCALE);
		yDrawOffset=(int)(2*Geme.SCALE);
		
		maxHoverOffset=(int)(10*Geme.SCALE);
	}

	public void update()
	{
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset+=(0.075f*Geme.SCALE*hoverDir);//this should be below methinks
		if(hoverOffset>=maxHoverOffset)
			hoverDir=-1;
		else if(hoverOffset<0)
			hoverDir=1;
		hitbox.y=y+hoverOffset;
		
	}
}
