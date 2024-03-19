package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Geme;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {

	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemymanager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Geme.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Geme.GAME_WIDTH);
	private int maxLvlOffsetx;

	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPos;
	private Random rnd = new Random();

	private boolean gameOver;
	private boolean lvlCompleted;
	private boolean playerDying=false;

	public Playing(Geme geme) {
		super(geme);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudsPos = new int[8];
		for (int i = 0; i < smallCloudsPos.length; i++)
			smallCloudsPos[i] = (int) (90 * Geme.SCALE) + rnd.nextInt((int) (100 * Geme.SCALE));
		calcLvlOffset();
		loadStartLevel();
	}
	public void loadNextLevel()
	{
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		resetAll();
	}

	private void loadStartLevel() {
		enemymanager.LoadEnemies(levelManager.getCurrentLevel());
		objectManager.loadObjects(levelManager.getCurrentLevel());
		
	}

	private void calcLvlOffset() {

		maxLvlOffsetx=levelManager.getCurrentLevel().getLvlOffset();
	}

	private void initClasses() {
		levelManager = new LevelManager(geme);
		enemymanager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		player = new Player(200, 200, (int) (64 * Geme.SCALE), (int) (40 * Geme.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay=new LevelCompletedOverlay(this);
	}

	@Override
	public void update() {
		if(paused)
		{
			pauseOverlay.update();
		}else if(lvlCompleted)
			levelCompletedOverlay.update();
		else if(gameOver)
		{
			gameOverOverlay.update();
		}
		else if(playerDying)
			player.update();
		else
		{
			levelManager.update();
			objectManager.update(levelManager.getCurrentLevel().getLevelData(),player);
			player.update();
			enemymanager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		if (xLvlOffset > maxLvlOffsetx)
			xLvlOffset = maxLvlOffsetx;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Geme.GAME_WIDTH, Geme.GAME_HEIGHT, null);
		drawClouds(g);
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemymanager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Geme.GAME_WIDTH, Geme.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
		else if(gameOver)
			gameOverOverlay.draw(g);
		else if(lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	private void drawClouds(Graphics g) {

		for (int i = 0; i < 3; i++)
			g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Geme.SCALE),
					BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		for (int i = 0; i < smallCloudsPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i],
					SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
	}

	public void resetAll() {
		gameOver=false;
		paused=false;
		lvlCompleted=false;
		playerDying=false;
		player.resetAll();
		enemymanager.resetAllEnemies();
		objectManager.resetAllObjects();
	}

	public void checkEnemyHit(Rectangle2D.Float attackbox) {
		enemymanager.checkEnemyHit(attackbox);
	}

	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
	}
	public void checkSpikesTouched(Player p) {
		objectManager.checkSpikesTouched(p);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
			else if(e.getButton()==MouseEvent.BUTTON3)
			{
				player.powerAttack();
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver)
		{
			if (paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
		else
			gameOverOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver)
		{
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
		else
			gameOverOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver)
		{
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
		else
			gameOverOverlay.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {

			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				player.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				player.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}

	}
	
	public void setLevelCompleted(boolean lvlCompleted)
	{
		this.lvlCompleted=lvlCompleted;
	}
	
	public void setMaxLvlOffset(int lvlOffset)
	{
		this.maxLvlOffsetx=lvlOffset;
	}

	public void unPauseGame() {
		paused = false;
	}

	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public EnemyManager getEnemyManager()
	{
		return enemymanager;
	}
	public ObjectManager getObjectManager()
	{
		return objectManager;
	}
	public LevelManager getLevelManager()
	{
		return levelManager;
	}
	public void setPlayerDying(boolean playerDying) {
		this.playerDying=playerDying;
		
	}
	
	
}
