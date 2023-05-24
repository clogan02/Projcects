package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectKey extends SuperObject{
	
	GamePanel panel;
	
	public ObjectKey(GamePanel panel) {
		this.panel = panel;
		name = "Key";
				
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Key.gif"));
			tool.scaleImage(image, panel.tileSize, panel.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		hasCollision = true;
	}
}
