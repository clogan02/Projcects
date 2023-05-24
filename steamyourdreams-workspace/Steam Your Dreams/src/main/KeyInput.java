package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	public boolean up, down, left, right, interact, sprint, resume, selected;
	GamePanel panel;

	public KeyInput (GamePanel panel) {
		this.panel = panel;
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// matching game quick start
		//		if (code == KeyEvent.VK_4) {
		//			panel.gameState = 4;
		//		}
		// title state
		if (panel.gameState == 0) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				up = true;
				if (panel.ui.commandNum != 0) {
					panel.ui.commandNum--;
				}
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				down = true;
				if (panel.ui.commandNum != 2) {
					panel.ui.commandNum++;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (panel.ui.commandNum == 0) { // start game
					panel.gameState = 1;
				} else if (panel.ui.commandNum == 1) { // display controls
					panel.gameState = 3;
					panel.ui.dialogue = "WASD/Arrow Keys Control Movement \n e -> interact w/ npcs/objects \n space -> exit dialogue \n enter -> confirm";
				} else if (panel.ui.commandNum == 2) { // display controls
					System.exit(0);
				} 
			}
		}
		// play state
		if (panel.gameState == 1) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				up = true;
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				down = true;
			}
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				left = true;
			}
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				right = true;
			}

			if (code == KeyEvent.VK_E) {
				interact = true;
			}
			// allows for a sprinting function
			if (code == KeyEvent.VK_SHIFT) {
				sprint = true;
			}
		}
		// matching game state
		if (panel.gameState == 4) {

			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				up = true;
				if (panel.ui.cardNumY != 0) {
					panel.ui.cardNumY--;
					panel.ui.cursorY = (panel.height / 8);
					panel.ui.currCard -= 4;
				}
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				down = true;
				if (panel.ui.cardNumY != 1) {
					panel.ui.cursorY = (panel.height / 8) + (panel.tileSize * 6);
					panel.ui.currCard += 4;
					panel.ui.cardNumY++;
				}
			}
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				left = true;
				if (panel.ui.cardNumX != 0) {
					panel.ui.cursorX = panel.tileSize + (panel.tileSize * panel.ui.cardNumX * 4);
					panel.ui.currCard--;
					panel.ui.cardNumX--;
				}
			}
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				right = true;
				if (panel.ui.cardNumX != 3) {
					panel.ui.cursorX = panel.tileSize + (panel.tileSize * panel.ui.cardNumX * 4);
					panel.ui.currCard++;
					panel.ui.cardNumX++;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				selected = true;
			}
		}
		if (panel.gameState == 5) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				if (panel.ui.commandNum != 0) {
					panel.ui.commandNum--;
				}
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				if (panel.ui.commandNum != 1) {
					panel.ui.commandNum++;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (panel.ui.commandNum == 0) { // title screen 
					panel.restart();
					panel.gameState = 0;
				} else if (panel.ui.commandNum == 1) { // retry
					panel.restart();
					panel.gameState = 1;
				}
				//				else if (panel.ui.commandNum == 2) { // quit
				//					
				//				}
			}

		}
		// pauses/resumes the game code == KeyEvent.VK_ESCAPE || 
		if (code == KeyEvent.VK_SPACE) {
			if (panel.gameState == 1) {
				resume = false;
				panel.gameState = 2; 
			}
			if (panel.gameState == 2 || panel.gameState == 3){
				resume = true;
				panel.gameState = 1;
			}
			if (panel.gameState == 5){
				resume = true;
				panel.gameState = 0;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			up = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			right = false;
		}

		if (code == KeyEvent.VK_E) {
			interact = false;
		}

		if (code == KeyEvent.VK_SHIFT) {
			sprint = false;
		}

		if (panel.gameState == 4) {
			if (code == KeyEvent.VK_ENTER) {
				selected = false;
			}
		}

	}

}
