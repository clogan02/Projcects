package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Guide extends Entity {
	
	public NPC_Guide(GamePanel panel) {
		super(panel);
		
		direction = "down";
		speed = 0;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
//		walkUp1 = setup("/player/SYD Sprite Walk Up 1.png");
//		walkUp2 = setup("/player/SYD Sprite Walk Up 2.png");
//		
//		walkDown1 = setup("/player/SYD Sprite Walk Down 1.png");
//		walkDown2 = setup("/player/SYD Sprite Walk Down 2.png");
//			
//		walkLeft = setup("/player/SYD Walk Left.png");
//		walkRight = setup("/player/SYD Walk Right.png");
//			
//		idleUp = setup("/player/SYD Idle Up.png");
//		idleLeft = setup("/player/SYD Idle Left.png");
//		idleRight = setup("/player/SYD Idle Right.png");
		idleDown = setup("/NPCs/Guide Idle Down.png");
	}
		
	public void setDialogue() {
		dialogues[0] = "Hello, we need your help! \nThe principal is locked inside and we can't get to her. \nWe need you to solve the puzzles and save the principal!";
		dialogues[1] = ":(";
	}
	
	public void setAction() {
		
		actionCounter++;
		
		// performs an action every 120 frames (2 seconds)
		if (actionCounter == 120) {		
			Random random = new Random();
			
			int i = random.nextInt(100) + 1;
			
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "right";
			}
			if (i > 75) {
				direction = "left";
			}
		}
		
		actionCounter = 0;
	}
	
	public void speak() {
		super.speak();
	}

}
