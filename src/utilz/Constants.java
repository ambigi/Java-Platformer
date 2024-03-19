package utilz;


import main.Geme;

public class Constants {
	public static final float GRAVITY=0.04f*Geme.SCALE;
	public static final int ANI_SPEED=25;
	
	public static class Projectiles
	{
		public static final int CANNON_BALL_DEFAULT_WIDTH=15;
		public static final int CANNON_BALL_DEFAULT_HEIGHT=15;
		
		public static final int CANNON_BALL_WIDTH=(int)(Geme.SCALE*
				CANNON_BALL_DEFAULT_WIDTH);
		public static final int CANNON_BALL_HEIGHT=(int)(Geme.SCALE*
				CANNON_BALL_DEFAULT_HEIGHT);
		public static final float SPEED=0.65f*Geme.SCALE;
		
	}
	
	public static class ObjectConstants {

		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;
		public static final int SPIKE = 4;
		public static final int CANNON_LEFT = 5;
		public static final int CANNON_RIGHT = 6;
		

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (Geme.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Geme.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Geme.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Geme.SCALE * POTION_HEIGHT_DEFAULT);
		
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int) (Geme.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT = (int) (Geme.SCALE * SPIKE_HEIGHT_DEFAULT);
		
		public static final int CANNON_WIDTH_DEFAULT = 40;
		public static final int CANNON_HEIGHT_DEFAULT = 26;
		public static final int CANNON_WIDTH = (int) (Geme.SCALE * CANNON_WIDTH_DEFAULT);
		public static final int CANNON_HEIGHT = (int) (Geme.SCALE * CANNON_HEIGHT_DEFAULT);
		
		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_POTION, BLUE_POTION:
				return 7;
			case BARREL, BOX:
				return 8;
			case CANNON_LEFT, CANNON_RIGHT:
				return 7;
			}
			return 1;
		}
	}
	
	public static class EnemyConstants
	{
		public static final int CRABBY=0;
		
		public static final int IDLE=0;
		public static final int RUNNING=1;
		public static final int ATTACK=2;
		public static final int HIT=3;
		public static final int DEAD=4;
		
		public static final int CRABBY_WIDTH_DEFAULT=72;
		public static final int CRABBY_HEIGHT_DEFAULT=32;
		public static final int CRABBY_WIDTH=(int)(CRABBY_WIDTH_DEFAULT*Geme.SCALE);
		public static final int CRABBY_HEIGHT=(int)(CRABBY_HEIGHT_DEFAULT*Geme.SCALE);
		public static final int CRABBY_DRAWOFFSET_X=(int)(26*Geme.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y=(int)(9*Geme.SCALE);
		
		
		public static int getSpriteAmount(int enemy_type,int enemy_state)
		{
			switch(enemy_type)
			{
			case CRABBY:
				switch(enemy_state)
				{
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;
		}
		
		public static int GetMaxHealth(int enemyType)
		{
			switch(enemyType)
			{
			case CRABBY:
				return 10;
			default:
				return 1;
			}
		}
		public static int GetEnemyDmg(int enemyType)
		{
			switch(enemyType)
			{
			case CRABBY:
				return 10;
			default:
				return 0;
			}
		}
		
	}
	
	public static class Environment
	{
		public static final int BIG_CLOUD_WIDTH_DEFAULT	=448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT=101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT=74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT=24;
		
		public static final int BIG_CLOUD_WIDTH	=(int)(BIG_CLOUD_WIDTH_DEFAULT*Geme.SCALE);
		public static final int BIG_CLOUD_HEIGHT=(int)(BIG_CLOUD_HEIGHT_DEFAULT*Geme.SCALE);
		public static final int SMALL_CLOUD_WIDTH	=(int)(SMALL_CLOUD_WIDTH_DEFAULT*Geme.SCALE);
		public static final int SMALL_CLOUD_HEIGHT=(int)(SMALL_CLOUD_HEIGHT_DEFAULT*Geme.SCALE);

	}
	
	public static class UI
	{
		public static class Buttons
		{
			public static final int B_WIDTH_DEFAULT=140;
			public static final int B_HEIGHT_DEFAULT=56;
			public static final int B_WIDTH=(int)(B_WIDTH_DEFAULT*Geme.SCALE);
			public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT*Geme.SCALE);
			
		}
		public static class PauseButtons
		{
			public static final int SOUND_SIZE_DEFAULT=42;
			public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Geme.SCALE);
			
		}
		public static class UrmButtons{
			public static final int URM_DEFAULT_SIZE=56;
			public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Geme.SCALE);
			
		}
	}
	public static class Directions
	{
		public static final int left=0;
		public static final int up=1;
		public static final int right=2;
		public static final int down=3;
	}
	public static class PlayerConstants
	{
		public static final int idle =0;
		public static final int running =1;
		public static final int JUMP=2;
		public static final int falling=3;
		public static final int ATTACK=4;
		public static final int hit=5;
		public static final int DEAD=6;

		public static int GetSpriteAmount(int PlayerAction)
		{
			switch(PlayerAction)
			{
			case DEAD:
				return 8;
			case running:
				return 6;
			case idle:
				return 5;
			case hit:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case falling:
			default:
				return 1;
				
			}
		}
	}
}
