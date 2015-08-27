package model;

import java.util.ArrayList;

import model.enums.Suit;
import model.hands.Flush;
import model.hands.Pair;
import model.hands.TwoPair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Game Class
 * 
 * @author astro_000
 *
 */
public class GameTest {

	/** An instant of an example game */
	private Game game;
	/** A list of players */
	private ArrayList<Player> players;
	/** A List of player */
	Player son;
	Player bobby;
	Player jason;
	Player sune;

	@Before
	public void setUp() throws Exception {
		game = new Game();

		son = Player.createPlayer("Son", 100000, game);
		bobby = Player.createPlayer("Bobby", 50000, game);
		jason = Player.createPlayer("Jason", 10, game);
		sune = Player.createPlayer("Sune", 1995, game);

		game.addPlayer(sune);
		game.addPlayer(jason);
		game.addPlayer(bobby);
		game.addPlayer(son);
	}

	@Test
	public void testFindBestHand() {

		ArrayList<Card> flush = new ArrayList<Card>();
		flush.add(Card.createCard(Suit.CLUB, 11));
		flush.add(Card.createCard(Suit.CLUB, 7));
		flush.add(Card.createCard(Suit.CLUB, 11));
		flush.add(Card.createCard(Suit.CLUB, 7));
		flush.add(Card.createCard(Suit.CLUB, 14));
		Flush flushHand = CardUtility.createFlush(flush);
		son.setBestHand(flushHand);

		ArrayList<Card> pair = new ArrayList<Card>();
		pair.add(Card.createCard(Suit.CLUB, 11));
		pair.add(Card.createCard(Suit.CLUB, 7));
		pair.add(Card.createCard(Suit.HEART, 11));
		pair.add(Card.createCard(Suit.DIAMOND, 8));
		pair.add(Card.createCard(Suit.SPADE, 14));
		Pair pairHand = CardUtility.createAPair(pair);
		bobby.setBestHand(pairHand);

		ArrayList<Card> pair2 = new ArrayList<Card>();
		pair2.add(Card.createCard(Suit.CLUB, 11));
		pair2.add(Card.createCard(Suit.CLUB, 7));
		pair2.add(Card.createCard(Suit.DIAMOND, 11));
		pair2.add(Card.createCard(Suit.DIAMOND, 6));
		pair2.add(Card.createCard(Suit.SPADE, 14));
		Pair pairHand2 = CardUtility.createAPair(pair2);
		jason.setBestHand(pairHand2);

		ArrayList<Card> twoPair = new ArrayList<Card>();
		twoPair.add(Card.createCard(Suit.CLUB, 11));
		twoPair.add(Card.createCard(Suit.CLUB, 7));
		twoPair.add(Card.createCard(Suit.HEART, 11));
		twoPair.add(Card.createCard(Suit.DIAMOND, 7));
		twoPair.add(Card.createCard(Suit.SPADE, 14));
		TwoPair twoPairHand = CardUtility.createTwoPair(twoPair);
		sune.setBestHand(twoPairHand);

		ArrayList<Card> twoFlush = new ArrayList<Card>();
		twoFlush.add(Card.createCard(Suit.DIAMOND, 11));
		twoFlush.add(Card.createCard(Suit.DIAMOND, 7));
		twoFlush.add(Card.createCard(Suit.DIAMOND, 11));
		twoFlush.add(Card.createCard(Suit.DIAMOND, 7));
		twoFlush.add(Card.createCard(Suit.DIAMOND, 14));
		Flush twoFlushHand = CardUtility.createFlush(twoFlush);
		sune.setBestHand(twoFlushHand);

	}

	@Test
	public void testGameConsole() {
		game.testingConsole();
	}

	@After
	public void tearDown() throws Exception {
		game = null;
	}

}
