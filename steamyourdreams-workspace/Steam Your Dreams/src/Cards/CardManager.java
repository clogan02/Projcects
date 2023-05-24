package Cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class CardManager {

	GamePanel panel;
	Graphics2D g2;
	public Card[] cards;
	public int cardNum[][];
	public int cardX;
	public int cardY;
	public int card_1;
	public int card_2;
	public int cardCounter = 0;
	
	int displayCards[] = new int[8];
	ArrayList<Card> list = new ArrayList<Card>();

	public CardManager(GamePanel panel) {
		this.panel = panel;
		cards = new Card[8];
		// 3D array that stores a map and their rows/cols
		cardNum = new int[panel.worldCols][panel.worldRows];
		getCardImage();
	}

	private void getCardImage() {
		setup(0, "/matchcards/Match Card Rooster.png");
		setup(1, "/matchcards/Match Card Rooster.png");
		setup(2, "/matchcards/Match Card Monkey.png");
		setup(3, "/matchcards/Match Card Monkey.png");
		setup(4, "/matchcards/Match Card Dog.png");
		setup(5, "/matchcards/Match Card Dog.png");
		setup(6, "/matchcards/Match Card Dragon.png");
		setup(7, "/matchcards/Match Card Dragon.png");
	    // shuffles front facing cards
		list.addAll(Arrays.asList(cards));
		Collections.shuffle(list);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				cards[i] = list.get(i);
			}
		}
	}
	
	public void setup(int index, String path) {
		UtilityTool tool = new UtilityTool();
		try {
			cards[index] = new Card();
			cards[index].image = ImageIO.read(getClass().getResourceAsStream(path));
			cards[index].image = tool.scaleImage(cards[index].image, panel.tileSize * 2, panel.tileSize * 2);
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
	}
	
	
	public void placeCards(Graphics2D g2) {	
		this.g2 = g2;

		int pos;
		cardX = panel.tileSize;
		cardY = panel.height / 8;
		
		for (int i = 0; i < 8; i++) {
			pos = i % 4;
			if (i < 4) {
				cardX = panel.tileSize + (panel.tileSize * pos * 4);
				drawCard(cards[i].image, cardX, cardY, panel.tileSize * 2, panel.tileSize * 2);
			}
			
			if (i >= 4) {
				cardY = (panel.height / 8) + (panel.tileSize * 6);
				cardX = panel.tileSize + (panel.tileSize * pos * 4);
				drawCard(cards[i].image, cardX, cardY, panel.tileSize * 2, panel.tileSize * 2);
			}
		}
	}
	
	public void hideCards() {
		try {
			BufferedImage cardBack = ImageIO.read(getClass().getResourceAsStream("/matchcards/Match Card Back.png"));
			int pos;
			cardX = panel.tileSize;
			
			for (int i = 0; i < 8; i++) {
				pos = i % 4;
				if (i < 4) {
					cardY = panel.height / 8;
					cardX = panel.tileSize + (panel.tileSize * pos * 4);
					drawCard(cardBack, cardX, cardY, panel.tileSize * 2, panel.tileSize * 2);
				}
				
				if (i >= 4) {
					cardY = (panel.height / 8) + (panel.tileSize * 6);
					cardX = panel.tileSize + (panel.tileSize * pos * 4);
					drawCard(cardBack, cardX, cardY, panel.tileSize * 2, panel.tileSize * 2);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showCard(int selected, int x, int y) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/objects/Bench.png"));
			drawCard(image, panel.tileSize + (panel.tileSize * (x % 4) * 4),
				cardY, panel.tileSize * 2, panel.tileSize * 2);
				} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void drawCard(BufferedImage card, int x, int y, int width, int height) {

		g2.drawImage(card, cardX, cardY, panel.tileSize * 2, panel.tileSize * 2, null);

	}
	
	public boolean compareCards(Card a, Card b) {
		if (a != null && b != null) {
			if (a == b) {
				return true;
			}
		}
		return false;
	}
}
