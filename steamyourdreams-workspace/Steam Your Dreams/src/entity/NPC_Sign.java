package entity;

import main.GamePanel;

public class NPC_Sign extends Entity {
	public NPC_Sign(GamePanel panel) {
		super(panel);
		
		direction = "down";
		speed = 0;
		isPrincipal = false;
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		idleDown = setup("/NPCs/Sign.png");
	}
	
	public void setDialogue() {
		dialogues[1] = "1. Straight Middle \n 2. Very Top \n 3. One From the Bottom \n 4. Straight Middle";
	}
	
	public void speak() {
		super.speak();
	}
}
