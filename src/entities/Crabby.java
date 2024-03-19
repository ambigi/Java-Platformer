package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import java.awt.geom.Rectangle2D;

import main.Geme;

public class Crabby extends Enemy {

	private int attackBoxOffsetX;

	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(22,19);
		initAttackBox();

	}

	private void initAttackBox() {

		attackBox = new Rectangle2D.Float(x, y, (int) (82 * Geme.SCALE), (int) (19 * Geme.SCALE));
		attackBoxOffsetX = (int) (Geme.SCALE * 30);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();

	}

	private void updateAttackBox() {

		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);
		if (inAir)
			updateInAir(lvlData);
		else {
			switch (state) {
			case IDLE:
				newState(RUNNING);
				;
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, player)) {
					turnTowardPlayer(player);
					if (isPlayerCloseForAttack(player))
						newState(ATTACK);
				}
				move(lvlData);
				break;
			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;
				if (aniIndex == 3 && !attackChecked)
					checkEnemyHit(attackBox, player);
				break;
			case HIT:
				break;
			}
		}

	}

	public int flipX() {
		if (walkDir == right)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == right)
			return -1;
		else
			return 1;
	}
}
