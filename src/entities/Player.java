package entities;

import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import static utilz.Constants.PlayerConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Geme;
import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] ani;
	private boolean left,right,jump;
	private boolean moving=false,attacking=false;
	private int[][] lvlData;
	private float xDrawOffset=21*Geme.SCALE;
	private float yDrawOffset=4*Geme.SCALE;
	//jumping/gravity
	private float jumpSpeed=-2.25f*Geme.SCALE;
	private float fallSpeedAfterCollision=0.5f*Geme.SCALE;
	
	//status bar ui
	private BufferedImage statusBarImg;
	
	private int statusBarWidth=(int)(192*Geme.SCALE);
	private int statusBarHeight=(int)(58*Geme.SCALE);
	private int statusBarX=(int)(10*Geme.SCALE);
	private int statusBarY=(int)(10*Geme.SCALE);
	
	private int healthBarWidth=(int)(150*Geme.SCALE);
	private int healthBarHeight=(int)(4*Geme.SCALE);
	private int healthBarXStart=(int)(34*Geme.SCALE);
	private int healthBarYStart=(int)(14*Geme.SCALE);
	private int healthWidth=healthBarWidth;
	
	private int powerBarWidth = (int) (104 * Geme.SCALE);
	private int powerBarHeight = (int) (2 * Geme.SCALE);
	private int powerBarXStart = (int) (44 * Geme.SCALE);
	private int powerBarYStart = (int) (34 * Geme.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;
	
	private int flipX=0;
	private int flipW=1;
	
	private boolean attackChecked;
	private Playing playing;
	
	private int tileY=0;
	
	private boolean powerAttackActive;
	private int powerAttackTick;
	private int powerGrowSpeed=15;
	private int powerGrowTick;
	
	public Player(float x, float y, int width, int height,Playing playing) {
		super(x, y,width,height);
		this.playing=playing;
		this.state=idle;
		this.maxHealth=100;
		this.currentHealth=90;
		this.walkSpeed=Geme.SCALE*1.0f;
		loadAnimations();
		initHitbox(20,27);
		initAttackBox();
	}
	public void setSpawn(Point spawn)
	{
		this.x=spawn.x;
		this.y=spawn.y;
		hitbox.x=x;
		hitbox.y=y;
		
	}
	private void initAttackBox() {
		attackBox=new Rectangle2D.Float(x,y,(int)(20*Geme.SCALE),(int)(20*Geme.SCALE));
		resetAttackBox();
	}
	public void update()
	{
		updateHealthBar();
		updatePowerBar();
		if(currentHealth<=0)
		{
			if(state!=DEAD)
			{
				state=DEAD;
				aniTick=0;
				aniIndex=0;
				playing.setPlayerDying(true);
			}
			else if(aniIndex==GetSpriteAmount(DEAD)-1&&aniTick>=ANI_SPEED-1)
			{
				playing.setGameOver(true);
			}
			else
				updateAnimationTick();
			return;
		}
		updateAttackBox();
		
		updatepos();
		if(moving)
		{
			checkPotionTouched();
			checkSpikesTouched();
			tileY=(int) (hitbox.y/Geme.TILES_SIZE);
			if(powerAttackActive)
			{
				powerAttackTick++;
				if(powerAttackTick>=35)
				{
					powerAttackTick=0;
					powerAttackActive=false;
				}
			}
		}	
		if(attacking||powerAttackActive)
			checkAttack();
		updateAnimationTick();
		
		setAnimation();
	}
	
	
	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);
		
	}
	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);
		
	}
	private void checkAttack() {
		 if(attackChecked ||aniIndex!=1)
			 return;
		 attackChecked=true;
		 if(powerAttackActive)
			 attackChecked=false;
		 playing.checkEnemyHit(attackBox);
		 playing.checkObjectHit(attackBox);
		
	}
	private void updateAttackBox() {
		if(left&&right)
		{
			if(flipW==1)
			{
				attackBox.x=hitbox.x+hitbox.width+(int)(Geme.SCALE*10);
			}
			else
			{
				attackBox.x=hitbox.x-hitbox.width-(int)(Geme.SCALE*10);
			}
		}
		else if(right||(powerAttackActive&&flipW==1))
		{
			attackBox.x=hitbox.x+hitbox.width+(int)(Geme.SCALE*10);
		}
		else if(left||(powerAttackActive&&flipW==-1))
		{
			attackBox.x=hitbox.x-hitbox.width-(int)(Geme.SCALE*10);
		}
		attackBox.y=hitbox.y+(Geme.SCALE*10);
	}
	private void updateHealthBar() {
		healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);//when we divide this we make it a percentile and that
		                                  //percentile gets multiplied with the width of the bar so 40% health= 40%of the health bar
	}
	private void updatePowerBar() {
		powerWidth=(int)((powerValue/(float)powerMaxValue)*powerBarWidth);
		
		powerGrowTick++;
		if(powerGrowTick>=powerGrowSpeed)
		{
			powerGrowTick=0;
			changePower(1);
		}
		
	}
	public void render(Graphics g,int lvlOffset) {
		
		g.drawImage(ani[state][aniIndex], 
				(int)(hitbox.x-xDrawOffset)-lvlOffset+flipX, 
				(int)(hitbox.y-yDrawOffset),width*flipW,height, null);
		//drawHitbox(g,lvlOffset);
		//drawAttackBox(g,lvlOffset);
		drawUI(g);
		
	}
	private void drawUI(Graphics g) {
		//background ui
		g.drawImage(statusBarImg, statusBarX, statusBarX, statusBarWidth, statusBarHeight, null);
		//health ui
		g.setColor(Color.red);
		g.fillRect(healthBarXStart+statusBarX, healthBarYStart+statusBarY, healthWidth, healthBarHeight);
		//power bar
		g.setColor(Color.yellow);
		g.fillRect(powerBarXStart+statusBarX, powerBarYStart+statusBarY, powerWidth, powerBarHeight);
	}
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >=ANI_SPEED)
		{
			aniTick=0;
			aniIndex++;
			if(aniIndex>=GetSpriteAmount(state))
				{
				aniIndex=0;
				attacking=false;
				attackChecked=false;
				}
		}
	}
	private void setAnimation() {
		int startAni=state;
		if(moving)
			state=running;
		else
			state=idle;
		if(inAir)
		{
			if(airSpeed<0)
				state=JUMP;
			else
				state=falling;
		}
		
		if(powerAttackActive)
		{
			state=ATTACK;
			aniIndex=1;
			aniTick=0;
			return;
		}
		
		if(attacking)
		{
			state=ATTACK;
			if(startAni!=ATTACK)
			{
				aniIndex=1;
				aniTick=0;
				return;
			}
		}
		if(startAni!=state)
			resetAniTick();
	}
	private void resetAniTick() {
		aniIndex=0;
		aniTick=0;
	}
	private void updatepos() {           
		moving =false;                  
		if(jump)
			jump();
		if(!inAir)
			if(!powerAttackActive)
			if((right&&left)||(!left&&!right))//this is to check the condition when two opposite buttons
				return;	//are pressed
		
		float xSpeed=0;
		
		if(left&&!right)
		{
			xSpeed-=walkSpeed;
			flipX=width;
			flipW=-1;
		}
		 if(right&&!left)
		 {
			xSpeed+=walkSpeed;
			flipX=0;
			flipW=1;
		 }
		 if(powerAttackActive)
		 {
			 if((!left&&!right)||(left&&right))
			 {
				 if(flipW==-1)
					 xSpeed=-walkSpeed;
				 else
					 xSpeed=walkSpeed;
			 }
			 xSpeed*=3;
		 }
		 if(!inAir)
		 {
			 if(!IsEntityOnFloor(hitbox,lvlData))
				inAir=true; 
		 }
		 if(inAir&&!powerAttackActive)
		 {
			 if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)) {
				 hitbox.y+=airSpeed;
				 airSpeed+=GRAVITY;
				 updateXpos(xSpeed);
			 }
			 else
			 {
				 hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
				 if(airSpeed>0)
					 resetInAir();
				 else
					 airSpeed=fallSpeedAfterCollision;
				 	 updateXpos(xSpeed);
			 }
		 }
		 else
			 updateXpos(xSpeed);
		 
		 moving=true;
		 }
		 
	private void jump() {
		if(inAir)
			return;
		inAir=true;
		airSpeed=jumpSpeed;
	}
	private void resetInAir() {
		inAir=false;
		airSpeed=0;
		
	}
	private void updateXpos(float xSpeed) {
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
		{
		hitbox.x+=xSpeed;
	}
		else
		{
			hitbox.x=GetEntityXPosNextToWall(hitbox,xSpeed);
			if(powerAttackActive)
			{
				powerAttackActive=false;
				powerAttackTick=0;
			}
			
		}
		
	}
	
	public void changeHealth(int value)
	{
		currentHealth+=value;
		if(currentHealth<=0)
		{
			currentHealth=0;
			//gemeover
		}
		else if(currentHealth>=maxHealth)
			currentHealth=maxHealth;
	}
	public void kill() {
		currentHealth=0;
		
	}

	public void changePower(int value) {
		powerValue+=value;
		if(powerValue>=powerMaxValue)
			powerValue=powerMaxValue;
		else if(powerValue<=0)
			powerValue=0;
		
	}
	
	private void loadAnimations() {
		
		
			BufferedImage img =LoadSave.GetSpriteAtlas(LoadSave.Player_Atlas);
			

			ani=new BufferedImage[7][8];
			for(int j=0;j<ani.length;j++)
				
			for(int i=0;i<ani[j].length;i++)
				ani[j][i]=img.getSubimage(i*64,j*40,64,40);
			statusBarImg=LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
			
		} 		
	
	public void loadLvlData(int[][] lvlData)
	{
		this.lvlData=lvlData;
		
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir=true;
	}
	
	public void resetDirBooleans() {
		left=false;
		right=false;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public void setJump(boolean jump) {
		this.jump=jump;
	}
	public void resetAll() {
		resetDirBooleans();
		inAir=false;
		attacking=false;
		moving=false;
		airSpeed=0f;
		state=idle;
		currentHealth=maxHealth;
		
		hitbox.x=x;
		hitbox.y=y;
		resetAttackBox();
		
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir=true;
	}
	private void resetAttackBox()
	{
		if (flipW == 1) {
			attackBox.x = hitbox.x + hitbox.width + (int) (Geme.SCALE * 10);
		} else {
			attackBox.x = hitbox.x - hitbox.width - (int) (Geme.SCALE * 10);
		}
	}
	public int getTileY()
	{
		return tileY;
	}
	public void powerAttack() {
		if(powerAttackActive)
			return;
		if(powerValue>=60)
		{
			powerAttackActive=true;
			changePower(-60);
		}
		
	}
		
}
