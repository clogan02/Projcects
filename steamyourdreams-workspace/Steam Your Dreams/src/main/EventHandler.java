package main;

import java.awt.Rectangle;

import tile.TileManager;

public class EventHandler {
	
	GamePanel panel;
	EventRect eventRect[][][];
	int prevEventX, prevEventY;
	boolean canTouchEvent;
	
	public EventHandler(GamePanel panel) {
		this.panel = panel;
		
		eventRect = new EventRect[panel.numMaps][panel.worldCols][panel.worldRows];
		int map = 0;
		int col = 0, row = 0;
		
		while (map < panel.numMaps && col < panel.worldCols && row < panel.worldRows) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 0; 
			eventRect[map][col][row].y = 0;
			eventRect[map][col][row].width = panel.tileSize;
			eventRect[map][col][row].height = panel.tileSize;
			eventRect[map][col][row].defaultRectX = eventRect[map][col][row].x;
			eventRect[map][col][row].defaultRectY = eventRect[map][col][row].y;
			
			col++;
			if (col == panel.worldCols) {
				col = 0;
				row++;
				
				if (row == panel.worldRows) {
					row = 0;
					map++;
				}
			}
		}
	}
	
	public void checkEvent() {
		int distX = Math.abs(panel.player.worldX - prevEventX);
		int distY = Math.abs(panel.player.worldY - prevEventY);
		int distance = Math.max(distX, distY);
		
		if (distance > panel.tileSize) {
			canTouchEvent = true;
		}
		if (canTouchEvent) {
			if (hit(1, 9, 7, "any") || hit(1, 9, 8, "any") || hit(1, 9, 19, "any") || hit(1, 9, 20, "any")) {
				changeMap();
			}
			
			if(hit(2, 0, 2, "any") || hit(2, 0, 3, "any") || hit(2, 0, 46, "any") || hit(2, 0, 47, "any")) {
				changeMap();
			}
		}
		
	}
	
	public boolean hit(int map, int col, int row, String direction) {
		boolean hit = false;
		
		if (map == panel.currMap) {
			panel.player.solidArea.x += panel.player.worldX;
			panel.player.solidArea.y += panel.player.worldY;
			
			eventRect[map][col][row].x += col * panel.tileSize;
			eventRect[map][col][row].y += row * panel.tileSize;
			
			if (panel.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if (panel.player.direction.contentEquals(direction) || direction.contentEquals("any")) {
					hit = true;
					eventRect[map][col][row].eventDone = true;
					prevEventX = panel.player.worldX;
					prevEventY = panel.player.worldY;
				}
			}
			// resets solid area positions
			panel.player.solidArea.x = panel.player.defaultSolidX;
			panel.player.solidArea.y = panel.player.defaultSolidY;
			eventRect[map][col][row].x = eventRect[map][col][row].defaultRectX;
			eventRect[map][col][row].y = eventRect[map][col][row].defaultRectY;	
		}
		
		return hit;
	}
	
	public void changeMap() {
		if (panel.currMap == 1) {
			panel.tiles = new TileManager(panel);
			panel.currMap = 2;
			panel.tiles.loadMap("/maps/map02.txt", 2);
			panel.player.worldX = panel.tileSize * 1;
			panel.player.worldY = panel.tileSize * 2;
			prevEventX = panel.player.worldX;
			prevEventY = panel.player.worldY;
			canTouchEvent = false;
		} else {
			panel.tiles = new TileManager(panel);
			panel.currMap = 1;
			panel.tiles.loadMap("/maps/map01.txt", 1);
			panel.player.worldX = panel.tileSize * 8;
			panel.player.worldY = panel.tileSize * 7;
			prevEventX = panel.player.worldX;
			prevEventY = panel.player.worldY;
			canTouchEvent = false;
		}
	}
}
