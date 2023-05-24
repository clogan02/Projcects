package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel panel;
	public Tile[] tile;
	public int tileNum[][][];
	
	public TileManager(GamePanel panel) {
		this.panel = panel;
		tile = new Tile[10];
		// 3D array that stores a map and their rows/cols
		tileNum = new int[panel.numMaps][panel.worldCols][panel.worldRows];
		getTileImage();
		loadMap("/maps/map01.txt", 1);
		loadMap("/maps/map02.txt", 2);
	}
	
	public void getTileImage() {
		setup(0, "/tiles/Grass.png", false);
		setup(1, "/tiles/Building Floor.png", false);
		setup(2, "/tiles/Path.png", false);
		setup(3, "/tiles/Wall.png", true);
		setup(4, "/tiles/Water.gif", true);
		setup(5, "/tiles/Roof.png", false);
		setup(6, "/tiles/Cafe Floor.png", false);
		setup(7, "/tiles/Invisible Cafe Floor.png", true);
	}

	public void setup(int index, String path, boolean collision) {
		UtilityTool tool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream(path));
			tile[index].image = tool.scaleImage(tile[index].image, panel.tileSize, panel.tileSize);
			tile[index].collision = collision;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String path, int map) {
		
		try {
			InputStream input = getClass().getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			int col = 0, row = 0;
			int num;
			
			while (col < panel.worldCols && row < panel.worldRows) {
				String line = reader.readLine();
				while (col < panel.worldCols) {
					String numbers[] = line.split(" ");
					
					num = Integer.parseInt(numbers[col]);
					
					tileNum[map][col][row] = num;
					col++;
				}
				
				if (col == panel.worldCols) {
					col = 0;
					row++;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		
		int worldCol = 0, worldRow = 0;
		int worldX, worldY, screenX, screenY;
		int num;
		
		while (worldCol < panel.worldCols && worldRow < panel.worldRows) {
			num = tileNum[panel.currMap][worldCol][worldRow];
			
			worldX = worldCol * panel.tileSize;
			worldY = worldRow * panel.tileSize;
			screenX = worldX - panel.player.worldX + panel.player.screenX;
			screenY = worldY - panel.player.worldY + panel.player.screenY;
			
			if (tile[num] != null) {
				if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX && worldX - panel.tileSize < panel.player.worldX + panel.player.screenX &&
						worldY + panel.tileSize > panel.player.worldY - panel.player.screenY && worldY - panel.tileSize < panel.player.worldY + panel.player.screenY) {
					g2.drawImage(tile[num].image, screenX, screenY, null);
				}
			}
			
			worldCol++;
			
			if (worldCol == panel.worldCols) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
