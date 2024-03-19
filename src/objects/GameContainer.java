package objects;
import static utilz.Constants.ObjectConstants.*;

import main.Geme;
public class GameContainer extends GameObject{

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	private void createHitbox() {
		if(objType==BOX)
		{
			initHitbox(25, 18);
			xDrawOffset=(int)(7*Geme.SCALE);
			yDrawOffset=(int)(12*Geme.SCALE);
		}
		else
		{
			initHitbox(23, 25);
			xDrawOffset=(int)(8*Geme.SCALE);
			yDrawOffset=(int)(5*Geme.SCALE);
		}
		hitbox.y+=yDrawOffset+(int)(2*Geme.SCALE);
		hitbox.x+=xDrawOffset/2;
		
	}
	public void update()
	{
		if(doAnimation)
			updateAnimationTick();
	}

}
