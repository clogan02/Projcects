package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Cards.CardManager;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

		final int size = 16; // creates a 16 x 16 tile
		public final int scale = 3;
		public final int tileSize = size * scale; // creates a 48 x 48 area for character size
		// creates a 16 x 12 grid
		public final int screenCols = 16;
		public final int screenRows = 12;
		// creates a 768 x 576 pixel screen
		public final int width = tileSize * screenCols;
		public final int height = tileSize * screenRows;
		// world map params
		public final int worldCols = 50;
		public final int worldRows = 50;
		public final int worldWidth = tileSize * worldCols;
		public final int worldHeight = tileSize * worldRows;
		public final int numMaps = 10;
		public int currMap = 1; // param for checking the map the player is on -- player starts on map (1)
		// used for creating map tiles
		public TileManager tiles = new TileManager(this);
		// used for creating matching game cards
		public CardManager cards = new CardManager(this);
		// used for receiving keyboard input
		public KeyInput input = new KeyInput(this);
		
		// creates thread for running the game
		Thread gameThread;
		// used to check which objects prevent player movement
		public CollisionChecker check = new CollisionChecker(this);
		// sets max number of displayable objects
		final int numObjects = 10;
		public AssetSetter setter = new AssetSetter(this);
		public UI ui = new UI(this);
		public EventHandler handler = new EventHandler(this);
		// creates player and things it can interact with
		public Player player = new Player(this, input);
		public SuperObject obj[][] = new SuperObject[numMaps][numObjects];
		public Entity npc[][] = new Entity[numMaps][numObjects];

		//sets default position
		int playerX = 100, playerY = 100;
		int playerSpeed = 5;
		
		int FPS = 60;
		
		public int gameState;

		public GamePanel() {
			this.setPreferredSize(new Dimension(width, height));
			this.setBackground(Color.black);
			this.setDoubleBuffered(true);
			this.addKeyListener(input);
			this.setFocusable(true);
		}
		
		public void setupGame() {
			setter.setObject();
			setter.setNPC();
			
			gameState = 0;
		}
		
		public void startGame() {
			gameThread = new Thread(this);
			gameThread.start();
		}

		public void run() {
			double drawInterval = 1000000000/FPS;
			double delta = 0;
			long lastTime = System.nanoTime();
			long currentTime;
			
			while (gameThread != null) {
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / drawInterval;
				lastTime = currentTime;
				
				if (delta >= 1) {
					update();
					repaint();
					delta--;
				}
			}
		}
		
		public void update() {
			
			if (gameState == 1) {
				// updates player
				player.update();
				// updates npc
				for (int i = 0; i < npc[1].length; i++) {
					if (npc[currMap][i] != null) {
						npc[currMap][i].update();
					}
				}
			}
			
			if (gameState == 2) {
				
			}
		}
		
		public void restart() {
			player.setDefaultValues();
			setter.setNPC();
			setter.setObject();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			if (gameState == 0) {
				ui.draw(g2);
			} else {
			
				// puts tiles in the game
				tiles.draw(g2);
				
				// places objects in the game
				for (int i = 0; i < obj[1].length; i++) {
					if (obj[currMap][i] != null) {
						obj[currMap][i].draw(g2, this);
					}
				}
				
				// places npcs in the game
				for (int i = 0; i < npc[1].length; i++) {
					if (npc[currMap][i] != null) {
						npc[currMap][i].draw(g2);
					}
				}
				// places player in the game
				player.draw(g2);
				
				ui.draw(g2);
				
				g2.dispose();
			}
			
		}
}
