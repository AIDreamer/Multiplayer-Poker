package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.enums.Suit;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testGenerateBestHand() {
		
		for (int i = 0; i < 0; i ++) {
			System.out.println(i);
		}
		ArrayList<Card> sevenCards = new ArrayList<Card>();
		sevenCards.add(Card.createCard(Suit.SPADE, 10));
		sevenCards.add(Card.createCard(Suit.CLUB, 8));
		sevenCards.add(Card.createCard(Suit.SPADE, 11));
		sevenCards.add(Card.createCard(Suit.DIAMOND, 8));
		sevenCards.add(Card.createCard(Suit.HEART, 8));
		sevenCards.add(Card.createCard(Suit.CLUB, 8));
		sevenCards.add(Card.createCard(Suit.SPADE, 8));
		Player player = Player.createPlayer("Son", 50000, new Game());
		player.generateBestHand(sevenCards);
		System.out.println(player.getBestHand());
	}

}
