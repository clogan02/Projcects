package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

//parent of playerClass
public class Entity {
	GamePanel panel;
	
	public int worldX, worldY;
	// sets character speed
	public int speed;
	public int spriteCounter = 0, spriteNumber = 1;
	public boolean collided = false;
	
	public BufferedImage walkUp1, walkUp2, walkDown1, walkDown2, walkLeft, walkRight, idleUp, idleDown, idleLeft, idleRight;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int defaultSolidX, defaultSolidY;
	public String direction;
	public boolean isPrincipal = false;
	// sets speed of npc actions
	public int actionCounter;
	
	String dialogues[] = new String[20];
	int dialogueIndex;
	
	public Entity(GamePanel panel) {
		this.panel = panel;
	}
	
	public void setAction() {}
	
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 1;
		}
		
		panel.ui.dialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(panel.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	
	public void update() {
		setAction();
		collided = false;
		
		panel.check.checkTile(this);
		panel.check.checkObject(this, false);
		panel.check.checkPlayer(this);
		
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
	public BufferedImage setup(String path) {
		UtilityTool tool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
			image = tool.scaleImage(image, panel.tileSize, panel.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}

	public void draw(Graphics2D g2) {
		int screenX = worldX - panel.player.worldX + panel.player.screenX;
		int screenY = worldY - panel.player.worldY + panel.player.screenY;
		BufferedImage image = idleDown;
		
		if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX && worldX - panel.tileSize < panel.player.worldX + panel.player.screenX &&
				worldY + panel.tileSize > panel.player.worldY - panel.player.screenY && worldY - panel.tileSize < panel.player.worldY + panel.player.screenY) {
			
			switch(direction) {
			case "up":
				if (spriteNumber == 1) image = idleDown;
				if (spriteNumber == 2) image = idleDown;
				break;
			case "down":
				if (spriteNumber == 1) image = idleDown;
				if (spriteNumber == 2) image = idleDown;
				break;
			case "left":
				if (spriteNumber == 1) image = idleDown;
				if (spriteNumber == 2) image = idleDown;
				break;
			case "right":
				if (spriteNumber == 1) image = idleDown;
				if (spriteNumber == 2) image = idleDown;
				break;
			}
			g2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
		}
	}
}
