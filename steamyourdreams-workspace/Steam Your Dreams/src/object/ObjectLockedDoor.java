package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectLockedDoor extends SuperObject {
	GamePanel panel;
	
	public ObjectLockedDoor(GamePanel panel, String object) {
		this.panel = panel;
		name = object;
			
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/" + name + ".png"));
			tool.scaleImage(image, panel.tileSize, panel.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		hasCollision = true;
	}
}
