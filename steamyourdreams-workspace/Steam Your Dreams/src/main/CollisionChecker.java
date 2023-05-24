package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel panel;
	
	public CollisionChecker(GamePanel panel) {
		this.panel = panel;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftX = entity.worldX + entity.solidArea.x;
		int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopY = entity.worldY + entity.solidArea.y;
		int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftX/panel.tileSize;
		int entityRightCol = entityRightX/panel.tileSize;
		int entityTopRow = entityTopY/panel.tileSize;
		int entityBottomRow = entityBottomY/panel.tileSize;
		
		int tile1, tile2;
		
		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopY - entity.speed)/panel.tileSize;
			tile1 = panel.tiles.tileNum[panel.currMap][entityLeftCol][entityTopRow];
			tile2 = panel.tiles.tileNum[panel.currMap][entityRightCol][entityTopRow];
			
			if(panel.tiles.tile[tile1].collision == true || panel.tiles.tile[tile2].collision == true) {
				entity.collided = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomY + entity.speed)/panel.tileSize;
			tile1 = panel.tiles.tileNum[panel.currMap][entityLeftCol][entityBottomRow];
			tile2 = panel.tiles.tileNum[panel.currMap][entityRightCol][entityBottomRow];
			
			if(panel.tiles.tile[tile1].collision == true || panel.tiles.tile[tile2].collision == true) {
				entity.collided = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftX - entity.speed)/panel.tileSize;
			tile1 = panel.tiles.tileNum[panel.currMap][entityLeftCol][entityTopRow];
			tile2 = panel.tiles.tileNum[panel.currMap][entityLeftCol][entityBottomRow];
			
			if(panel.tiles.tile[tile1].collision == true || panel.tiles.tile[tile2].collision == true) {
				entity.collided = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightX + entity.speed)/panel.tileSize;
			tile1 = panel.tiles.tileNum[panel.currMap][entityRightCol][entityTopRow];
			tile2 = panel.tiles.tileNum[panel.currMap][entityRightCol][entityBottomRow];
			
			if(panel.tiles.tile[tile1].collision == true || panel.tiles.tile[tile2].collision == true) {
				entity.collided = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		
		for (int i = 0; i < panel.obj[1].length; i++) {
			if (panel.obj[panel.currMap][i] != null) {
				// gets the solid area positions
				entity.solidArea.x += entity.worldX;
				entity.solidArea.y += entity.worldY;
				
				panel.obj[panel.currMap][i].solidArea.x += panel.obj[panel.currMap][i].worldX;	
				panel.obj[panel.currMap][i].solidArea.y += panel.obj[panel.currMap][i].worldY;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if (entity.solidArea.intersects(panel.obj[panel.currMap][i].solidArea)) {
						if (panel.obj[panel.currMap][i].hasCollision) {
							entity.collided = true;
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if (entity.solidArea.intersects(panel.obj[panel.currMap][i].solidArea)) {
						if (panel.obj[panel.currMap][i].hasCollision) {
							entity.collided = true;
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "left": 
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(panel.obj[panel.currMap][i].solidArea)) {
						if (panel.obj[panel.currMap][i].hasCollision) {
							entity.collided = true;
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(panel.obj[panel.currMap][i].solidArea)) {
						if (panel.obj[panel.currMap][i].hasCollision) {
							entity.collided = true;
						}
						if (player) {
							index = i;
						}
					}
					break;
				}
				
				entity.solidArea.x = entity.defaultSolidX;
				entity.solidArea.y = entity.defaultSolidY;
				panel.obj[panel.currMap][i].solidArea.x = panel.obj[panel.currMap][i].defaultSolidX;
				panel.obj[panel.currMap][i].solidArea.y = panel.obj[panel.currMap][i].defaultSolidY;
			}
		}
		return index;
	}
	
	// checks npc collision
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;
		
		for (int i = 0; i < target[1].length; i++) {
			if (target[panel.currMap][i] != null) {
				// gets the solid area positions
				entity.solidArea.x += entity.worldX;
				entity.solidArea.y += entity.worldY;
				
				target[panel.currMap][i].solidArea.x += target[panel.currMap][i].worldX;	
				target[panel.currMap][i].solidArea.y += target[panel.currMap][i].worldY;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if (entity.solidArea.intersects(target[panel.currMap][i].solidArea)) {
						entity.collided = true;
						index = i;
					}
					
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if (entity.solidArea.intersects(target[panel.currMap][i].solidArea)) {
						entity.collided = true;
						index = i;
					}
					break;
				case "left": 
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(target[panel.currMap][i].solidArea)) {
						entity.collided = true;
						index = i;
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(target[panel.currMap][i].solidArea)) {
						entity.collided = true;
						index = i;
					}
					break;
				}
				
				entity.solidArea.x = entity.defaultSolidX;
				entity.solidArea.y = entity.defaultSolidY;
				target[panel.currMap][i].solidArea.x = target[panel.currMap][i].defaultSolidX;
				target[panel.currMap][i].solidArea.y = target[panel.currMap][i].defaultSolidY;
			}
		}
		return index;
	}
	
	// checks NPC collision against player
	public void checkPlayer(Entity entity) {

		// gets the solid area positions
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;

		panel.player.solidArea.x += panel.player.worldX;	
		panel.player.solidArea.y += panel.player.worldY;

		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			if (entity.solidArea.intersects(panel.player.solidArea)) {
				entity.collided = true;
			}

			break;
		case "down":
			entity.solidArea.y += entity.speed;
			if (entity.solidArea.intersects(panel.player.solidArea)) {
				entity.collided = true;
			}
			break;
		case "left": 
			entity.solidArea.x -= entity.speed;
			if (entity.solidArea.intersects(panel.player.solidArea)) {
				entity.collided = true;
			}
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			if (entity.solidArea.intersects(panel.player.solidArea)) {
				entity.collided = true;
			}
			break;
		}

		entity.solidArea.x = entity.defaultSolidX;
		entity.solidArea.y = entity.defaultSolidY;
		panel.player.solidArea.x = panel.player.defaultSolidX;
		panel.player.solidArea.y = panel.player.defaultSolidY;
	}
}
