package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyInput;
import main.UtilityTool;
import tile.TileManager;

public class Player extends Entity {
	GamePanel panel;
	KeyInput input;
	
	public final int screenX;
	public final int screenY;
	int numPoints = 0;
	int numKeys = 0;
	
	boolean mapLoaded = false;
	
	public Player(GamePanel panel, KeyInput input) {
		
		super(panel);
		this.panel = panel;
		this.input = input;
		screenX = panel.width/2 - (panel.tileSize/2);
		screenY = panel.height/2 - (panel.tileSize/2);
		
		// sets collision area
		solidArea = new Rectangle(15, 32, panel.tileSize / panel.scale, panel.tileSize / panel.scale);
		defaultSolidX = solidArea.x;
		defaultSolidY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		// player position on world map
		worldX = panel.tileSize * 3 - (panel.tileSize/2);
		worldY = panel.tileSize * 2 - (panel.tileSize/2);
		panel.currMap = 1;
		speed = 3;
		direction = "down";
	}
	
	public void getPlayerImage() {
		walkUp1 = setup("/player/SYD Sprite Walk Up 1.png");
		walkUp2 = setup("/player/SYD Sprite Walk Up 2.png");
		
		walkDown1 = setup("/player/SYD Sprite Walk Down 1.png");
		walkDown2 = setup("/player/SYD Sprite Walk Down 2.png");
			
		walkLeft = setup("/player/SYD Walk Left.png");
		walkRight = setup("/player/SYD Walk Right.png");
			
		idleUp = setup("/player/SYD Idle Up.png");
		idleDown = setup("/player/SYD Idle Down.png");
		idleLeft = setup("/player/SYD Idle Left.png");
		idleRight = setup("/player/SYD Idle Right.png");
	}
	
	public void update() {
		
		if (input.up == true || input.down == true || input.left == true || input.right == true) {
			
			if (input.up == true) {
				if (input.sprint) {
					speed = 7;
				} else {
					speed = 5;
				}
				direction = "up";
			}
			if (input.down == true) {
				if (input.sprint) {
					speed = 7;
				} else {
					speed = 5;
				}
				direction = "down";
			}
			if (input.left == true) {
				if (input.sprint) {
					speed = 7;
				} else {
					speed = 5;
				}
				direction = "left";
			}
			if (input.right == true) {
				if (input.sprint) {
					speed = 7;
				} else {
					speed = 5;
				}
				direction = "right";
			}
			// checks tile collision
			collided = false;
			panel.check.checkTile(this);
			// checks object collision
			int objIndex = panel.check.checkObject(this, true);
			interact(objIndex);
			// checks npc collision
			int npcIndex = panel.check.checkEntity(this, panel.npc);
			interactNPC(npcIndex);
			// checks events
			panel.handler.checkEvent();

			panel.input.interact = false;
			
			if (collided == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 10) {
				if (spriteNumber == 1) {
					spriteNumber = 2;
				} else if (spriteNumber == 2) {
					spriteNumber = 1;
				}
				spriteCounter = 0;
			}
			
		}
	}
	
	public void interact(int index) {
		if (index != 999) {
			String objName = panel.obj[panel.currMap][index].name;
			
			switch (objName) {
			case "Key":
				numKeys++;
				//removes the key that was picked up
				panel.obj[panel.currMap][index] = null;
				break;
			case "Locked Door":
				if (numKeys > 0) {
					panel.obj[panel.currMap][index] = null;
					numKeys--;
				}
				break;
//			case "Sign 1":
//				if (panel.input.interact /* && this.solidArea.intersects(panel.obj[index].solidArea)*/) {
//					panel.ui.showMessage("i eat tacos on tacho thursdasys");
//				}
//				break;
//			case "Door":
//				if (panel.currMap == 1 ) {
//					panel.tiles = new TileManager(panel);
//					panel.currMap = 2;
//					panel.tiles.loadMap("/maps/map02.txt", 2);
//				} else {
//					panel.tiles = new TileManager(panel);
//					panel.currMap = 1;
//					panel.tiles.loadMap("/maps/map01.txt", 1);
//				}
//				break;
//			case "Door (Glint)":
//				if (panel.currMap == 1 ) {
//					panel.tiles = new TileManager(panel);
//					panel.currMap = 2;
//					panel.tiles.loadMap("/maps/map02.txt", 2);
//				} else {
//					panel.tiles = new TileManager(panel);
//					panel.currMap = 1;
//					panel.tiles.loadMap("/maps/map01.txt", 1);
//				}
//				break;
//			case "Computer":
////				if ----.gameWon() {
////				 
////				} else {
////				 
////				} /* && this.solidArea.intersects(panel.obj[index].solidArea)*/
//				if (panel.input.interact) {
//					panel.gameState = 4;
//					System.out.println("Computer On");
//
//					if (panel.input.selected) {
//						System.out.println("card get");
//
//						if (panel.cards.cardCounter == 0) {
//							panel.cards.card_1 = panel.ui.currCard;
//							System.out.println(panel.cards.card_1);
//							panel.cards.cardCounter++;
//						}
//						if (panel.cards.cardCounter == 1) {
//							System.out.println(panel.cards.card_2);
//							panel.cards.card_2 = panel.ui.currCard;
//							panel.cards.cardCounter++;
//							if (panel.cards.compareCards(panel.cards.cards[panel.cards.card_1], panel.cards.cards[panel.cards.card_2])) {
//								numPoints++;
//							} else {
//								numPoints--;
//							}
//						}
//						//panel.cards.showCard(panel.ui.currCard, panel.ui.cursorX, panel.ui.cursorY);
//					}
//					
//					if (numPoints == 4) {
//						panel.ui.outcome = "Passed :)";
//					} else {
//						panel.ui.outcome = "Failed :(";
//					}
//					panel.ui.gameOver = true;
//				}
////				
//				break;
				
				
			}
		}
	}
	
	public void interactNPC(int index) {
		
		if (index != 999) {
			if (panel.input.interact) {
				panel.gameState = 3;
				panel.npc[panel.currMap][index].speak();
				// checks if the principal is safe
				if (panel.npc[panel.currMap][index].isPrincipal) {
					panel.gameState = 5;
				}
			}
		}
		
	}

	// clears objects from the current map when changing maps
//	public void clearObjects() {
//		int i = 0;
//		while(panel.obj[i] != null) {
//			panel.obj[i] = null;
//			i++;
//		}
//	}

	public void draw(Graphics2D g2) {
		
		BufferedImage image = idleDown;
		
		switch(direction) {
		case "up":
			if (spriteNumber == 1) image = walkUp1;
			if (spriteNumber == 2) image = walkUp2;
			break;
		case "down":
			if (spriteNumber == 1) image = walkDown1;
			if (spriteNumber == 2) image = walkDown2;
			break;
		case "left":
			if (spriteNumber == 1) image = walkLeft;
			if (spriteNumber == 2) image = idleLeft;
			break;
		case "right":
			if (spriteNumber == 1) image = walkRight;
			if (spriteNumber == 2) image = idleRight;
			break;
		}
		
		g2.drawImage(image, screenX, screenY, null);
	}
}
